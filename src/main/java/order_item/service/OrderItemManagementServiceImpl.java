package order_item.service;

import exception.business.detailed_exceptions.OrderItemAlreadyExistsException;
import exception.resource.detailed_exceptions.OrderItemNotFoundException;
import exception.resource.detailed_exceptions.OrderNotFoundException;
import order_item.entities.OrderItem;
import order_item.repository.OrderItemRepo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderItemManagementServiceImpl implements OrderItemManagementService{
    private final OrderItemRepo orderItemRepo;
//    constructor
    public OrderItemManagementServiceImpl(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = Objects.requireNonNull(orderItemRepo, "orderItemRepo must not be null");
    }
//    create new OrderItem
    @Override
    public OrderItem createNewOrderItem(Long orderId, Long productId, int quantity, BigDecimal unit_price) {
        Objects.requireNonNull(productId, "productId must not be null");
        Objects.requireNonNull(orderId, "orderId must not be null");
        if(quantity <= 0){
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
        Objects.requireNonNull(unit_price, "unit_price must not be null");
        if(unit_price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("unit_price must be greater than 0");
        }
        // check for existence
        Optional<OrderItem> orderItem = orderItemRepo.findByOrderIdAndProductId(orderId,productId);
        if(orderItem.isPresent()){
            throw new OrderItemAlreadyExistsException(orderId,productId);
        }
        OrderItem orderItem1 = new OrderItem(orderId, productId, quantity, unit_price);
        orderItemRepo.save(orderItem1);
        return orderItem1;
    }
//    delete Order Item
    @Override
    public void deleteOrderItem(Long orderId, Long productId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        Objects.requireNonNull(productId, "productId must not be null");
        // check for existence
        OrderItem orderItem = orderItemRepo.findByOrderIdAndProductId(orderId,productId)
                .orElseThrow(() -> OrderItemNotFoundException.byOrderIdAndProductId(orderId, productId));
        orderItemRepo.delete(orderId,productId);
    }
//    get OrderItem by orderId and ProductId
    @Override
    public OrderItem getOrderItemById(Long orderId, Long productId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        Objects.requireNonNull(productId, "productId must not be null");
        return orderItemRepo.findByOrderIdAndProductId(orderId,productId)
                .orElseThrow(() -> OrderItemNotFoundException.byOrderIdAndProductId(orderId, productId)
                );
    }
//    get list of order items by orderId
    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        return Collections.unmodifiableList(
                orderItemRepo.findByOrderId(orderId)
        );
    }
//    calculate total price of an order items
    @Override
    public BigDecimal getTotalPrice(OrderItem orderItem) {
        Objects.requireNonNull(orderItem, "orderItem must not be null");
        if(orderItemRepo.findByOrderIdAndProductId(orderItem.getOrderId(), orderItem.getProductId()).isEmpty()){
            throw OrderItemNotFoundException.byOrderIdAndProductId(orderItem.getOrderItemId(),orderItem.getProductId());
        }
        return orderItem.getUnitPrice().multiply(
                new BigDecimal(orderItem.getQuantity())
        );
    }
}
