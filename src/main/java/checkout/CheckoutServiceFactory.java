package checkout;

import cart.repository.CartRepository;
import cart.repository.JdbcCartRepository;
import cart.service.CartManagementService;
import cart.service.CartManagementServiceImpl;
import cart_item.repository.CartItemRepository;
import cart_item.repository.JdbcCartItemRepository;
import cart_item.service.CartItemManagementService;
import cart_item.service.CartItemManagementServiceImpl;
import checkout.service.CheckoutService;
import checkout.service.CheckoutServiceImpl;
import discount.service.DiscountService;
import order.repository.JdbcOrderRepository;
import order.repository.OrderRepository;
import order.service.OrderManagementService;
import order.service.OrderManagementServiceImpl;
import order_item.repository.JdbcOrderItemRepository;
import order_item.repository.OrderItemRepository;
import order_item.service.OrderItemManagementService;
import order_item.service.OrderItemManagementServiceImpl;
import product.repository.JdbcProductRepository;
import product.repository.ProductRepository;
import product.service.ProductManagementService;
import product.service.ProductManagementServiceImpl;
import shipping.ShippingStrategy;
import transaction_management.TransactionManagement;

import java.sql.Connection;
import java.util.Objects;

/**
 * Composition root for the checkout use case.
 *
 * <p>Every JDBC repository and {@link TransactionManagement} receives the same
 * connection. JDBC transaction state belongs to a connection, so sharing it is
 * what makes order creation, stock changes and the cart transition one atomic unit.</p>
 */
public final class CheckoutServiceFactory {
    private CheckoutServiceFactory() {
    }

    public static CheckoutService create(
            Connection connection,
            ShippingStrategy shippingStrategy,
            DiscountService discountService
    ) {
        Objects.requireNonNull(connection, "connection cannot be null");
        Objects.requireNonNull(shippingStrategy, "shippingStrategy cannot be null");
        Objects.requireNonNull(discountService, "discountService cannot be null");

        CartRepository cartRepository = new JdbcCartRepository(connection);
        CartItemRepository cartItemRepository = new JdbcCartItemRepository(connection);
        ProductRepository productRepository = new JdbcProductRepository(connection);
        OrderRepository orderRepository = new JdbcOrderRepository(connection);
        OrderItemRepository orderItemRepository = new JdbcOrderItemRepository(connection);

        CartManagementService cartService =
                new CartManagementServiceImpl(cartRepository, cartItemRepository);
        CartItemManagementService cartItemService =
                new CartItemManagementServiceImpl(cartItemRepository, productRepository);
        ProductManagementService productService =
                new ProductManagementServiceImpl(productRepository);
        OrderManagementService orderService =
                new OrderManagementServiceImpl(orderRepository, shippingStrategy);
        OrderItemManagementService orderItemService =
                new OrderItemManagementServiceImpl(orderItemRepository, orderRepository);

        return new CheckoutServiceImpl(
                cartService,
                cartItemService,
                shippingStrategy,
                discountService,
                orderService,
                orderItemService,
                productService,
                new TransactionManagement(connection)
        );
    }
}
