package shipping;

import cart_item.entities.CartItem;
import cart_item.repository.CartItemRepository;
import product.entities.Product;
import product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class WeightBasedStrategy implements ShippingStrategy {
    private final BigDecimal price_per_unit;
//    constructor
    public WeightBasedStrategy(BigDecimal price_per_unit) {
        this.price_per_unit = Objects.requireNonNull(price_per_unit);
        if(price_per_unit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price per unit must be greater than zero");
        }
    }
//    calculate Fee
    @Override
    public BigDecimal calculateShippingFee(List<CartItem> cartItems) {
        Objects.requireNonNull(cartItems, "cartItems must not be null");
        BigDecimal totalWeight = BigDecimal.ZERO;
        for(int i =0; i < cartItems.size(); i++)
        {
            totalWeight = totalWeight.add(
                    BigDecimal.valueOf(
                            cartItems.get(i).getNumber()
                    )
            );
        }
        return price_per_unit.multiply(totalWeight);
    }
}
