package cart;

import cart.entities.Cart;
import cart.repository.CartRepository;
import cart_item.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

public class CartManagementServiceImplTest {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    @BeforeEach
    public void setup()
    {
        cartRepository = new CartRepository() {
            @Override
            public Optional<Cart> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public List<Cart> findByUserId(Long userId) {
                return List.of();
            }

            @Override
            public Cart save(Cart cart) {
                return null;
            }

            @Override
            public void deleteById(Long cartId) {

            }

            @Override
            public void update(Cart cart) {

            }
        };
    }
}
