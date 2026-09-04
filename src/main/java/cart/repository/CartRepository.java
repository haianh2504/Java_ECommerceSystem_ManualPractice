package cart.repository;

import cart.entities.Cart;
import cart_item.entities.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
//    Find cart by id
    public Optional<Cart> findById(Long id);
//    Find cart by userId
    public List<Cart> findByUserId(Long userId);
//    save cart
    public Cart save(Cart cart);
//    delete cart
    public void deleteById(Long cartId);
//    update cart
    public void update(Cart cart);
}
