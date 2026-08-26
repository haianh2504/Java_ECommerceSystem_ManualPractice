package order.service;

import order.entities.Order;
import order.entities.OrderStatus;
import order.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OrderManagementServiceImpl implements OrderManagementService {
    private final OrderRepository orderRepository;
//    constructor
    public OrderManagementServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository must not be null");
    }
//    create Order
    @Override
    public Order createOrder(
            Long userId,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount
    ) {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(subTotal, "subTotal must not be null");
        Objects.requireNonNull(shippingFee, "shippingFee must not be null");
        Objects.requireNonNull(discountAmount, "discountAmount must not be null");
        if(subTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("subTotal must be greater than zero");
        }
        if(shippingFee.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("shippingFee must be greater than zero");
        }
        if(discountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("discountAmount must be greater than zero");
        }
        Order order = new Order(
                userId,
                subTotal,
                shippingFee,
                discountAmount
        );
        return order;
    }
//    get list of orders by userId
    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        Objects.requireNonNull(userId, "userId must not be null");
        return orderRepository.findByUserId(userId);
    }
//    delete order by id
    public void deleteOrderById(Long orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        orderRepository.deleteByOrderId(orderId);
    }
//    get order by order id
    @Override
    public Order getOrderById(Long orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(
                        () -> new RuntimeException("Order with id " + orderId + " not found")
                );
    }
}
