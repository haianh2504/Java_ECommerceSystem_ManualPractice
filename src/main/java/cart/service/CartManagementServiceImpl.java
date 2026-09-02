package cart.service;

import cart.entities.Cart;
import cart.repository.CartRepository;
import cart_item.repository.CartItemRepository;

import java.util.Objects;

public class CartManagementServiceImpl implements CartManagementService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
//    constructor
    public CartManagementServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = Objects.requireNonNull(cartRepository, "CartRepository cannot be null");
        this.cartItemRepository = Objects.requireNonNull(cartItemRepository, "CartItemRepository cannot be null");
    }
//    create new cart
    @Override
    public Cart createCart(Long userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        Cart cart = new Cart(userId);
        cartRepository.save(cart);
        return cart;
    }
//    get cart by userID
    @Override
    public Cart getCartByUserId(Long userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("Cart for userId does not exists")
        );
    }
//    clear cart
    @Override
    public void clearCart(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }
//    check out cart - change cart status
    @Override
    public void checkoutCart(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("Cart does not exists")
        );
        cart.setCheckedOutStatus();
        cartRepository.update(cart);
    }
}
