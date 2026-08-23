package services.CartItemManagement;

import entities.CartItem;

import java.math.BigDecimal;

public interface CartItemManagementService {

//    add new cartItem in cart
    public void addNewCartItem(Long cartId,Long productId, int number);

//    delete cartItem in cart
    public boolean deleteCartItem(Long cartItemId);

//    delete all cartItems in cart
    public void deleteAllCartItems(Long cartId);

// find CartItem by ID in cart
    public CartItem findCartItemById(Long cartId,String cartItemId);

//    increase quantity by one in cartItem
    public void changeQuantityByOne(Long cartItemId);

//    calculate total price for a cartItem
    public BigDecimal getTotalPriceByCartItem(Long cartId, String cartItemId);

//    update Item quantity
    public void updateItemQuantity(Long cartItemId, int newQuantity);
}
