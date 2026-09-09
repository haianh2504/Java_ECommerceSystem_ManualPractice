package payment_method.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PaymentProviderTest {
    private static final String PROVIDER_ID = "provider-1";
    private static final String PROVIDER_NAME = "Visa";
    private static final PaymentProviderSupportStatus STATUS = PaymentProviderSupportStatus.SUPPORTED;

    // Verify every required provider field rejects null with the exact message.
    @ParameterizedTest(name = "Reject null {0}")
    @MethodSource("nullArguments")
    @DisplayName("Throw NullPointerException for null provider data")
    void constructor_nullData(String field, String message, String id,
                              String name, PaymentProviderSupportStatus status) {
        NullPointerException exception = Assertions.assertThrows(
                NullPointerException.class,
                () -> new PaymentProvider(id, name, status)
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> nullArguments() {
        return Stream.of(
                Arguments.of("id", "PaymentProviderID cannot be null", null, PROVIDER_NAME, STATUS),
                Arguments.of("name", "PaymentProviderName cannot be null", PROVIDER_ID, null, STATUS),
                Arguments.of("status", "PaymentProviderStatus cannot be null", PROVIDER_ID, PROVIDER_NAME, null)
        );
    }

    // Verify blank provider identifiers and names are rejected with the exact message.
    @ParameterizedTest(name = "Reject blank {0}")
    @MethodSource("blankArguments")
    @DisplayName("Throw IllegalArgumentException for blank provider data")
    void constructor_blankData(String field, String message, String id, String name) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new PaymentProvider(id, name, STATUS)
        );
        Assertions.assertEquals(message, exception.getMessage(), field);
    }

    static Stream<Arguments> blankArguments() {
        return Stream.of(
                Arguments.of("id", "PaymentProviderID cannot be blank", "   ", PROVIDER_NAME),
                Arguments.of("name", "PaymentProviderName cannot be blank", PROVIDER_ID, "   ")
        );
    }
}
