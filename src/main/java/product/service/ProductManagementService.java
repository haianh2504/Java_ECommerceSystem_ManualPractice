package product.service;

import product.entities.Product;
import product.entities.ProductName;

import java.math.BigDecimal;

public interface ProductManagementService {
//    create new physical product
    public Product createNewPhysicalProduct(ProductName name, int stockQuantity, BigDecimal basePrice, BigDecimal weight);
//    create new digital product
    public Product createNewDigitalProduct(ProductName name, int stockQuantity, BigDecimal basePrice);
//    delete a product
//    find Product by id
    public Product findProductById(Long productId);
//    update product name - need authorize
    public void updateProductName(Long productId, ProductName newName);
//    update product weight - only for physical - need autho
    public void updateProductWeight(Long productId, BigDecimal newWeight);
//    update base price - need autho
    public void updateBasePrice(Long productId, BigDecimal newBasePrice);
//    activate product - need autho
    public void activateProduct(Long productId);
//    deactivate product - need autho
    public void deactivateProduct(Long productId);
}
