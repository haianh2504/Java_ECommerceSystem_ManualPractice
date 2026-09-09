package order_item.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class OrderItemTest {
    private static final Long ORDER_ITEM_ID = 1L;
    private static final Long ORDER_ID = 2L;
    private static final Long PRODUCT_ID = 3L;
    private static final int QUANTITY = 4;
    private static final BigDecimal UNIT_PRICE = new BigDecimal("25.00");

    @Nested
    @DisplayName("Return an order item from SQL")
    class ReturnOrderItemFromSql {
        // Verify that the SQL-return constructor maps every persisted value correctly.
        @Test
        @DisplayName("Construct an order item from valid SQL data")
        void constructOrderItem_validSqlData() {
            OrderItem orderItem = new OrderItem(
                    ORDER_ITEM_ID, ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE
            );

            Assertions.assertAll(
                    () -> Assertions.assertEquals(ORDER_ITEM_ID, orderItem.getOrderItemId()),
                    () -> Assertions.assertEquals(ORDER_ID, orderItem.getOrderId()),
                    () -> Assertions.assertEquals(PRODUCT_ID, orderItem.getProductId()),
                    () -> Assertions.assertEquals(QUANTITY, orderItem.getQuantity()),
                    () -> Assertions.assertEquals(UNIT_PRICE, orderItem.getUnitPrice())
            );
        }

        // Verify that every required SQL field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required SQL data is null")
        void constructOrderItem_nullRequiredData(String fieldName, String expectedMessage,
                                                 Long orderItemId, Long orderId,
                                                 Long productId, BigDecimal unitPrice) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new OrderItem(orderItemId, orderId, productId, QUANTITY, unitPrice)
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("orderItemId", "orderItemId cannot be null",
                            null, ORDER_ID, PRODUCT_ID, UNIT_PRICE),
                    Arguments.of("orderId", "orderId cannot be null",
                            ORDER_ITEM_ID, null, PRODUCT_ID, UNIT_PRICE),
                    Arguments.of("productId", "ProductID cannot be null",
                            ORDER_ITEM_ID, ORDER_ID, null, UNIT_PRICE),
                    Arguments.of("unitPrice", "UnitPrice cannot be null",
                            ORDER_ITEM_ID, ORDER_ID, PRODUCT_ID, null)
            );
        }

        // Verify that zero and negative quantities or unit prices are rejected.
        @ParameterizedTest(name = "Reject {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal SQL data")
        void constructOrderItem_illegalArguments(String fieldName, String expectedMessage,
                                                 int quantity, BigDecimal unitPrice) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new OrderItem(
                            ORDER_ITEM_ID, ORDER_ID, PRODUCT_ID, quantity, unitPrice
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("zero quantity", "Invalid quantity", 0, UNIT_PRICE),
                    Arguments.of("negative quantity", "Invalid quantity", -1, UNIT_PRICE),
                    Arguments.of("zero unitPrice", "Invalid UnitPrice", QUANTITY, BigDecimal.ZERO),
                    Arguments.of("negative unitPrice", "Invalid UnitPrice", QUANTITY,
                            new BigDecimal("-0.01"))
            );
        }
    }

    @Nested
    @DisplayName("Create a new order item")
    class CreateNewOrderItem {
        // Verify that valid input creates an order item without a generated ID.
        @Test
        @DisplayName("Construct a new order item from valid data")
        void constructOrderItem_validData() {
            OrderItem orderItem = new OrderItem(ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE);

            Assertions.assertAll(
                    () -> Assertions.assertNull(orderItem.getOrderItemId()),
                    () -> Assertions.assertEquals(ORDER_ID, orderItem.getOrderId()),
                    () -> Assertions.assertEquals(PRODUCT_ID, orderItem.getProductId()),
                    () -> Assertions.assertEquals(QUANTITY, orderItem.getQuantity()),
                    () -> Assertions.assertEquals(UNIT_PRICE, orderItem.getUnitPrice())
            );
        }

        // Verify that every required new-order-item field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required data is null")
        void constructOrderItem_nullRequiredData(String fieldName, String expectedMessage,
                                                 Long orderId, Long productId,
                                                 BigDecimal unitPrice) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new OrderItem(orderId, productId, QUANTITY, unitPrice)
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("orderId", "orderId cannot be null", null, PRODUCT_ID, UNIT_PRICE),
                    Arguments.of("productId", "ProductID cannot be null", ORDER_ID, null, UNIT_PRICE),
                    Arguments.of("unitPrice", "UnitPrice cannot be null", ORDER_ID, PRODUCT_ID, null)
            );
        }

        // Verify that zero and negative quantities or unit prices are rejected.
        @ParameterizedTest(name = "Reject invalid {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal data")
        void constructOrderItem_illegalArguments(String fieldName, String expectedMessage,
                                                 int quantity, BigDecimal unitPrice) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new OrderItem(ORDER_ID, PRODUCT_ID, quantity, unitPrice)
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("zero quantity", "Invalid quantity", 0, UNIT_PRICE),
                    Arguments.of("negative quantity", "Invalid quantity", -1, UNIT_PRICE),
                    Arguments.of("zero unitPrice", "Invalid UnitPrice", QUANTITY, BigDecimal.ZERO),
                    Arguments.of("negative unitPrice", "Invalid UnitPrice", QUANTITY,
                            new BigDecimal("-0.01"))
            );
        }
    }
}
