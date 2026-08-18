package services;

import entities.Cart;
import entities.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {
//    Manage lifecyle of a cart

    // get cart by userID
    public Cart getCartByUserId(String userId);
    // clear cart
    public boolean clearCart(String cartId);
    // merge Anomynous Cart into


}
