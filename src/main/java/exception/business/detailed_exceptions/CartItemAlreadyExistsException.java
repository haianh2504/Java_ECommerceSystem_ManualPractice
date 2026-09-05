package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class CartItemAlreadyExistsException extends BusinessException {
    public CartItemAlreadyExistsException(Long cartId, Long productId)
    {
        super(String.format(
                ("This cartItem has already existed in cart [id=%d] with the product [id=%d]"),cartId,productId
        ));
    }
    public CartItemAlreadyExistsException(Long cartId, Long productId, Throwable cause) {
        super(String.format(
                ("This cartItem has already existed in cart [id=%d] with the product [id=%d]"),cartId,productId
        ),cause);
    }
}
