package checkout;

import order.entities.Order;

public interface CheckoutService {
//    check out by cartId
    public Order checkout(Long userId, Long cartId);

}
