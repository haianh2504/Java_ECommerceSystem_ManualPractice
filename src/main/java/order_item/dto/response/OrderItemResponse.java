package order_item.dto.response;

import order_item.entities.OrderItem;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderItemResponse(
        Long orderItemId,
        Long orderId,
        Long productId,
        int quantity,
        BigDecimal unitPrice
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        Objects.requireNonNull(orderItem, "OrderItem cannot be null");
        return new OrderItemResponse(
                orderItem.getOrderItemId(),
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }
}
