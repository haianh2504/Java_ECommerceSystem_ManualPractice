package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class ProductNameAlreadyBeenInUseException extends BusinessException {
    public ProductNameAlreadyBeenInUseException() {
        super("This product name has already been used");
    }
    public ProductNameAlreadyBeenInUseException(Throwable cause) {
        super("This product name has already been used", cause);
    }
}
