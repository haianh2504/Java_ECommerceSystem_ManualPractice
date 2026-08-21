package entities;

import java.math.BigDecimal;
import java.time.Instant;

public class DigitalProduct extends Product {
//    constructor to create new one
    public DigitalProduct(Long id, ProductName name, int stockQuantity, BigDecimal basePrice,ProductStatus status, ProductType productType)
    {
        super(id,name,stockQuantity,basePrice,status,productType);
    }
//    constructor to return one from database
public DigitalProduct(Long id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductStatus status, ProductType productType, Instant createdAt)
{
    super(id,name,stockQuantity,basePrice,status,productType,createdAt);
}

//    get final price
    public BigDecimal getFinalPrice()
    {
        return this.getBasePrice();
    }
}
