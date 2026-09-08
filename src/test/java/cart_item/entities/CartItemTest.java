package cart_item.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CartItemTest {
    // set up
    private static final Long CART_ITEM_ID = 15L;
    private static final Long CART_ID = 2L;
    private static final Long PRODUCT_ID = 3L;
    private static final int VALID_NUMBER = 10;
    private static final int NEGATIVE_NUMBER = -5;
    private static final int ZERO_NUMBER = 0;
    private static final String INVALID_NUMBER_MESSAGE = "Cart item number must be greater than 0";

    // GENERATE A NEW CART
    @Nested
    @DisplayName("Generate a new cartItem")
    class GenerateNewCartItem{
        // a new cartItem with fully information
        @Test
        @DisplayName("Generate a new cartItem with full valid information provided")
        public void generateNewCartItem_fullValidInformationProvided(){
            // initialize
            CartItem cartItem = new CartItem(
                    CART_ID,
                    PRODUCT_ID,
                    VALID_NUMBER
            );
            Assertions.assertAll(
                    () -> Assertions.assertNull(cartItem.getCartItemId()),
                    () -> Assertions.assertEquals(CART_ID, cartItem.getCartId()),
                    () -> Assertions.assertEquals(PRODUCT_ID, cartItem.getProductId()),
                    () -> Assertions.assertEquals(VALID_NUMBER, cartItem.getNumber())
            );
        }

        // a new cartItem with cartId null
        @Test
        @DisplayName("Generate a new cartItem with null cartId provided")
        public void generateNewCartItem_nullCartIdProvided(){
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new CartItem(
                            null,
                            PRODUCT_ID,
                            VALID_NUMBER
                    )
            );
            Assertions.assertEquals("Cart ID cannot be null", exception.getMessage());
        }

        // a new cartItem with productId null
        @Test
        @DisplayName("Generate a new cartItem with null productId provided")
        public void generateNewCartItem_nullProductIdProvided(){
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new CartItem(
                            CART_ID,
                            null,
                            VALID_NUMBER
                    )
            );
            Assertions.assertEquals("Product ID cannot be null", exception.getMessage());
        }

        // a new cartItem with negative number
        @Test
        @DisplayName("Generate a new cartItem with negative number provided")
        public void generateNewCartItem_negativeNumberProvided(){
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new CartItem(
                            CART_ID,
                            PRODUCT_ID,
                            NEGATIVE_NUMBER
                    )
            );
            Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage());
        }

        @Test
        @DisplayName("Reject a new cartItem with number equal to zero")
        void generateNewCartItem_zeroNumberProvided(){
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new CartItem(CART_ID, PRODUCT_ID, ZERO_NUMBER)
            );

            Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage());
        }
    }


    // RETURN PERSISTED CART FROM DATABASE
    @Nested
    @DisplayName("Returning persisted cart from database")
    class ReturningPersistedCartFromDatabase{

        // Null data
        @ParameterizedTest(name = "Returning Failed Due To: null {0}")
        @MethodSource("invalidPersistedCartItemDatabaseArguments")
        @DisplayName("Throw Null pointer exception when required data is missing")
        void returningPersistedCartFromDatabase_nullPointerException_whenRequiredDataIsMissing(
                String fieldName,
                String expectedMessage,
                Long cartItemId,
                Long cartId,
                Long productId,
                int number
        )
        {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new CartItem(cartItemId, cartId, productId, number)
            );
            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }
        // BĂNG CHUYỀN GIÁ TRỊ
        static Stream<Arguments> invalidPersistedCartItemDatabaseArguments(){
            return Stream.of(
                    Arguments.of("cartItemId","CartItem ID cannot be null", null, CART_ID, PRODUCT_ID, VALID_NUMBER),
                    Arguments.of("cartId", "Cart ID cannot be null", CART_ITEM_ID, null, PRODUCT_ID, VALID_NUMBER),
                    Arguments.of("productId", "Product ID cannot be null", CART_ITEM_ID, CART_ID, null, VALID_NUMBER)
            );
        }

        // Invalid Name
        @Test
        @DisplayName("Throw IllegalArgumentException when negative number provided")
        void  returningPersistedCartFromDatabase_illegalArgumentException_negativeNumberProvided(){
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new CartItem(
                            CART_ITEM_ID,
                            CART_ID,
                            PRODUCT_ID,
                            NEGATIVE_NUMBER
                    )
            );
            Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage());
        }

        @Test
        @DisplayName("Reject a persisted cartItem with number equal to zero")
        void returningPersistedCartFromDatabase_zeroNumberProvided(){
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new CartItem(CART_ITEM_ID, CART_ID, PRODUCT_ID, ZERO_NUMBER)
            );

            Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage());
        }
    }

    // CHANGE CART ITEM'S NUMBER WITH NEGATIVE NUM
    @Test
    @DisplayName("Change cart item's number with negative number")
    void changeCartItemNumber_illegalArgumentException_negativeNumberProvided(){
        // set up
        CartItem cartItem = new CartItem(
                CART_ITEM_ID,
                CART_ID,
                PRODUCT_ID,
                VALID_NUMBER
        );
        // assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cartItem.changeNumber(NEGATIVE_NUMBER)
        );
        Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Change cart item's number to zero")
    void changeCartItemNumber_illegalArgumentException_zeroNumberProvided(){
        CartItem cartItem = new CartItem(
                CART_ITEM_ID,
                CART_ID,
                PRODUCT_ID,
                VALID_NUMBER
        );

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cartItem.changeNumber(ZERO_NUMBER)
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(INVALID_NUMBER_MESSAGE, exception.getMessage()),
                () -> Assertions.assertEquals(VALID_NUMBER, cartItem.getNumber())
        );
    }
}
