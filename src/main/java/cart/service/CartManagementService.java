package cart.service;

import cart.entities.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartManagementService {
    // create new cart
    public Cart createCart(Long userId);
    // get cart by cartId
    public Cart getCartById(Long cartId);
    // get list carts by userID
    public List<Cart> getCartByUserId(Long userId);
    // clear cart
    public void clearCart(Long cartId);
    // checkout cart
    public void checkoutCart(Long cartId);
}
