package exception.business.detailed_exceptions;

import exception.business.BusinessException;

public class UserAlreadyActive extends BusinessException {
    public UserAlreadyActive()
    {
      super("User is already active");
    }
    public UserAlreadyActive(Throwable cause)
    {
      super("User is already active", cause);
    }
}
