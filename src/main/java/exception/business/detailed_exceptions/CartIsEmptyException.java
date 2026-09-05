package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class CartIsEmptyException extends BusinessException {
    public CartIsEmptyException() {
        super("This cart item has already been empty");
    }
    public CartIsEmptyException(Throwable cause) {
        super("This cart item has already been empty", cause);
    }
}
