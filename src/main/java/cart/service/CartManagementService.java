package cart.service;

import cart.entities.Cart;

import java.math.BigDecimal;

public interface CartManagementService {
    // create new cart
    public Cart createCart(Long userId);
    // get cart by userID
    public Cart getCartByUserId(Long userId);
    // clear cart
    public void clearCart(Long cartId);
    // checkout cart
    public void checkoutCart(Long cartId);
}
