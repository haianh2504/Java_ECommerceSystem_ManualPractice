package product.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Stream;

public class PhysicalProductTest {
    private static final Long PRODUCT_ID = 1L;
    private static final ProductName PRODUCT_NAME = new ProductName("Mechanical Keyboard");
    private static final int STOCK_QUANTITY = 10;
    private static final BigDecimal BASE_PRICE = new BigDecimal("100.00");
    private static final ProductStatus STATUS = ProductStatus.ACTIVE;
    private static final ProductType TYPE = ProductType.PHYSICAL;
    private static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    private static final BigDecimal WEIGHT = new BigDecimal("2.50");

    @Nested
    @DisplayName("Return a physical product from SQL")
    class ReturnPhysicalProductFromSql {
        // Verify that the SQL-return constructor maps all values to a Product instance.
        @Test
        @DisplayName("Construct a physical product from valid SQL data")
        void constructPhysicalProduct_validSqlData() {
            Product product = new PhysicalProduct(
                    PRODUCT_ID, PRODUCT_NAME, STOCK_QUANTITY, BASE_PRICE,
                    STATUS, TYPE, CREATED_AT, WEIGHT
            );

            Assertions.assertAll(
                    () -> Assertions.assertInstanceOf(PhysicalProduct.class, product),
                    () -> Assertions.assertEquals(PRODUCT_ID, product.getId()),
                    () -> Assertions.assertSame(PRODUCT_NAME, product.getName()),
                    () -> Assertions.assertEquals(STOCK_QUANTITY, product.getQuantity()),
                    () -> Assertions.assertEquals(BASE_PRICE, product.getBasePrice()),
                    () -> Assertions.assertEquals(STATUS, product.getStatus()),
                    () -> Assertions.assertEquals(TYPE, product.getProductType()),
                    () -> Assertions.assertEquals(CREATED_AT, product.getCreatedAt()),
                    () -> Assertions.assertEquals(WEIGHT, ((PhysicalProduct) product).getWeight())
            );
        }

