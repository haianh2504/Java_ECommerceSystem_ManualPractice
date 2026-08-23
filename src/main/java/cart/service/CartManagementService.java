package cart.service;

import cart.entities.Cart;

public interface CartManagementService {
//    Manage lifecyle of a cart

    // get cart by userID
    public Cart getCartByUserId(String userId);
    // clear cart
    public boolean clearCart(String cartId);
    // merge Anomynous Cart into


}
