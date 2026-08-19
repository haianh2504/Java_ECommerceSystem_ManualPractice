package repository.productRepo;

import entities.Product;

public interface ProductRepository {
//    Save Product
    public void save(Product product);

//    find product by Id
    public Product findById(String productId);


}
