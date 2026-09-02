package exception.resource.detailed_exceptions;

import exception.resource.ResourceException;

public class ProductNotFoundException extends ResourceException
{
        public ProductNotFoundException(Long id)
        {
            super("Product with id " + id + " not found");
        }
        public ProductNotFoundException(Long id,Throwable cause)
        {
            super("Product with id " + id + " not found", cause);
        }
}
