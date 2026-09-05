package exception.business.detailed_exceptions;

public final class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException() {
        super("Authentication is required to access this resource");
    }
    public UserNotAuthorizedException(Throwable cause) {
        super("Authentication is required to access this resource", cause);
    }
}
