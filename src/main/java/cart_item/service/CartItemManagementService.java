package cart_item.service;

import cart_item.entities.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemManagementService {
//    get cartItems by cartId
    public List<CartItem> getCartItemsByCartId(Long cartId);

//    add new cartItem in cart
    public void addNewCartItem(Long cartId,Long productId, int number);

//    delete cartItem in cart
    public void deleteCartItem(Long cartItemId);

//    delete all cartItems in cart
    public void clearCart(Long cartId);

//    find CartItem by cart ID and product ID in cart
    public CartItem getCartItemByCartIdAndProductId(Long cartId,Long productId);

//    update Item quantity
    public void updateItemQuantity(Long cartItemId, int newQuantity);
}
