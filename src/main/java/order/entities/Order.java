package order.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    // identity
    private Long orderId;
    private Long userId;
    private OrderStatus orderStatus; // initially PENDING
    private List<OrderItem> orderItemList;
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
            OrderStatus orderStatus,
            List<OrderItem> orderItemList,
            Instant createdAt,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount,
            BigDecimal totalPrice
    ) {
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.orderStatus = Objects.requireNonNull(orderStatus, "orderStatus cannot be null");
        this.orderItemList = List.copyOf(Objects.requireNonNull(orderItemList, "cartItemsList cannot be null"));
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.subTotal = Objects.requireNonNull(subTotal, "subTotal cannot be null");
        this.shippingFee = (shippingFee == null) ? BigDecimal.ZERO : shippingFee;
        this.discountAmount = (discountAmount == null) ? BigDecimal.ZERO : discountAmount;
        this.totalPrice = Objects.requireNonNull(totalPrice, "totalPrice cannot be null");
    }
//    constructor - initialization
    public Order(
            Long userId,
            OrderStatus orderStatus,
            List<OrderItem> orderItemList
    )
    {

    }
//    getters
    public final Long getOrderId(){return this.orderId;}
    public final OrderStatus getOrderStatus(){return this.orderStatus;}
    public final Long getUserId(){return this.userId;}
    public final List<OrderItem> getOrderItemList(){
        return Collections.unmodifiableList(this.orderItemList);
    }
    public final BigDecimal getSubTotal(){return this.subTotal;}
    public final BigDecimal getShippingFee(){return this.shippingFee;}
    public final BigDecimal getDiscountAmount(){return this.discountAmount;}
    public final BigDecimal getTotalPrice(){return this.totalPrice;}
}
