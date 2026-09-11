package cart.service;

import cart.entities.Cart;
import cart.repository.CartRepository;
import cart_item.repository.CartItemRepository;
import exception.resource.detailed_exceptions.CartNotFoundException;

import java.util.List;
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
        Cart cart = cartRepository.save(new Cart(userId));
        return cart;
    }
//    get carts by userID
    @Override
    public List<Cart> getCartByUserId(Long userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        return List.copyOf(cartRepository.findByUserId(userId));
    }
//    get cart by cartId
    @Override
    public Cart getCartById(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId)
                );
    }

    //    clear cart
    @Override
    public void clearCart(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        cartItemRepository.deleteAllByCartId(cartId);
    }
//    check out cart - change cart status
    @Override
    public void checkoutCart(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartNotFoundException(cartId)
        );
        cart.setCheckedOutStatus();
        cartRepository.update(cart);
    }
}
