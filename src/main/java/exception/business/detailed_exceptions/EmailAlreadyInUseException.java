package exception.business.detailed_exceptions;
import exception.business.BusinessException;

public final class EmailAlreadyInUseException extends BusinessException {
    public  EmailAlreadyInUseException() {
        super("This email has already been used");
    }
    public EmailAlreadyInUseException(Throwable cause) {
        super("This email has already been used", cause);
    }
}
