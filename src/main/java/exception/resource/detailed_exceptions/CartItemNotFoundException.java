package exception.resource.detailed_exceptions;

import exception.resource.ResourceException;

public class CartItemNotFoundException extends ResourceException {
    public CartItemNotFoundException(Long id) {
        super("cart item with id " + id + " not found");
    }
    public CartItemNotFoundException(Long id, Throwable cause) {
        super("cart item with id " + id + " not found", cause);
    }
}
