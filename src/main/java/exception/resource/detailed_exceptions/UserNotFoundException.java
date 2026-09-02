package exception.resource.detailed_exceptions;

import exception.resource.ResourceException;
import user.entities.Email;

public class UserNotFoundException extends ResourceException {
    // id
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
    public UserNotFoundException(Long id, Throwable cause) {
        super("User with id " + id + " not found", cause);
    }

    // email
    public UserNotFoundException(Email email) {
        super("User with email " + email.toString() + " not found");
    }
    public UserNotFoundException(Email email, Throwable cause) {
        super("User with email " + email.toString() + " not found", cause);
    }
}
