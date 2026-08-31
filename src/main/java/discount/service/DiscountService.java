package discount.service;

import discount.entities.Discount;

import java.math.BigDecimal;

public interface DiscountService {
    public BigDecimal calculateDiscountAmount(Discount discount);
}
