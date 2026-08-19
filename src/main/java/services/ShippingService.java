package services;

import entities.PhysicalProduct;

import java.math.BigDecimal;

public interface ShippingService {
    // calculating
    public BigDecimal calculateShippingFee(PhysicalProduct pp);
}
