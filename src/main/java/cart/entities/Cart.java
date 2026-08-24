package cart.entities;

import cart_item.entities.CartItem;

import java.time.Instant;
import java.util.*;

public class Cart{
    private Long cartId;
    private Long userId;
    private Instant createdAt;
    private Map<Long, CartItem> cartItems;
//    constructor for SQL return
    public Cart(Long cartId, Long userId, Instant createdAt) // with list Items
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
        this.createdAt = Objects.requireNonNull(createdAt, "Timestamp createdAt cannot be null");
        this.cartItems = new HashMap<>();
    }
//    constructor for creating new one
    public Cart(Long userId)
    {
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.createdAt = Instant.now();
    }
//    getters
    public final Long getCartId()
    {
        return this.cartId;
    }
    public final Long getUserId()
    {
        return this.userId;
    }
    public Map<Long,CartItem> getListItems() {
        Map<Long, CartItem> deepCopyMap = new HashMap<>();
        for(Map.Entry<Long,CartItem> e: cartItems.entrySet())
                {
                    deepCopyMap.put(e.getKey(), new CartItem(
                            e.getValue().getCartItemId(),
                            e.getValue().getProductId(),
                            e.getValue().getNumber()));
                }
        return Collections.unmodifiableMap(deepCopyMap);
    }
    public final Instant getCreatedAt() {return this.createdAt;}
//    add new cartItem in cart
    public void addNewCartItem(Long cartItemId,Long productId, int number)
    {
        if(cartItemId == null){
            throw new NullPointerException("CartItemID cannot be null");
        }
        if(productId == null){
            throw new NullPointerException("ProductId cannot be null");
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
    public boolean changeQuantity(Long cartItemId, int newNumber){
        if(cartItemId == null){
            throw new NullPointerException("CartItemID cannot be null");
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
    public boolean deleteCartItem(Long cartItemId){
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
