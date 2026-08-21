package entities;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Product {
    private Long id;
    private ProductName name;
    private int stockQuantity;
    private BigDecimal basePrice;
    private ProductStatus status;
    private final ProductType productType;
    private Instant createdAt;
    protected Product(Long id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductStatus status, ProductType productType)
    {
        this.id = Objects.requireNonNull(id,"productId cannot be null");
        this.name = name;
        if(stockQuantity < 0){
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
        this.basePrice = Objects.requireNonNull(basePrice,"Product base price cannot be null");
        if(basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product base price cannot be negative");
        }
        this.status = Objects.requireNonNull(status,"Product status cannot be null");
        this.productType = Objects.requireNonNull(productType,"Product type cannot be null");
        this.createdAt = Instant.now();
    }
//    constructor to return product from database
    protected Product(Long id, ProductName name, int stockQuantity, BigDecimal basePrice, ProductStatus status, ProductType productType, Instant createdAt)
    {
        this.id = Objects.requireNonNull(id,"productId cannot be null");
        this.name = name;
        if(stockQuantity < 0){
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
        this.basePrice = Objects.requireNonNull(basePrice,"Product base price cannot be null");
        if(basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product base price cannot be negative");
        }
        this.status = Objects.requireNonNull(status,"Product status cannot be null");
        this.productType = Objects.requireNonNull(productType,"Product type cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt,"Timestampt createdAt cannot be null");
    }
//    getters
    public final Long getId(){return this.id;}
    public final ProductName getName(){return this.name;}
    public final int getQuantity(){return this.stockQuantity;}
    public final BigDecimal getBasePrice() {
        return basePrice;
    }
    public final ProductStatus getStatus(){return this.status;}
    public final ProductType getProductType(){return this.productType;}
    public final Instant getCreatedAt(){return this.createdAt;}

    //    setters
    public void changeProductName(ProductName name)
    {
        if(name == null)
        {
            throw new NullPointerException("Product Name cannot be null");
        }
        this.name = name;
    }
    public void setStockQuantity(int newQuantity)
    {
        if(newQuantity < 0)
        {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = newQuantity;
    }
    public void setBasePrice(BigDecimal basePrice)
    {
        if(basePrice == null)
        {
            throw new NullPointerException("Product base price cannot be null");
        }
        if(basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product base price cannot be negative");
        }
        this.basePrice = basePrice;
    }
}
