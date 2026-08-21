package entities;
import java.math.BigDecimal;
import java.time.Instant;

public class PhysicalProduct extends Product{
    private static BigDecimal PRICE_PER_WEIGHT = new BigDecimal("5"); // dollars
    private BigDecimal weight; // kg
//    constructor to create new one
    public PhysicalProduct(Long id, ProductName name, int stockQuantity, BigDecimal basePrice,ProductStatus status, ProductType productType, BigDecimal weight)
    {
        super(id,name,stockQuantity, basePrice, status,productType);
        if(weight == null)
        {
            throw new NullPointerException("Weight product cannot be null");
        }
        else if(weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid Weight product");
        }
        this.weight = weight;
    }

//    constructor to return one from database
public PhysicalProduct(Long id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductStatus status, ProductType productType, Instant createdAt, BigDecimal weight)
{
    super(id,name,stockQuantity, basePrice, status,productType, createdAt);
    if(weight == null)
    {
        throw new NullPointerException("Weight product cannot be null");
    }
    else if(weight.compareTo(BigDecimal.ZERO) <= 0){
        throw new IllegalArgumentException("Invalid Weight product");
    }
    this.weight = weight;
}
//    getters
    public final BigDecimal getWeight()
    {
        return this.weight;
    }
    public static final BigDecimal getPricePerWeight()
    {
        return PRICE_PER_WEIGHT;
    }
//    setter
    public void setWeight(BigDecimal weight)
    {
        if(weight == null)
        {
            throw new NullPointerException("Weight product cannot be null");
        }
        else if(weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Invalid Weight product");
        }
        this.weight = weight;
    }
    public static void setPricePerWeight(BigDecimal newPrice)
    {
        if(newPrice == null) throw new NullPointerException("Price per weight cannot be null");
        else if(newPrice.compareTo(PhysicalProduct.getPricePerWeight()) == 0) return;
        else if(newPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price per weight cannot smaller than ZERO");
        else{
            PhysicalProduct.PRICE_PER_WEIGHT = newPrice;
        }
    }
}