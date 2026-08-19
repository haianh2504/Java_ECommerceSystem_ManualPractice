package entities;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(
        String transactionId,
        String orderId,
        BigDecimal amount,
        TransactionType transactionType,
        PaymentMethod paymentMethod,
        TransactionState transactionState,
        Instant createdAt
        )
{
    public Transaction{
        if(transactionId == null) throw new NullPointerException("TransactionId cannot be null");
        else if(transactionId.isBlank()) throw new IllegalArgumentException("TransactionID undefined (blank)");
        if(orderId == null) throw new NullPointerException("OrderId cannot be null");
        else if(orderId.isBlank()) throw new IllegalArgumentException("OrderId undefined (blank)");
        if(amount == null) throw new NullPointerException("Amount cannot be null");
        else if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid amount");
        if(transactionType == null) throw new NullPointerException("Transaction type cannot be null");
        if(transactionState == null) throw new NullPointerException("TransactionState cannot be null");
        if(paymentMethod == null) throw new NullPointerException("Payment Method cannot be null");
        if(createdAt == null) throw new NullPointerException("Timestamp createAt cannot be null");
    }
}
