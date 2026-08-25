package cart.service;

import cart.entities.Cart;
import cart.repository.CartRepository;

import java.util.Objects;

public class CartManagementServiceImpl implements CartManagementService {
    private final CartRepository cartRepository;
//    constructor
    public CartManagementServiceImpl(CartRepository cartRepository) {
        this.cartRepository = Objects.requireNonNull(cartRepository, "CartRepository cannot be null");
    }
//    create new cart
    @Override
    public void createCart(Long userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        if(cartRepository.findByUserId(userId).isPresent()){
            throw new IllegalArgumentException("Cart for userId already exists");
        }
        cartRepository.save(new Cart(userId));
    }
//    get cart by userID
    @Override
    public Cart getCartByUserId(Long userId) {
        Objects.requireNonNull(userId, "userId cannot be null");
        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("Cart for userId does not exists")
        );
    }


}
