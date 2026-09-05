package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public class PhoneAlreadyInUseException extends BusinessException {
    public PhoneAlreadyInUseException() {
        super("This phone number has already been used");
    }
    public PhoneAlreadyInUseException(Throwable cause) {
        super("This phone number has already been used", cause);
    }
}
