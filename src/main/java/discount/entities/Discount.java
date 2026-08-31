package discount.entities;

import java.math.BigDecimal;
import java.util.Objects;

// temporarily not using discountCode
public class Discount {
    private BigDecimal subTotal;
    private String discountCode;

    public Discount (BigDecimal subTotal,String discountCode) {
        this.subTotal = Objects.requireNonNull(subTotal, "subTotal must not be null");
        if (subTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("subTotal must not be negative");
        }
    }
    public BigDecimal getSubTotal() {
        return subTotal;
    }
    public String getDiscountCode() {
        return discountCode;
    }
}
