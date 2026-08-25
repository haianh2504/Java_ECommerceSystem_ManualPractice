package cart.entities;

import cart_item.entities.CartItem;

import java.time.Instant;
import java.util.*;

public class Cart{
    private Long cartId;
    private Long userId;
    private Instant createdAt;
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
    public final Instant getCreatedAt() {return this.createdAt;}
}
