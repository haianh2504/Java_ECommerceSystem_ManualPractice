package discount.service;

import discount.entities.Discount;

import java.math.BigDecimal;
import java.util.Objects;

public class DiscountServiceImpl implements DiscountService {
    @Override
    public BigDecimal calculateDiscountAmount(Discount discount) {
        Objects.requireNonNull(discount, "discount must not be null");
        BigDecimal discountAmount = BigDecimal.ZERO;
        if(discount.getSubTotal().compareTo(BigDecimal.valueOf(5_000_000)) >= 0)
        {
            discountAmount = discount.getSubTotal().multiply(BigDecimal.valueOf(15)).divide(BigDecimal.valueOf(100));
        }
        else if(discount.getSubTotal().compareTo(BigDecimal.valueOf(1_000_000)) >= 0)
        {
            discountAmount = discount.getSubTotal().multiply(BigDecimal.valueOf(8)).divide(BigDecimal.valueOf(100));
        }
        return discountAmount;
    }
}
