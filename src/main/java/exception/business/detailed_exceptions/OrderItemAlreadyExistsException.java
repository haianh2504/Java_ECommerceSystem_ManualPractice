package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class OrderItemAlreadyExistsException extends BusinessException {
    public OrderItemAlreadyExistsException(Long orderId, Long productId)
    {
        super("This order item has already existed in orderId: " + orderId.toString() + "with the productId: " + productId.toString());
    }
    public OrderItemAlreadyExistsException(Long orderId, Long productId,Throwable cause) {
        super("This order item has already existed in orderId: " + orderId.toString() + "with the productId: " + productId.toString(), cause);
    }
}
