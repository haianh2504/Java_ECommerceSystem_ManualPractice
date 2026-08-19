package services;

import entities.Cart;

public interface CartService {
//    Manage lifecyle of a cart

    // get cart by userID
    public Cart getCartByUserId(String userId);
    // clear cart
    public boolean clearCart(String cartId);
    // merge Anomynous Cart into


}
