package repository.productRepo;

import entities.Product;
import entities.ProductName;

import java.util.Optional;

public interface ProductRepository {
//    Save Product
    public void save(Product product);
//    find product by Id
    public Optional<Product> findById(Long productId);
//    find product by name
    public Optional<Product> findByName(ProductName name);
//    update product after changes
    public void update(Product product);
}
