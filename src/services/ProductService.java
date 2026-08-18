package services;

import entities.Product;
import entities.ProductName;
import entities.ProductType;

import java.math.BigDecimal;

public interface ProductService {
//    create new physical product
    public Product createNewPhysicalProduct(String productId, ProductName name, int stockQuantity, BigDecimal basePrice, ProductType productType, BigDecimal weight);
//    create new digital product
    public Product createNewDigitalProduct(String productId, ProductName name, int stockQuantity, BigDecimal basePrice, ProductType productType);
//    delete a product
    public boolean deleteProductByID(String productId);
//    find Product by id
    public Product findProductByID(String productId);
}
