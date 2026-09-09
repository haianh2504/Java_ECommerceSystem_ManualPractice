package payment_method.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

public class BankPaymentMethodTest {
    private static final String PAYMENT_ID = "payment-1";
    private static final String USER_ID = "user-1";
    private static final PaymentMethodStatus STATUS = PaymentMethodStatus.ACTIVE;
    private static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");
    private static final PaymentProvider PROVIDER = new PaymentProvider(
            "provider-1", "Bank", PaymentProviderSupportStatus.SUPPORTED
    );
    private static final String MASKED_IDENTIFIER = "****1234";

    // Verify every required bank-payment field rejects null with the exact message.
    @ParameterizedTest(name = "Reject null {0}")
    @MethodSource("nullArguments")
    @DisplayName("Throw NullPointerException for null bank-payment data")
    void constructor_nullData(String field, String message, String paymentId,
                              String userId, PaymentMethodStatus status, Instant createdAt,
                              PaymentProvider provider, String maskedIdentifier) {
        NullPointerException exception = Assertions.assertThrows(
                NullPointerException.class,
                () -> new BankPaymentMethod(
                        paymentId, userId, status, createdAt, provider, maskedIdentifier
                )
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> nullArguments() {
        return Stream.of(
                Arguments.of("paymentId", "PaymentMethodID cannot be null", null,
                        USER_ID, STATUS, CREATED_AT, PROVIDER, MASKED_IDENTIFIER),
                Arguments.of("userId", "UserID cannot be null", PAYMENT_ID,
                        null, STATUS, CREATED_AT, PROVIDER, MASKED_IDENTIFIER),
                Arguments.of("status", "PaymentMethodStatus cannot be null", PAYMENT_ID,
                        USER_ID, null, CREATED_AT, PROVIDER, MASKED_IDENTIFIER),
                Arguments.of("createdAt", "Timestamp createdAt cannot be null", PAYMENT_ID,
                        USER_ID, STATUS, null, PROVIDER, MASKED_IDENTIFIER),
                Arguments.of("provider", "PaymentProvider cannot be null", PAYMENT_ID,
                        USER_ID, STATUS, CREATED_AT, null, MASKED_IDENTIFIER),
                Arguments.of("maskedIdentifier", "MaskedIdentifier cannot be null", PAYMENT_ID,
                        USER_ID, STATUS, CREATED_AT, PROVIDER, null)
        );
    }

    // Verify blank inherited identifiers and masked identifier are rejected.
    @ParameterizedTest(name = "Reject blank {0}")
    @MethodSource("blankArguments")
    @DisplayName("Throw IllegalArgumentException for blank bank-payment data")
    void constructor_blankData(String field, String message, String paymentId,
                               String userId, String maskedIdentifier) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new BankPaymentMethod(
                        paymentId, userId, STATUS, CREATED_AT, PROVIDER, maskedIdentifier
                )
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> blankArguments() {
        return Stream.of(
                Arguments.of("paymentId", "PaymentMethodID cannot be blank", "   ", USER_ID, MASKED_IDENTIFIER),
                Arguments.of("userId", "userId cannot be blank", PAYMENT_ID, "   ", MASKED_IDENTIFIER),
                Arguments.of("maskedIdentifier", "MaskedIdentifier cannot be blank", PAYMENT_ID, USER_ID, "   ")
        );
    }
}
