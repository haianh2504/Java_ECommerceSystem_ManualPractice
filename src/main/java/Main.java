import cart.entities.Cart;
import cart.repository.CartRepository;
import cart.repository.JdbcCartRepository;
import cart.service.CartManagementService;
import cart.service.CartManagementServiceImpl;
import cart_item.entities.CartItem;
import cart_item.repository.CartItemRepository;
import cart_item.repository.JdbcCartItemRepository;
import cart_item.service.CartItemManagementService;
import cart_item.service.CartItemManagementServiceImpl;
import common.DatabaseConnection;
import product.entities.Product;
import product.entities.ProductName;
import product.repository.JdbcProductRepository;
import product.repository.ProductRepository;
import product.service.ProductManagementService;
import product.service.ProductManagementServiceImpl;
import user.entities.*;
import user.repository.JdbcUserRepository;
import user.repository.UserRepository;
import user.service.UserManageServiceImpl;
import user.service.UserManagementService;
import java.math.BigDecimal;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // mô tả: user -> tạo cart -> tạo order
        // tạo database connection trước
        Connection connection = DatabaseConnection.getConnection();
        // Repository setting up
        UserRepository userRepository = new JdbcUserRepository(connection);
        CartRepository cartRepository = new JdbcCartRepository(connection);
        CartItemRepository cartItemRepository = new JdbcCartItemRepository(connection);
        ProductRepository productRepository = new JdbcProductRepository(connection);
        // cart service setting up
        CartManagementService cartManagementService = new CartManagementServiceImpl(cartRepository,cartItemRepository);
        // cart Item service setting up
        CartItemManagementService cartItemManagementService = new CartItemManagementServiceImpl(cartItemRepository, productRepository);
        // user service setting up
        UserManagementService userManageService = new UserManageServiceImpl(userRepository);
        // product service setting up
        ProductManagementService productManagementService = new ProductManagementServiceImpl(productRepository);
        // create product
        Product product = productManagementService.createNewPhysicalProduct(
                new ProductName("Iphone 16 pro max"),
                20,
                new BigDecimal(20_000_000),
                new BigDecimal(0.200)
        );
        // create user
        User user = userManageService.createUser(
                new PasswordHash("abcde123"),
                new PersonName("Phan Hai Anh"),
                new PhoneNumber("0346586211"),
                new Email("haianh2504077@gmail.com"),
                UserRole.ADMIN
        );
        // create cart for the user
        Cart cart = cartManagementService.createCart(user.getId());
        // create cart item
        CartItem cartItem = cartItemManagementService.addNewCartItem(
                cart.getCartId(),
                product.getId(),
                2
        );
        // khi bấm checkout


    }
}