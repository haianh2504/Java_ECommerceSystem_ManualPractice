package order_item.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private Long orderItemId;
    private Long orderId;
    private final Long productId;
    private int quantity;
    private final BigDecimal unitPrice;
//    constructor for SQL return
    public OrderItem(Long orderItemId, Long orderId, Long productId, int quantity, BigDecimal unitPrice)
    {
        this.orderItemId = Objects.requireNonNull(orderItemId, "orderItemId cannot be null");
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");
        if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
        if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
    }
//    constructor for creating one
public OrderItem(Long orderId, Long productId, int quantity, BigDecimal unitPrice)
{
    this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
    this.productId = Objects.requireNonNull(productId, "ProductID cannot be null");
    if(quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
    this.quantity = quantity;
    this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice cannot be null");
    if(unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid UnitPrice");
}

//    getters
    public final Long getOrderItemId() {return this.orderItemId;}
    public final Long getOrderId() {return this.orderId;}
    public final Long getProductId() {
        return this.productId;
    }
    public final int getQuantity() {
        return quantity;
    }
    public final BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
