package cart_item.service;

import cart_item.entities.CartItem;
import cart_item.repository.CartItemRepository;

import java.util.List;
import java.util.Objects;

public class CartItemManagementServiceImpl implements  CartItemManagementService {
    private final CartItemRepository cartItemRepository;
//    constructor
    public CartItemManagementServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = Objects.requireNonNull(cartItemRepository, "cartItemRepository cannot be null");
    }
//    get cartItems by cartId
    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return List.copyOf(cartItems);
    }
    //    add new cartItem in cart
    @Override
    public void addNewCartItem(Long cartId, Long productId, int number) {
        // validation
        Objects.requireNonNull(cartId, "cartId cannot be null");
        Objects.requireNonNull(productId, "productId cannot be null");
        if(number<=0){
            throw new IllegalArgumentException("number must be greater than 0");
        }
        // check for existence
        if(cartItemRepository.findByCartIdAndProductId(cartId, productId).isPresent()){
            throw new RuntimeException("CartItem already exists");
        }
        // add
        cartItemRepository.save(
                new CartItem(cartId, productId, number)
        );
    }
//    delete cartItem in cart
    @Override
    public void deleteCartItem(Long cartItemId) {
        Objects.requireNonNull( cartItemId, "cartItemId cannot be null");
        if(cartItemRepository.findByCartItemId(cartItemId).isEmpty()){
            throw new  RuntimeException("CartItem not found");
        }
        cartItemRepository.findByCartItemId(cartItemId);
    }
//    delete all cartItems in cart
    @Override
    public void clearCart(Long cartId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        cartItemRepository.deleteAllByCartId(cartId);
    }
//    find CartItem by cartId & productId in cart
    @Override
    public CartItem getCartItemByCartIdAndProductId(Long cartId,Long productId) {
        Objects.requireNonNull(cartId, "cartId cannot be null");
        Objects.requireNonNull(productId, "productId cannot be null");
        return cartItemRepository.findByCartIdAndProductId(cartId,productId).orElseThrow(
                () -> new RuntimeException("CartItem not found")
        );
    }
//    update Item quantity
    @Override
    public void updateItemQuantity(Long cartItemId, int newQuantity) {
        Objects.requireNonNull(cartItemId, "cartItemId cannot be null");
        if(newQuantity<=0){
            throw new IllegalArgumentException("newQuantity must be greater than 0");
        }
        CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId).orElseThrow(
                () -> new RuntimeException("CartItem not found")
        );
        if(newQuantity == cartItem.getNumber()) return;
        cartItem.changeNumber(newQuantity);
        cartItemRepository.update(cartItem);
    }

}
