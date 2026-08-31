package order.repository;

import order.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
//    save Order
    public Order save(Order order);
//    get Orders by userId
    public List<Order> findByUserId(Long userId);
//    get Orders by userId and CartId
    public Optional<Order> findByUserIdAndCartId(Long userId, Long cartId);
//    get Order by orderId
    public Optional<Order> findByOrderId(Long orderId);
//    delete Order by Id
    public void deleteByOrderId(Long orderId);
//    update
    public void update(Order order);
}
