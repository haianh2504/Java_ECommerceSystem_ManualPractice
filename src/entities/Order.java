package entities;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Builder
public class Order {
    // identity
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.PENDING_PAYMENT;
    private String userId;
    private List<OrderItem> cartItemsList;
    // financial
    private BigDecimal subTotal; // total final price of all product
    private BigDecimal shippingFee;
    private BigDecimal discountAmount = BigDecimal.ZERO; // discount = 0 initially
    private BigDecimal totalPrice; // total price + shippingFee + discountAmount
//    constructor - full
    public Order(String orderId,
                 String userId,
                 List<OrderItem> cartItemsList,
                 BigDecimal subTotal,
                 BigDecimal shippingFee,
                 BigDecimal discountAmount
                 )
    {
//        check null
        this.orderId = Objects.requireNonNull(orderId, "Order Id cannot be null");
        this.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        this.cartItemsList = List.copyOf(Objects.requireNonNull(cartItemsList, "CartItemList cannot be null"));
        this.subTotal = Objects.requireNonNull(subTotal, "Subtotal cannot be null");
        this.shippingFee = Objects.requireNonNull(shippingFee, "Shipping cannot be null");
        this.discountAmount = Objects.requireNonNull(discountAmount, "DiscountAmount cannot be null");
        this.totalPrice = subTotal
                .add(shippingFee)
                .subtract(discountAmount);
//        check the existence of orderId, UserId
        if(subTotal.compareTo(BigDecimal.ZERO) <= 0){throw new IllegalArgumentException("Invalid Subtotal");}
        if(shippingFee.compareTo(BigDecimal.ZERO) <= 0){throw new IllegalArgumentException("Invalid shippingFee");}
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0){throw new IllegalArgumentException("Invalid discountAmount");}
    }

//    constructor - discountAmount not included
    public Order(String orderId,
                 String userId,
                 List<OrderItem> cartItemsList,
                 BigDecimal subTotal,
                 BigDecimal shippingFee
                 )
    {
//        check null
        this.orderId = Objects.requireNonNull(orderId, "Order Id cannot be null");
        this.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        this.cartItemsList = List.copyOf(Objects.requireNonNull(cartItemsList, "CartItemList cannot be null"));
        this.subTotal = Objects.requireNonNull(subTotal, "Subtotal cannot be null");
        this.shippingFee = Objects.requireNonNull(shippingFee, "Shipping cannot be null");
        this.totalPrice = subTotal
                .add(shippingFee)
                .subtract(discountAmount);
//        check the existence of orderId, UserId
        if(subTotal.compareTo(BigDecimal.ZERO) <= 0){throw new IllegalArgumentException("Invalid Subtotal");}
        if(shippingFee.compareTo(BigDecimal.ZERO) <= 0){throw new IllegalArgumentException("Invalid shippingFee");}
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0){throw new IllegalArgumentException("Invalid discountAmount");}
    }
//    getters
    public final String getOrderId(){return this.orderId;}
    public final OrderStatus getOrderStatus(){return this.orderStatus;}
    public final String getUserId(){return this.userId;}
    public final List<OrderItem> getCartItemsList(){
        return Collections.unmodifiableList(this.cartItemsList);
    }
    public final BigDecimal getSubTotal(){return this.subTotal;}
    public final BigDecimal getShippingFee(){return this.shippingFee;}
    public final BigDecimal getDiscountAmount(){return this.discountAmount;}
    public final BigDecimal getTotalPrice(){return this.totalPrice;}
}
