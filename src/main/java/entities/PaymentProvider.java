package entities;

import java.util.Objects;

public class PaymentProvider {
    private final String id;
    private final String name;
    private final PaymentProviderSupportStatus status;
//    constructor
    public PaymentProvider(String id, String name,PaymentProviderSupportStatus status)
    {
        // kiem tra NAME hay ID co trong database không
        // kiem tra null
        this.id = Objects.requireNonNull(id, "PaymentProviderID cannot be null");
        if(id.isBlank()) throw new IllegalArgumentException("PaymentProviderID cannot be blank");
        this.name = Objects.requireNonNull(name, "PaymentProviderName cannot be null");
        if(name.isBlank()) throw new IllegalArgumentException("PaymentProviderName cannot be blank");
        this.status = Objects.requireNonNull(status, "PaymentProviderStatus cannot be null");
    }
//    getters
    public final String getId(){return this.name;}
    public final String getName(){return this.name;}
    public final String getPaymentProviderSupportStatus(){return this.status.getDescription();}
}
