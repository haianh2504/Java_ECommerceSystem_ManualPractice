package entities;

public class CashPaymentMethod extends PaymentMethod{
    public CashPaymentMethod(String paymentId, String userId, PaymentMethodStatus status){
        super(paymentId,userId,status);
    }
}
