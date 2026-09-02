package exception.resource.detailed_exceptions;

import exception.resource.ResourceException;

public class CartNotFoundException extends ResourceException {
    public CartNotFoundException(Long id) {
        super("Cart with id " + id + " not found");
    }
    public CartNotFoundException(Long id, Throwable cause) {
        super("Cart with id " + id + " not found", cause);
    }
}
