package entities;
import services.ShippingService;
import java.math.BigDecimal;

public class PhysicalProduct extends Product implements ShippingService {
    private  static BigDecimal PRICE_PER_WEIGHT = new BigDecimal("5"); // dollars
    private BigDecimal shippingFee;
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
//    calculate shipping fee
    @Override
    public void calculateShippingFee()
    {
        // fee = weight x PRICE_PER_WEIGHT
        this.shippingFee = this.weight.multiply(this.PRICE_PER_WEIGHT);
    }
//    calculate total price
    @Override
    public BigDecimal getFinalPrice()
    {
        return this.getBasePrice().add(this.shippingFee);
    }

}