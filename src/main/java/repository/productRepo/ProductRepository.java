package repository.productRepo;

import entities.Product;

import java.util.Optional;

public interface ProductRepository {
//    Save Product
    public void save(Product product);

//    find product by Id
    public Optional<Product> findById(Long productId);


}
