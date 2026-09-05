package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public final class UserNotAuthorizedException extends BusinessException {
    public UserNotAuthorizedException() {
        super("User is not authorized to perform this operation");
    }
    public UserNotAuthorizedException(Throwable cause) {
        super("User is not authorized to perform this operation", cause);
    }
}
