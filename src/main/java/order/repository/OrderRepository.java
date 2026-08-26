package order.repository;

import order.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
//    save Order
    public void save(Order order);
//    get Orders by userId
    public List<Order> findByUserId(Long userId);
//    get Order by orderId
    public Optional<Order> findByOrderId(Long orderId);
//    delete Order by Id
    public void deleteByOrderId(Long orderId);
}
