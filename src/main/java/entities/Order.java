package entities;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    // identity
    private String orderId;
    private OrderStatus orderStatus; // initially PENDING
    private String userId;
    private List<OrderItem> cartItemsList;
    // financial
    private BigDecimal subTotal; // total final price of all product
    private BigDecimal shippingFee;
    private BigDecimal discountAmount; // discount = 0 initially
    private BigDecimal totalPrice; // total price + shippingFee + discountAmount
//    constructor rút gọn
    public Order(
            String orderId,
            String userId,
            List<OrderItem> cartItemsList,
            BigDecimal subTotal,
            BigDecimal shippingFee
    ){
        // gọi đến constructor tổng
        this(orderId,OrderStatus.PENDING_PAYMENT,userId,cartItemsList,subTotal,shippingFee,BigDecimal.ZERO);
    }
    public Order(
            String orderId,
            String userId,
            List<OrderItem> cartItemsList,
            BigDecimal subTotal,
            BigDecimal shippingFee,
            BigDecimal discountAmount
    ){
        this(orderId,OrderStatus.PENDING_PAYMENT,userId,cartItemsList,subTotal,shippingFee,discountAmount);
    }
//    constructor - full
    @Builder
    public Order(String orderId,
                 OrderStatus orderStatus,
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
        this.discountAmount = (discountAmount == null) ? BigDecimal.ZERO : discountAmount;
        this.totalPrice = subTotal
                .add(shippingFee)
                .subtract(discountAmount);
//        check the existence of orderId, UserId
        if(cartItemsList.isEmpty()) throw new IllegalArgumentException("Cart items list cannot be empty");
        if(subTotal.compareTo(BigDecimal.ZERO) <= 0){throw new IllegalArgumentException("Subtotal must greater than ZERO");}
        if(shippingFee.compareTo(BigDecimal.ZERO) < 0){throw new IllegalArgumentException("ShippingFee must greater than ZERO");}
        if(discountAmount.compareTo(BigDecimal.ZERO) < 0){throw new IllegalArgumentException("Invalid discountAmount");}
        if(totalPrice.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Total price cannot be negative after discount");
        }
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
