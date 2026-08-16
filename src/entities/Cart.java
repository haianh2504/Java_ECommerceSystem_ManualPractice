package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private String cartId;
    private String userId;
    private List<CartItem> cartItems;
//    constructor
    public Cart(String cartId, String userId, List<CartItem> cartItems) // with list Items
    {
        if(cartId == null)
        {
            throw new NullPointerException("Cart ID cannot be null");
        }
        if(userId == null)
        {
            throw new NullPointerException("User ID in cart cannot be null");
        }
        if(cartItems == null)
        {
            throw new NullPointerException("ListItems input cannot be null");
        }
        this.cartId = cartId;
        this.userId = userId;
        this.cartItems = Collections.unmodifiableList(new ArrayList<>(cartItems));
    }
    public Cart(String cartId, String userId) // without list Items
    {
        if(cartId == null)
        {
            throw new NullPointerException("Cart ID cannot be null");
        }
        if(userId == null)
        {
            throw new NullPointerException("User ID in cart cannot be null");
        }
        // also need to check if userID exists
        this.cartId = cartId;
        this.userId = userId;
        cartItems = new ArrayList<>();
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
    public List<CartItem> getListItems() {
        return Collections.unmodifiableList(new ArrayList<>(this.cartItems));
    }
//
}
