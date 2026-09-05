package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public class AccountBannedException extends BusinessException {
    public AccountBannedException() {
        super("This account has already been banned.");
    }
    public AccountBannedException(Throwable cause) {
        super("This account has already been banned.", cause);
    }
}
