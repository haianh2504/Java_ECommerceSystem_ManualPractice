package entities;

import java.time.Instant;
import java.util.Objects;

public abstract class PaymentMethod {
    private final String paymentId;
    private final String userId;
    private PaymentMethodStatus status;
    private final Instant createdAt;
    private Instant updatedAt;
//    constructor
    protected PaymentMethod(String paymentId, String userId, PaymentMethodStatus status, Instant createdAt)
    {
        this.paymentId = Objects.requireNonNull(paymentId, "PaymentMethodID cannot be null");
        if(paymentId.isBlank()) throw new IllegalArgumentException("PaymentMethodID cannot be blank");
        this.userId = Objects.requireNonNull(userId, "UserID cannot be null");
        if(userId.isBlank()) throw new IllegalArgumentException("userId cannot be blank");
        this.status = Objects.requireNonNull(status, "PaymentMethodStatus cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Timestamp createdAt cannot be null");
    }
//    getters
    public final String getPaymentId(){return this.paymentId;}
    public final String getUserId(){return this.userId;}
    public final  PaymentMethodStatus getStatus(){return status;}
    public final Instant getTimeStampCreated(){return createdAt;}
    public final Instant getTimeStampUpdated(){return updatedAt;}
}