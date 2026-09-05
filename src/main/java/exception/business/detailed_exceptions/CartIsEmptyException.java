package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class CartIsEmptyException extends BusinessException {
    public CartIsEmptyException() {
        super("This cart is empty");
    }
    public CartIsEmptyException(Throwable cause) {
        super("This cart is empty", cause);
    }
}
