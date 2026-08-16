package services;

import entities.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

//    calculate total price for a cart
    public BigDecimal getTotalPriceByCart(String cartId, List<CartItem> cartItems);

//    clear cart after finishing paying

//    create cart when a user register
}
