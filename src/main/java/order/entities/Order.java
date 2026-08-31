package order.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    // identity
    private Long orderId;
    private Long cartId;
    private Long userId;
    private OrderStatus orderStatus; // initially PENDING
    private Instant createdAt;
    // financial
    private BigDecimal subTotal; // total final price of all product
    private BigDecimal shippingFee;
    private BigDecimal discountAmount; // discount = 0 initially
    private BigDecimal totalPrice; // total price + shippingFee + discountAmount
//    constructor - SQL return
    public Order(
            Long orderId,
            Long userId,
            Long cartId,
            OrderStatus orderStatus,
            Instant createdAt,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount,
            BigDecimal totalPrice
    ) {
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        this.cartId = Objects.requireNonNull(orderId, "cartId cannot be null");
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.orderStatus = Objects.requireNonNull(orderStatus, "orderStatus cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.subTotal = Objects.requireNonNull(subTotal, "subTotal cannot be null");
        this.shippingFee = (shippingFee == null) ? BigDecimal.ZERO : shippingFee;
        this.discountAmount = (discountAmount == null) ? BigDecimal.ZERO : discountAmount;
        this.totalPrice = Objects.requireNonNull(totalPrice, "totalPrice cannot be null");
    }
//    constructor for creating one
    public Order(
            Long userId,
            Long cartId,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount,
            BigDecimal totalPrice
    )
    {
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.cartId = Objects.requireNonNull(cartId, "cartId cannot be null");
        this.subTotal = Objects.requireNonNull(subTotal, "subTotal cannot be null");
        this.shippingFee = (shippingFee == null) ? BigDecimal.ZERO : shippingFee;
        this.discountAmount = (discountAmount == null) ? BigDecimal.ZERO : discountAmount;
        this.orderStatus = OrderStatus.PENDING_PAYMENT;
        this.createdAt = Instant.now();
        this.totalPrice = Objects.requireNonNull(totalPrice, "totalPrice cannot be null");
        if(subTotal.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("subTotal must be greater than zero");
        }
        if(shippingFee.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("shippingFee must not be negative");
        }
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("discountAmount must not be negative");
        }
    }
//    getters
    public final Long getOrderId(){return this.orderId;}
    public final Long getCartId(){return this.cartId;}
    public final OrderStatus getOrderStatus(){return this.orderStatus;}
    public final Long getUserId(){return this.userId;}
    public final BigDecimal getSubTotal(){return this.subTotal;}
    public final BigDecimal getShippingFee(){return this.shippingFee;}
    public final BigDecimal getDiscountAmount(){return this.discountAmount;}
    public final BigDecimal getTotalPrice(){return this.totalPrice;}
    public final Instant getCreatedAt(){return this.createdAt;}
}
