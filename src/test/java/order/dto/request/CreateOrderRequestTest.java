package order.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateOrderRequestTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void validRequestHasNoConstraintViolations() {
        CreateOrderRequest request = new CreateOrderRequest(1L, 10L);

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void nullIdsAreRejected() {
        CreateOrderRequest request = new CreateOrderRequest(null, null);

        assertEquals(Set.of("userId", "cartId"), invalidProperties(request));
    }

    @Test
    void zeroIdsAreRejected() {
        CreateOrderRequest request = new CreateOrderRequest(0L, 0L);

        assertEquals(Set.of("userId", "cartId"), invalidProperties(request));
    }

    @Test
    void negativeIdsAreRejected() {
        CreateOrderRequest request = new CreateOrderRequest(-1L, -10L);

        assertEquals(Set.of("userId", "cartId"), invalidProperties(request));
    }

    private Set<String> invalidProperties(CreateOrderRequest request) {
        return validator.validate(request).stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
}
