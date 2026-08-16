package services;

import entities.CartItem;

import java.math.BigDecimal;

public interface CartItemService {

//    add new cartItem in cart
    public CartItem addNewCartItem(String productId, int number);

//    delete cartItem in cart
    public boolean deleteCartItem(String cartId, String cartItemId);

//    delete all cartItems in cart
    public boolean deleteAllCartItems(String cartId);

// find CartItem in cart
    public CartItem findCartItem(String cartItemId);

//    change quantity in cartItem
    public boolean changeQuantity(String cartId,String cartItemId, int newNumber);

//    increase quantity by one in cartItem
    public boolean changeQuantityByOne(String cartId, String cartItemId);

//    calculate total price for a cartItem
    public BigDecimal getTotalPriceByCartItem(String cartId, String cartItemId);
}
