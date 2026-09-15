package checkout.service;

import cart.entities.CartStatus;
import checkout.CheckoutServiceFactory;
import discount.service.DiscountService;
import exception.business.detailed_exceptions.CartAlreadyCheckedOutException;
import exception.business.detailed_exceptions.InsufficientStockException;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import order.entities.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shipping.ShippingStrategy;
import shipping.WeightBasedStrategy;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end JDBC tests for checkout.
 *
 * <p>These tests intentionally use a real PostgreSQL process instead of repository
 * mocks. Calling rollback() is easy to verify with Mockito, but only a database test
 * can prove that rows written by several repositories really belong to one atomic
 * transaction and disappear together after a failure.</p>
 */
class CheckoutServiceIntegrationTest {
    private static EmbeddedPostgres postgres;
    private static DataSource dataSource;

    @BeforeAll
    static void startPostgres() throws IOException {
        // A random free port and a temporary data directory isolate this suite from
        // the developer's database. Closing EmbeddedPostgres removes the test server.
        postgres = EmbeddedPostgres.builder().setPort(0).start();
        dataSource = postgres.getPostgresDatabase();
    }

    @AfterAll
    static void stopPostgres() throws IOException {
        if (postgres != null) {
            postgres.close();
        }
    }

    @BeforeEach
    void recreateSchema() throws Exception {
        // Recreating the small schema makes every test independent. A test can commit
        // real transactions without leaking data into the next test method.
        String schema = readClasspathResource("/db/checkout-schema.sql");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            for (String command : schema.split(";")) {
                if (!command.isBlank()) {
                    statement.execute(command);
                }
            }
        }
    }

    @Test
    @DisplayName("Successful checkout commits order, item snapshots, stock, cart and exact prices")
    void checkout_validData_commitsCompleteSnapshot() throws Exception {
        Seed seed = seedCartWithPhysicalAndDigitalProducts();

        try (Connection checkoutConnection = dataSource.getConnection()) {
            CheckoutService service = CheckoutServiceFactory.create(
                    checkoutConnection,
                    new WeightBasedStrategy(new BigDecimal("10.00")),
                    subTotal -> new BigDecimal("20.00")
            );

            Order order = service.checkout(seed.userId(), seed.cartId());

            assertAll(
                    () -> assertNotNull(order.getOrderId()),
                    () -> assertMoney("250.00", order.getSubTotal()),
                    () -> assertMoney("30.00", order.getShippingFee()),
                    () -> assertMoney("20.00", order.getDiscountAmount()),
                    () -> assertMoney("260.00", order.getTotalPrice()),
                    () -> assertTrue(checkoutConnection.getAutoCommit(),
                            "TransactionManagement must restore the connection for its next use")
            );
        }

        // Order-item prices are snapshots. A future change to products.price must not
        // rewrite the historical amount the customer agreed to pay at checkout.
        try (Connection verificationConnection = dataSource.getConnection()) {
            assertAll(
                    () -> assertEquals(1, queryInt(verificationConnection, "SELECT count(*) FROM orders")),
                    () -> assertEquals(2, queryInt(verificationConnection, "SELECT count(*) FROM order_items")),
                    () -> assertMoney("100.00", queryMoney(verificationConnection,
                            "SELECT unit_price FROM order_items WHERE product_id = " + seed.physicalProductId())),
                    () -> assertMoney("50.00", queryMoney(verificationConnection,
                            "SELECT unit_price FROM order_items WHERE product_id = " + seed.digitalProductId())),
                    () -> assertEquals(8, queryInt(verificationConnection,
                            "SELECT quantity FROM products WHERE id = " + seed.physicalProductId())),
                    () -> assertEquals(19, queryInt(verificationConnection,
                            "SELECT quantity FROM products WHERE id = " + seed.digitalProductId())),
                    () -> assertEquals(CartStatus.CHECKED_OUT.name(), queryString(
                            verificationConnection, "SELECT status FROM carts WHERE id = " + seed.cartId()))
            );
        }
    }

    @Test
    @DisplayName("A database failure after order writes rolls every checkout mutation back")
    void checkout_stockWriteFails_rollsBackEverything() throws Exception {
        long userId = insertUser("rollback@example.com");
        long productId = insertProduct("Rollback Product", 2, "50.00", "DIGITAL", null);
        long cartId = insertCart(userId);
        insertCartItem(cartId, productId, 2);

        try (Connection setupConnection = dataSource.getConnection();
             Statement statement = setupConnection.createStatement()) {
            // Fault injection without mocks: the current row (quantity=2) is valid,
            // but the checkout's real UPDATE to quantity=0 violates this constraint.
            // The failure occurs after order and order_items have already been INSERTed.
            statement.execute("""
                    ALTER TABLE products ADD CONSTRAINT reject_rollback_product_stock_change
                    CHECK (name <> 'Rollback Product' OR quantity = 2)
                    """);
        }

        try (Connection checkoutConnection = dataSource.getConnection()) {
            CheckoutService service = CheckoutServiceFactory.create(
                    checkoutConnection,
                    items -> BigDecimal.ZERO,
                    subTotal -> BigDecimal.ZERO
            );

            assertThrows(RuntimeException.class, () -> service.checkout(userId, cartId));
            assertTrue(checkoutConnection.getAutoCommit());
        }

        // All repositories were composed with one Connection. Therefore PostgreSQL
        // rolls their earlier INSERT/UPDATE statements back as one indivisible unit.
        try (Connection verificationConnection = dataSource.getConnection()) {
            assertAll(
                    () -> assertEquals(0, queryInt(verificationConnection, "SELECT count(*) FROM orders")),
                    () -> assertEquals(0, queryInt(verificationConnection, "SELECT count(*) FROM order_items")),
                    () -> assertEquals(2, queryInt(verificationConnection,
                            "SELECT quantity FROM products WHERE id = " + productId)),
                    () -> assertEquals(CartStatus.ACTIVE.name(), queryString(
                            verificationConnection, "SELECT status FROM carts WHERE id = " + cartId))
            );
        }
    }

    @Test
    @DisplayName("The same cart cannot create a second order or consume stock twice")
    void checkout_sameCartTwice_rejectsSecondAttempt() throws Exception {
        long userId = insertUser("once@example.com");
        long productId = insertProduct("One-time Product", 5, "25.00", "DIGITAL", null);
        long cartId = insertCart(userId);
        insertCartItem(cartId, productId, 2);

        try (Connection checkoutConnection = dataSource.getConnection()) {
            CheckoutService service = CheckoutServiceFactory.create(
                    checkoutConnection,
                    items -> BigDecimal.ZERO,
                    subTotal -> BigDecimal.ZERO
            );

            service.checkout(userId, cartId);
            assertThrows(CartAlreadyCheckedOutException.class,
                    () -> service.checkout(userId, cartId));
        }

        try (Connection verificationConnection = dataSource.getConnection()) {
            assertAll(
                    () -> assertEquals(1, queryInt(verificationConnection, "SELECT count(*) FROM orders")),
                    () -> assertEquals(3, queryInt(verificationConnection,
                            "SELECT quantity FROM products WHERE id = " + productId)),
                    () -> assertEquals(CartStatus.CHECKED_OUT.name(), queryString(
                            verificationConnection, "SELECT status FROM carts WHERE id = " + cartId))
            );
        }
    }

    @Test
    @DisplayName("Concurrent checkouts cannot make shared product stock negative")
    void checkout_twoBuyersForLastUnit_onlyOneCommits() throws Exception {
        long firstUserId = insertUser("buyer-one@example.com");
        long secondUserId = insertUser("buyer-two@example.com");
        long productId = insertProduct("Last Unit", 1, "75.00", "DIGITAL", null);
        long firstCartId = insertCart(firstUserId);
        long secondCartId = insertCart(secondUserId);
        insertCartItem(firstCartId, productId, 1);
        insertCartItem(secondCartId, productId, 1);

        CyclicBarrier bothValidatedStock = new CyclicBarrier(2);
        ShippingStrategy synchronizedShipping = items -> {
            try {
                // Both transactions have read quantity=1 before either reaches the
                // atomic UPDATE. This deterministically reproduces the stale-read race.
                bothValidatedStock.await(10, TimeUnit.SECONDS);
                return BigDecimal.ZERO;
            } catch (Exception exception) {
                throw new RuntimeException("Could not synchronize concurrent checkouts", exception);
            }
        };
        DiscountService noDiscount = subTotal -> BigDecimal.ZERO;

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            List<Callable<CheckoutResult>> attempts = List.of(
                    () -> runCheckout(firstUserId, firstCartId, synchronizedShipping, noDiscount),
                    () -> runCheckout(secondUserId, secondCartId, synchronizedShipping, noDiscount)
            );
            List<Future<CheckoutResult>> futures = executor.invokeAll(attempts);
            List<CheckoutResult> results = new ArrayList<>();
            for (Future<CheckoutResult> future : futures) {
                results.add(future.get(15, TimeUnit.SECONDS));
            }

            assertEquals(1, results.stream().filter(CheckoutResult::succeeded).count());
            Throwable failure = results.stream()
                    .filter(result -> !result.succeeded())
                    .map(CheckoutResult::failure)
                    .findFirst()
                    .orElseThrow();
            assertInstanceOf(InsufficientStockException.class, failure);
        } finally {
            executor.shutdownNow();
            assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
        }

        // UPDATE products ... WHERE quantity >= 1 takes a row lock. The loser waits;
        // after the winner commits quantity=0, PostgreSQL re-checks the WHERE clause,
        // updates zero rows, and the service rolls that checkout transaction back.
        try (Connection verificationConnection = dataSource.getConnection()) {
            assertAll(
                    () -> assertEquals(0, queryInt(verificationConnection,
                            "SELECT quantity FROM products WHERE id = " + productId)),
                    () -> assertEquals(1, queryInt(verificationConnection, "SELECT count(*) FROM orders")),
                    () -> assertEquals(1, queryInt(verificationConnection,
                            "SELECT count(*) FROM carts WHERE status = 'CHECKED_OUT'")),
                    () -> assertEquals(1, queryInt(verificationConnection,
                            "SELECT count(*) FROM carts WHERE status = 'ACTIVE'"))
            );
        }
    }

    private CheckoutResult runCheckout(
            long userId,
            long cartId,
            ShippingStrategy shippingStrategy,
            DiscountService discountService
    ) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try {
                CheckoutServiceFactory.create(connection, shippingStrategy, discountService)
                        .checkout(userId, cartId);
                return new CheckoutResult(true, null);
            } catch (RuntimeException failure) {
                return new CheckoutResult(false, failure);
            }
        }
    }

    private Seed seedCartWithPhysicalAndDigitalProducts() throws SQLException {
        long userId = insertUser("snapshot@example.com");
        long physicalProductId = insertProduct("Keyboard", 10, "100.00", "PHYSICAL", "1.500");
        long digitalProductId = insertProduct("E-book", 20, "50.00", "DIGITAL", null);
        long cartId = insertCart(userId);
        insertCartItem(cartId, physicalProductId, 2);
        insertCartItem(cartId, digitalProductId, 1);
        return new Seed(userId, cartId, physicalProductId, digitalProductId);
    }

    private long insertUser(String email) throws SQLException {
        return insertAndReturnId("""
                INSERT INTO users(name, email, role, status, password_hash)
                VALUES ('Test User', ?, 'NORMAL_USER', 'ACTIVE', 'hash')
                RETURNING id
                """, statement -> statement.setString(1, email));
    }

    private long insertProduct(
            String name,
            int quantity,
            String price,
            String type,
            String weight
    ) throws SQLException {
        return insertAndReturnId("""
                INSERT INTO products(name, quantity, price, status, type, weight)
                VALUES (?, ?, ?, 'ACTIVE', ?, ?)
                RETURNING id
                """, statement -> {
            statement.setString(1, name);
            statement.setInt(2, quantity);
            statement.setBigDecimal(3, new BigDecimal(price));
            statement.setString(4, type);
            if (weight == null) {
                statement.setNull(5, java.sql.Types.NUMERIC);
            } else {
                statement.setBigDecimal(5, new BigDecimal(weight));
            }
        });
    }

    private long insertCart(long userId) throws SQLException {
        return insertAndReturnId("""
                INSERT INTO carts(user_id, status)
                VALUES (?, 'ACTIVE')
                RETURNING id
                """, statement -> statement.setLong(1, userId));
    }

    private void insertCartItem(long cartId, long productId, int quantity) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO cart_items(cart_id, product_id, quantity)
                     VALUES (?, ?, ?)
                     """)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            statement.setInt(3, quantity);
            statement.executeUpdate();
        }
    }

    private long insertAndReturnId(String sql, SqlBinder binder) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.bind(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                return resultSet.getLong(1);
            }
        }
    }

    private static int queryInt(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            assertTrue(resultSet.next());
            return resultSet.getInt(1);
        }
    }

    private static BigDecimal queryMoney(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            assertTrue(resultSet.next());
            return resultSet.getBigDecimal(1);
        }
    }

    private static String queryString(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            assertTrue(resultSet.next());
            return resultSet.getString(1);
        }
    }

    private static void assertMoney(String expected, BigDecimal actual) {
        assertEquals(0, new BigDecimal(expected).compareTo(actual));
    }

    private static String readClasspathResource(String path) throws IOException {
        try (InputStream stream = CheckoutServiceIntegrationTest.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Missing classpath resource: " + path);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @FunctionalInterface
    private interface SqlBinder {
        void bind(PreparedStatement statement) throws SQLException;
    }

    private record Seed(long userId, long cartId, long physicalProductId, long digitalProductId) {
    }

    private record CheckoutResult(boolean succeeded, Throwable failure) {
    }
}
