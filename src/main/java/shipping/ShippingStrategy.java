package shipping;

import cart_item.entities.CartItem;
import order_item.entities.OrderItem;

import java.math.BigDecimal;
import java.util.List;

// Applying Design patten -> "Strategy Pattern"
public interface ShippingStrategy {
    public BigDecimal calculateShippingFee(List<CartItem> cartItems);
}
