package exception.business.detailed_exceptions;

public final class CartAlreadyCheckedOutException extends RuntimeException {
    public CartAlreadyCheckedOutException() {
        super("This cart has already been checked out");
    }
    public CartAlreadyCheckedOutException(Throwable cause) {
        super("This cart has already been checked out", cause);
    }
}
