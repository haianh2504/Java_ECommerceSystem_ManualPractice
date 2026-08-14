package entities;

import java.math.BigDecimal;

public class DigitalProduct extends Product {
//    constructor
    public DigitalProduct(String id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductType productType)
    {
        super(id,name,stockQuantity,basePrice,productType);
    }
//    get final price
    public BigDecimal getFinalPrice()
    {
        return this.getBasePrice();
    }
}
