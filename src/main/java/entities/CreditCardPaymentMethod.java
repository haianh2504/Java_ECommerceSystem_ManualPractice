package entities;

import java.time.Instant;
import java.util.Objects;

public class CreditCardPaymentMethod extends PaymentMethod{
    private PaymentProvider provider;
    private String maskedIdentifier;
    //    constructor
    public CreditCardPaymentMethod(String paymentId, String userId, PaymentMethodStatus status, Instant createdAt, PaymentProvider provider, String maskedIdentifier)
    {
        super(paymentId,userId, status,createdAt);
        this.provider = Objects.requireNonNull(provider, "PaymentProvider cannot be null");
        this.maskedIdentifier = Objects.requireNonNull(maskedIdentifier, "MaskedIdentifier cannot be null");
        if(maskedIdentifier.isBlank()) throw new IllegalArgumentException("MaskedIdentifier cannot be blank");
    }
}

