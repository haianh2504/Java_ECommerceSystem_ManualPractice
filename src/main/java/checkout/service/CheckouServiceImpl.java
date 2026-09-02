package checkout.service;

import cart.entities.Cart;
import cart.repository.CartRepository;
import cart.service.CartManagementService;
import cart_item.entities.CartItem;
import cart_item.repository.CartItemRepository;
import cart_item.service.CartItemManagementService;
import checkout.entities.CheckoutItem;
import discount.entities.Discount;
import discount.service.DiscountService;
import order.entities.Order;
import order.service.OrderManagementService;
import order_item.service.OrderItemManagementService;
import product.entities.Product;
import product.service.ProductManagementService;
import shipping.ShippingStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//USER
// │
// │ checkout(cartId)
// ▼
//CHECKOUT SERVICE
// │
// ├── 1. Tìm Cart
// │
// ├── 2. Kiểm tra Cart thuộc User
// │
// ├── 3. Lấy tất cả CartItems
// │
// ├── 4. Validate Cart + Product + Stock
// │
// ├── 5. Calculate Subtotal
// │
// ├── 6. Calculate Shipping Fee
// │
// ├── 7. Calculate Discount
// │
// ├── 8. Calculate Total Price
// │
// ├── 9. Create Order
// │       status = PENDING_PAYMENT
// │
// ├── 10. Save Order
// │        → lấy generated orderId
// │
// ├── 11. CartItems → OrderItems
// │        → gắn orderId
// │
// ├── 12. Save OrderItems
// │
// ├── 13. Update Cart status
// │        ACTIVE → CHECKED_OUT
// │
// └── 14. Commit Transaction
public class CheckouServiceImpl implements CheckoutService {
    // repositories
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    // services
    private final CartManagementService cartManagementService;
    private final CartItemManagementService cartItemManagementService;
    private final ShippingStrategy shippingStrategy;
    private final DiscountService discountService;
    private final OrderManagementService orderManagementService;
    private final OrderItemManagementService orderItemManagementService;
    private final ProductManagementService productManagementService;
//    constructor
    public CheckouServiceImpl(CartRepository cartRepository,
                              CartItemRepository cartItemRepository,
                              CartManagementService cartManagementService,
                              CartItemManagementService cartItemManagementService,
                              ShippingStrategy shippingStrategy,
                              DiscountService discountService,
                              OrderManagementService orderManagementService,
                              OrderItemManagementService orderItemManagementService,
                              ProductManagementService productManagementService
                              ) {
        this.cartRepository = Objects.requireNonNull(cartRepository, "cartRepository cannot be null");
        this.cartItemRepository = Objects.requireNonNull(cartItemRepository, "cartItemRepository cannot be null");
        this.cartManagementService = Objects.requireNonNull(cartManagementService, "cartManagementService cannot be null");
        this.cartItemManagementService = Objects.requireNonNull(cartItemManagementService, "cartItemManagementService cannot be null");
        this.shippingStrategy = Objects.requireNonNull(shippingStrategy, "shippingStrategy cannot be null");
        this.discountService = Objects.requireNonNull(discountService, "discountService cannot be null");
        this.orderManagementService = Objects.requireNonNull(orderManagementService, "orderManagementService cannot be null");
        this.orderItemManagementService = Objects.requireNonNull(orderItemManagementService, "orderItemManagementService cannot be null");
        this.productManagementService = Objects.requireNonNull(productManagementService, "productManagementService cannot be null");
    }
//    check out
    @Override
    public Order checkout(Long userId, Long cartId) {
//        check null input
        Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(cartId, "cartId is required");
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart with id " + cartId + " not found"));
//        check ownership
        if(!Objects.equals(cart.getUserId(), userId)){
            throw new IllegalArgumentException("UserId does not match the cart");
        }
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
//        check if the cart is empty
        if(cartItemList.isEmpty()){
            throw new IllegalStateException("Cart has no items");
        }
//        check validate and get products
        List< CheckoutItem> checkoutItemList = new ArrayList<>();
        for(CartItem cartItem : cartItemList){
            Product product = cartItemManagementService.validatedCartItemToOrderItem(cartItem);
            checkoutItemList.add(new CheckoutItem(cartItem, product));
            // throw exception if
            // =>  product not exist | invalid quantity
        }
        // calculate subtotal
        BigDecimal subTotal = cartItemManagementService.calculateTotalPrice(cartItemList);
        // calculate shipping fee
        BigDecimal shippingFee = shippingStrategy.calculateShippingFee(cartItemList);
        // calculate discount
        BigDecimal discountAmount = discountService.calculateDiscountAmount(new Discount(subTotal,null));
        // calculate totalPrice
        BigDecimal totalPrice = subTotal.add(shippingFee).subtract(discountAmount);
        // create order
        Order newOrder =  orderManagementService.createOrder(userId,cartId,subTotal,shippingFee,discountAmount,totalPrice);
        // create order items
        int index = 0; // for product list
        for(CartItem cartItem : cartItemList){
            orderItemManagementService.createNewOrderItem(
                    newOrder.getOrderId(),
                    cartItem.getProductId(),
                    cartItem.getNumber(),
                    checkoutItemList.get(index++).product().getBasePrice()
            );
        }
        // change cart status into CHECKED_OUT
        cartManagementService.checkoutCart(cartId);
        // decrease stock quantity of the quantity
        for(CheckoutItem checkoutItem : checkoutItemList){
            productManagementService.decreaseStockQuantity(  // -> could throw exception
                    checkoutItem.product().getId(), // product Id
                    checkoutItem.cartItem().getNumber() // decreased number
            );
        }
        return newOrder;
    }
}