        // Verify that every required SQL field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required SQL data is null")
        void constructPhysicalProduct_nullRequiredData(
                String fieldName, String expectedMessage, Long productId,
                ProductName name, BigDecimal basePrice, ProductStatus status,
                ProductType type, Instant createdAt, BigDecimal weight
        ) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new PhysicalProduct(
                            productId, name, STOCK_QUANTITY, basePrice,
                            status, type, createdAt, weight
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("productId", "productId cannot be null", null,
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, CREATED_AT, WEIGHT),
                    Arguments.of("name", "Product name cannot be null", PRODUCT_ID,
                            null, BASE_PRICE, STATUS, TYPE, CREATED_AT, WEIGHT),
                    Arguments.of("basePrice", "Product base price cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, null, STATUS, TYPE, CREATED_AT, WEIGHT),
                    Arguments.of("status", "Product status cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, null, TYPE, CREATED_AT, WEIGHT),
                    Arguments.of("productType", "Product type cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, STATUS, null, CREATED_AT, WEIGHT),
                    Arguments.of("createdAt", "Timestampt createdAt cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, null, WEIGHT),
                    Arguments.of("weight", "Weight product cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, CREATED_AT, null)
            );
        }

        // Verify that inherited numeric fields and physical weight reject illegal values.
        @ParameterizedTest(name = "Reject invalid {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal SQL data")
        void constructPhysicalProduct_illegalArguments(
                String fieldName, String expectedMessage, int stockQuantity,
                BigDecimal basePrice, BigDecimal weight
        ) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new PhysicalProduct(
                            PRODUCT_ID, PRODUCT_NAME, stockQuantity, basePrice,
                            STATUS, TYPE, CREATED_AT, weight
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("negative stockQuantity", "Stock quantity cannot be negative",
                            -1, BASE_PRICE, WEIGHT),
                    Arguments.of("zero basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, BigDecimal.ZERO, WEIGHT),
                    Arguments.of("negative basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, new BigDecimal("-0.01"), WEIGHT),
                    Arguments.of("zero weight", "Invalid Weight product",
                            STOCK_QUANTITY, BASE_PRICE, BigDecimal.ZERO),
                    Arguments.of("negative weight", "Invalid Weight product",
                            STOCK_QUANTITY, BASE_PRICE, new BigDecimal("-0.01"))
            );
        }
    }

    @Nested
    @DisplayName("Create a new physical product")
    class CreateNewPhysicalProduct {
        // Verify that valid input creates a PhysicalProduct through its Product parent type.
        @Test
        @DisplayName("Construct a new physical product from valid data")
        void constructPhysicalProduct_validData() {
            Instant beforeCreation = Instant.now();
            Product product = new PhysicalProduct(
                    PRODUCT_NAME, STOCK_QUANTITY, BASE_PRICE, STATUS, TYPE, WEIGHT
            );
            Instant afterCreation = Instant.now();

            Assertions.assertAll(
                    () -> Assertions.assertInstanceOf(PhysicalProduct.class, product),
                    () -> Assertions.assertNull(product.getId()),
                    () -> Assertions.assertSame(PRODUCT_NAME, product.getName()),
                    () -> Assertions.assertEquals(STOCK_QUANTITY, product.getQuantity()),
                    () -> Assertions.assertEquals(BASE_PRICE, product.getBasePrice()),
                    () -> Assertions.assertEquals(STATUS, product.getStatus()),
                    () -> Assertions.assertEquals(TYPE, product.getProductType()),
                    () -> Assertions.assertFalse(product.getCreatedAt().isBefore(beforeCreation)),
                    () -> Assertions.assertFalse(product.getCreatedAt().isAfter(afterCreation)),
                    () -> Assertions.assertEquals(WEIGHT, ((PhysicalProduct) product).getWeight())
            );
        }

        // Verify that every required creation field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required data is null")
        void constructPhysicalProduct_nullRequiredData(
                String fieldName, String expectedMessage, ProductName name,
                BigDecimal basePrice, ProductStatus status, ProductType type,
                BigDecimal weight
        ) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new PhysicalProduct(
                            name, STOCK_QUANTITY, basePrice, status, type, weight
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("name", "Product name cannot be null",
                            null, BASE_PRICE, STATUS, TYPE, WEIGHT),
                    Arguments.of("basePrice", "Product base price cannot be null",
                            PRODUCT_NAME, null, STATUS, TYPE, WEIGHT),
                    Arguments.of("status", "Product status cannot be null",
                            PRODUCT_NAME, BASE_PRICE, null, TYPE, WEIGHT),
                    Arguments.of("productType", "Product type cannot be null",
                            PRODUCT_NAME, BASE_PRICE, STATUS, null, WEIGHT),
                    Arguments.of("weight", "Weight product cannot be null",
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, null)
            );
        }

        // Verify that inherited numeric fields and physical weight reject illegal values.
        @ParameterizedTest(name = "Reject {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal data")
        void constructPhysicalProduct_illegalArguments(
                String fieldName, String expectedMessage, int stockQuantity,
                BigDecimal basePrice, BigDecimal weight
        ) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new PhysicalProduct(
                            PRODUCT_NAME, stockQuantity, basePrice, STATUS, TYPE, weight
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("negative stockQuantity", "Stock quantity cannot be negative",
                            -1, BASE_PRICE, WEIGHT),
                    Arguments.of("zero basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, BigDecimal.ZERO, WEIGHT),
                    Arguments.of("negative basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, new BigDecimal("-0.01"), WEIGHT),
                    Arguments.of("zero weight", "Invalid Weight product",
                            STOCK_QUANTITY, BASE_PRICE, BigDecimal.ZERO),
                    Arguments.of("negative weight", "Invalid Weight product",
                            STOCK_QUANTITY, BASE_PRICE, new BigDecimal("-0.01"))
            );
        }
    }
}
