package order.service;

import order.entities.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderManagementService {
//    create new Order
    public Order createOrder(
            Long userId,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount
            );
//    get List of orders by userId
    public List<Order> getAllOrdersByUserId(Long userId);
//    delete order
    public void deleteOrderById(Long orderId);
//    get order by orderId
    public Order getOrderById(Long orderId);
}
