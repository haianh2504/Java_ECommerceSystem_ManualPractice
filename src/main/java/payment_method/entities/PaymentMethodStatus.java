package payment_method.entities;

public enum PaymentMethodStatus {
    ACTIVE, // CAN USE
    INACTIVE, // TEMPORARILY CAN'T USE
    EXPIRED, // EXPIRED
    BLOCKED // BLOCKED
}
