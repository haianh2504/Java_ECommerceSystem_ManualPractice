package cart_item.entities;

import java.util.Objects;

public class CartItem {
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private int number;
//    constructor for SQL return
    public CartItem(Long cartItemId, Long cartId, Long productId, int number)
    {
        if(cartItemId == null)
        {
            throw new NullPointerException("CartItem ID cannot be null");
        }
        Objects.requireNonNull(cartId, "Cart ID cannot be null");
        if(productId == null)
        {
            throw new NullPointerException("Product ID in ListItem cannot be null");
        }
        // need to check if the product Id exists
        if(number <= 0) // also need to check the upper bound
        {
            throw new IllegalArgumentException("The number in ListItem is invalid");
        }
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.number = number;
    }
//    constructor for creating one
    public CartItem(Long cartId, Long productId, int number)
    {
        this.cartId = Objects.requireNonNull(cartId, "Cart ID cannot be null");
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        if(number <= 0){
            throw new IllegalArgumentException("Invalid number");
        }
    }
//    getters
    public final Long getCartItemId(){return this.cartItemId;}
    public final Long getCartId(){return this.cartId;}
    public final Long getProductId()
    {
        return this.productId;
    }
    public final int getNumber()
    {
        return this.number;
    }
//    setter
    public void changeNumber(int number)
    {
        if(number <= 0 || number == this.number) // also need to check the upper bound
        {
            throw new IllegalArgumentException("The number is invalid");
        }
        this.number = number;
    }
    public void changeNumberByOne()
    {
        this.number += 1;
    }
}
