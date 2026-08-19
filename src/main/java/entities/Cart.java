package entities;

import java.util.*;

public class Cart{
    private String cartId;
    private String userId;
    private Map<String, CartItem> cartItems;
//    constructor
    public Cart(String cartId, String userId) // with list Items
    {
        if(cartId == null)
        {
            throw new NullPointerException("Cart ID cannot be null");
        }
        if(userId == null)
        {
            throw new NullPointerException("User ID in cart cannot be null");
        }
        this.cartId = cartId;
        this.userId = userId;
        this.cartItems = new HashMap<>();
    }
//    getters
    public final String getCartId()
    {
        return this.cartId;
    }
    public final String getUserId()
    {
        return this.userId;
    }
    public Map<String,CartItem> getListItems() {
        Map<String, CartItem> deepCopyMap = new HashMap<>();
        for(Map.Entry<String,CartItem> e: cartItems.entrySet())
                {
                    deepCopyMap.put(e.getKey(), new CartItem(
                            e.getValue().getCartItemId(),
                            e.getValue().getProductId(),
                            e.getValue().getNumber()));
                }
        return Collections.unmodifiableMap(deepCopyMap);
    }
//    add new cartItem in cart
    public void addNewCartItem(String cartItemId,String productId, int number)
    {
        if(cartItemId == null){
            throw new NullPointerException("CartItemID cannot be null");
        }
        else if(cartItemId.isBlank())
        {
            throw new IllegalArgumentException("CartItemID cannot be blank");
        }
        if(productId == null){
            throw new NullPointerException("ProductId cannot be null");
        }
        else if(productId.isBlank())
        {
            throw new IllegalArgumentException("ProductID cannot be blank");
        }
        if(number <= 0)
        {
            throw new IllegalArgumentException("Number cannot under ZERO");
        }
        CartItem newItem = new CartItem(cartItemId,productId,number);
        cartItems.put(cartItemId, newItem);
    }
//    find CartItem in cart
    public CartItem findCartItem(String cartItemId){
        for(int i = 0; i < cartItems.size(); i++)
        {
            if(cartItems.get(i).getCartItemId().equals(cartItemId))
            {
                return cartItems.get(i);
            }
        }
        return null;
    }
//    change quantity in cartItem
    public boolean changeQuantity(String cartItemId, int newNumber){
        if(cartItemId == null){
            throw new NullPointerException("CartItemID cannot be null");
        }
        else if(cartItemId.isBlank())
        {
            throw new IllegalArgumentException("CartItemID cannot be blank");
        }
        if(newNumber <= 0){
            throw new IllegalArgumentException("Number cannot be under ZERO");
        }
        boolean exist = cartItems.containsKey(cartItemId);
        if(exist)
        {
            CartItem holder = cartItems.get(cartItemId);
            holder.changeNumber(newNumber); // could have exception
            return true;
        }
        return false;
    }
//    delete cartItem in cart
    public boolean deleteCartItem(String cartItemId){
        boolean exist = cartItems.containsKey(cartItemId);
        if(exist)
        {
            cartItems.remove(cartItemId);
            return true;
        }
        return false;
    }
//    delete all cartItems in cart
    public void deleteAllCartItems()
    {
        cartItems.clear();
    }
//    increase quantity by one in cartItem
    public void changeQuantityByOne(String cartItemId){
        if(cartItemId == null){
            throw new NullPointerException("CartItemID cannot be null");
        }
        else if(cartItemId.isBlank())
        {
            throw new IllegalArgumentException("CartItemID cannot be blank");
        }
        CartItem holder = cartItems.get(cartItemId);
        if(holder == null){
            throw new NullPointerException("CartItem not found");
        }
        holder.changeNumberByOne();
    }
}
