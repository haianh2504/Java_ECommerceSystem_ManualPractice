package entities;
import java.math.BigDecimal;

public class PhysicalProduct extends Product{
    private  static BigDecimal PRICE_PER_WEIGHT = new BigDecimal("5"); // dollars
    private BigDecimal weight; // kg
//    constructor
    public PhysicalProduct(String id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductType productType, BigDecimal weight)
    {
        super(id,name,stockQuantity,basePrice,productType);
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

}