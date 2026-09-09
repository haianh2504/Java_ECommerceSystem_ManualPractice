package payment_method.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

public class CashPaymentMethodTest {
    private static final String PAYMENT_ID = "payment-1";
    private static final String USER_ID = "user-1";
    private static final PaymentMethodStatus STATUS = PaymentMethodStatus.ACTIVE;
    private static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");

    // Verify required values in the normal constructor reject null.
    @ParameterizedTest(name = "Reject null {0}")
    @MethodSource("normalNullArguments")
    @DisplayName("Throw NullPointerException for null creation data")
    void normalConstructor_nullData(String field, String message, String paymentId,
                                    String userId, PaymentMethodStatus status) {
        NullPointerException exception = Assertions.assertThrows(
                NullPointerException.class,
                () -> new CashPaymentMethod(paymentId, userId, status)
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> normalNullArguments() {
        return Stream.of(
                Arguments.of("paymentId", "PaymentMethodID cannot be null", null, USER_ID, STATUS),
                Arguments.of("userId", "UserID cannot be null", PAYMENT_ID, null, STATUS),
                Arguments.of("status", "PaymentMethodStatus cannot be null", PAYMENT_ID, USER_ID, null)
        );
    }

    // Verify blank identifiers in the normal constructor are rejected.
    @ParameterizedTest(name = "Reject blank {0}")
    @MethodSource("blankArguments")
    @DisplayName("Throw IllegalArgumentException for blank creation data")
    void normalConstructor_blankData(String field, String message,
                                     String paymentId, String userId) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CashPaymentMethod(paymentId, userId, STATUS)
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    // Verify the SQL constructor also requires its timestamp.
    @ParameterizedTest(name = "Reject null SQL {0}")
    @MethodSource("sqlNullArguments")
    @DisplayName("Throw NullPointerException for null SQL data")
    void sqlConstructor_nullData(String field, String message, String paymentId,
                                 String userId, PaymentMethodStatus status, Instant createdAt) {
        NullPointerException exception = Assertions.assertThrows(
                NullPointerException.class,
                () -> new CashPaymentMethod(paymentId, userId, status, createdAt)
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> sqlNullArguments() {
        return Stream.of(
                Arguments.of("paymentId", "PaymentMethodID cannot be null", null, USER_ID, STATUS, CREATED_AT),
                Arguments.of("userId", "UserID cannot be null", PAYMENT_ID, null, STATUS, CREATED_AT),
                Arguments.of("status", "PaymentMethodStatus cannot be null", PAYMENT_ID, USER_ID, null, CREATED_AT),
                Arguments.of("createdAt", "Timestamp createdAt cannot be null", PAYMENT_ID, USER_ID, STATUS, null)
        );
    }

    static Stream<Arguments> blankArguments() {
        return Stream.of(
                Arguments.of("paymentId", "PaymentMethodID cannot be blank", "   ", USER_ID),
                Arguments.of("userId", "userId cannot be blank", PAYMENT_ID, "   ")
        );
    }
}
