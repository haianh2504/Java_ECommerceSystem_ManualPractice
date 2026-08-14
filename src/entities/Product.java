package entities;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public abstract class Product {
    private String id;
    private ProductName name;
    private int stockQuantity;
    private BigDecimal basePrice;
    private final ProductType productType;
//    constructor
    @Builder
    public Product(String id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductType productType)
    {
        if(id == null)
        {
            throw new NullPointerException("ID product cannot be null");
        }
        if(name == null)
        {
            throw new NullPointerException("Product Name cannot be null");
        }
        if(basePrice == null)
        {
            throw new NullPointerException("Product base price cannot be null");
        }
        if(productType == null)
        {
            throw new NullPointerException("Product type cannot be null");
        }
        if(stockQuantity < 0){
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        if(basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product base price cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.basePrice = basePrice;
        this.productType = productType;
    }
//    setters
    public Product changeProductName(ProductName name)
    {
        if(name == null)
        {
            throw new NullPointerException("Product Name cannot be null");
        }
        this.name = name;
        return this;
    }
    public Product setStockQuantity(int newQuantity)
    {
        if(newQuantity < 0)
        {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = newQuantity;
        return this;
    }
    public Product setBasePrice(BigDecimal basePrice)
    {
        if(basePrice == null)
        {
            throw new NullPointerException("Product base price cannot be null");
        }
        if(basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product base price cannot be negative");
        }
        this.basePrice = basePrice;
        return this;
    }
//    get total price
    public abstract BigDecimal getFinalPrice();
}
