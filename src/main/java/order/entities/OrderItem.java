package order.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private Long orderItemId;
    private Long orderId;
    private final String productId;
    private int quantity;
    private final BigDecimal unitPrice;
//    constructor - full
    public OrderItem(String productId, String productName, int quantity, BigDecimal unitPrice, BigDecimal discountAmount)
    {
        this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");
        if(productName.isBlank()) throw new IllegalArgumentException("ProductName cannot be blank");
        if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
        if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Invalid discount Amount");
    }
//    constructor - without discount
public OrderItem(String productId, String productName, int quantity, BigDecimal unitPrice)
{
    this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");

    if(productName.isBlank()) throw new IllegalArgumentException("ProductName cannot be blank");
    if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
    this.quantity = quantity;
    this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
    if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
}

//    getters
    public final String getProductId() {
        return productId;
    }
    public final int getQuantity() {
        return quantity;
    }
    public final BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
