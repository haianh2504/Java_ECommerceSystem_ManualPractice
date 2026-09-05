package exception.business.detailed_exceptions;

public class AccountBannedException extends RuntimeException {
    public AccountBannedException() {
        super("This account has already been banned.");
    }
    public AccountBannedException(Throwable cause) {
        super("This account has already been banned.", cause);
    }
}
