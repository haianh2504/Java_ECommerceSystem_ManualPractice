package entities;

import java.time.Instant;

public class CashPaymentMethod extends PaymentMethod{
//    constructor create
    public CashPaymentMethod(String paymentId, String userId, PaymentMethodStatus status){
        super(paymentId,userId,status);
    }
//    constructor SQL return
public CashPaymentMethod(String paymentId, String userId, PaymentMethodStatus status,Instant createdAt){
    super(paymentId,userId,status,createdAt);
}
}
