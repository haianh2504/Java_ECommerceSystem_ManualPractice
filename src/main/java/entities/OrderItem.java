package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final String productId;
    private final String productName;
    private int quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal discountAmount; // should be Discount discount
//    constructor - full
    public OrderItem(String productId, String productName, int quantity, BigDecimal unitPrice, BigDecimal discountAmount)
    {
        this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");
        this.productName = Objects.requireNonNull(productName, "ProductName cannot be null");
        if(productName.isBlank()) throw new IllegalArgumentException("ProductName cannot be blank");
        if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
        if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
        this.discountAmount = Objects.requireNonNull(discountAmount, "DiscountAmount cannot be null");
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Invalid discount Amount");
    }
//    constructor - without discount
public OrderItem(String productId, String productName, int quantity, BigDecimal unitPrice)
{
    this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");
    this.productName = Objects.requireNonNull(productName, "ProductName cannot be null");
    if(productName.isBlank()) throw new IllegalArgumentException("ProductName cannot be blank");
    if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
    this.quantity = quantity;
    this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
    if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
    this.discountAmount = BigDecimal.ZERO;
}

//    getters
    public final String getProductId() {
        return productId;
    }
    public final String getProductName() {
        return productName;
    }
    public final int getQuantity() {
        return quantity;
    }
    public final BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public final BigDecimal getDiscountAmount() {
        return discountAmount;
    }
}
