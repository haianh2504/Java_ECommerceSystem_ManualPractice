package services;

import entities.CartItem;

import java.math.BigDecimal;

public interface CartItemService {
//    check before add or change the quantity
    public boolean validateItemStock(String productId, int quantity);

//    add new cartItem in cart
    public void addNewCartItem(String cartId,String productId, int number);

//    delete cartItem in cart
    public boolean deleteCartItem(String cartItemId);

//    delete all cartItems in cart
    public void deleteAllCartItems(String cartId);

// find CartItem in cart
    public CartItem findCartItem(String cartId,String cartItemId);

//    increase quantity by one in cartItem
    public void changeQuantityByOne(String cartItemId);

//    calculate total price for a cartItem
    public BigDecimal getTotalPriceByCartItem(String cartId, String cartItemId);

//    update Item quantity
    public void updateItemQuantity(String cartItemId, int newQuantity);
}
