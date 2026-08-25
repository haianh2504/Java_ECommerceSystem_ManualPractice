package cart_item.repository;

import cart_item.entities.CartItem;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
//    get all cart items by cart id
    public List<CartItem> findByCartId(Long cartId);

//    get cart item by cart id and product id
    public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

//    find one cart item by id
    public Optional<CartItem> findByCartItemId(Long cartItemId);

//    save cart item
    public void save(CartItem cartItem);

//    delete cart item by cart and product id - need auth
    public void deleteByCartIdAndProductId(Long cartId, Long productId);

//    delete cart item by cartItem id
    public void deleteByCartItemId(Long cartItemId);

//    delete all cart items by cartId
    public void deleteAllByCartId(Long cartId);

//    update cart item - usually for changing quantity
    public void update(CartItem cartItem);
}
