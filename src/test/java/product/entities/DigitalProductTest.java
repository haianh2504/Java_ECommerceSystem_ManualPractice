package product.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Stream;

public class DigitalProductTest {
    private static final Long PRODUCT_ID = 1L;
    private static final ProductName PRODUCT_NAME = new ProductName("Java E-book");
    private static final int STOCK_QUANTITY = 10;
    private static final BigDecimal BASE_PRICE = new BigDecimal("25.00");
    private static final ProductStatus STATUS = ProductStatus.ACTIVE;
    private static final ProductType TYPE = ProductType.DIGITAL;
    private static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");

    @Nested
    @DisplayName("Return a digital product from SQL")
    class ReturnDigitalProductFromSql {
        // Verify that the SQL-return constructor maps all values to a Product instance.
        @Test
        @DisplayName("Construct a digital product from valid SQL data")
        void constructDigitalProduct_validSqlData() {
            Product product = new DigitalProduct(
                    PRODUCT_ID, PRODUCT_NAME, STOCK_QUANTITY, BASE_PRICE,
                    STATUS, TYPE, CREATED_AT
            );

            Assertions.assertAll(
                    () -> Assertions.assertInstanceOf(DigitalProduct.class, product),
                    () -> Assertions.assertEquals(PRODUCT_ID, product.getId()),
                    () -> Assertions.assertSame(PRODUCT_NAME, product.getName()),
                    () -> Assertions.assertEquals(STOCK_QUANTITY, product.getQuantity()),
                    () -> Assertions.assertEquals(BASE_PRICE, product.getBasePrice()),
                    () -> Assertions.assertEquals(STATUS, product.getStatus()),
                    () -> Assertions.assertEquals(TYPE, product.getProductType()),
                    () -> Assertions.assertEquals(CREATED_AT, product.getCreatedAt())
            );
        }

        // Verify that every required SQL field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required SQL data is null")
        void constructDigitalProduct_nullRequiredData(
                String fieldName, String expectedMessage, Long productId,
                ProductName name, BigDecimal basePrice, ProductStatus status,
                ProductType type, Instant createdAt
        ) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new DigitalProduct(
                            productId, name, STOCK_QUANTITY, basePrice,
                            status, type, createdAt
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("productId", "productId cannot be null", null,
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, CREATED_AT),
                    Arguments.of("name", "Product name cannot be null", PRODUCT_ID,
                            null, BASE_PRICE, STATUS, TYPE, CREATED_AT),
                    Arguments.of("basePrice", "Product base price cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, null, STATUS, TYPE, CREATED_AT),
                    Arguments.of("status", "Product status cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, null, TYPE, CREATED_AT),
                    Arguments.of("productType", "Product type cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, STATUS, null, CREATED_AT),
                    Arguments.of("createdAt", "Timestampt createdAt cannot be null", PRODUCT_ID,
                            PRODUCT_NAME, BASE_PRICE, STATUS, TYPE, null)
            );
        }

        // Verify that inherited numeric fields reject illegal values.
        @ParameterizedTest(name = "Reject invalid {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal SQL data")
        void constructDigitalProduct_illegalArguments(
                String fieldName, String expectedMessage,
                int stockQuantity, BigDecimal basePrice
        ) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new DigitalProduct(
                            PRODUCT_ID, PRODUCT_NAME, stockQuantity, basePrice,
                            STATUS, TYPE, CREATED_AT
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("negative stockQuantity", "Stock quantity cannot be negative",
                            -1, BASE_PRICE),
                    Arguments.of("zero basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, BigDecimal.ZERO),
                    Arguments.of("negative basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, new BigDecimal("-0.01"))
            );
        }
    }

    @Nested
    @DisplayName("Create a new digital product")
    class CreateNewDigitalProduct {
        // Verify that valid input creates a DigitalProduct through its Product parent type.
        @Test
        @DisplayName("Construct a new digital product from valid data")
        void constructDigitalProduct_validData() {
            Instant beforeCreation = Instant.now();
            Product product = new DigitalProduct(
                    PRODUCT_NAME, STOCK_QUANTITY, BASE_PRICE, STATUS, TYPE
            );
            Instant afterCreation = Instant.now();

            Assertions.assertAll(
                    () -> Assertions.assertInstanceOf(DigitalProduct.class, product),
                    () -> Assertions.assertNull(product.getId()),
                    () -> Assertions.assertSame(PRODUCT_NAME, product.getName()),
                    () -> Assertions.assertEquals(STOCK_QUANTITY, product.getQuantity()),
                    () -> Assertions.assertEquals(BASE_PRICE, product.getBasePrice()),
                    () -> Assertions.assertEquals(STATUS, product.getStatus()),
                    () -> Assertions.assertEquals(TYPE, product.getProductType()),
                    () -> Assertions.assertFalse(product.getCreatedAt().isBefore(beforeCreation)),
                    () -> Assertions.assertFalse(product.getCreatedAt().isAfter(afterCreation))
            );
        }

        // Verify that every required creation field rejects null with the expected message.
        @ParameterizedTest(name = "Reject null {0}")
        @MethodSource("requiredNullArguments")
        @DisplayName("Throw NullPointerException when required data is null")
        void constructDigitalProduct_nullRequiredData(
                String fieldName, String expectedMessage, ProductName name,
                BigDecimal basePrice, ProductStatus status, ProductType type
        ) {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new DigitalProduct(
                            name, STOCK_QUANTITY, basePrice, status, type
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> requiredNullArguments() {
            return Stream.of(
                    Arguments.of("name", "Product name cannot be null",
                            null, BASE_PRICE, STATUS, TYPE),
                    Arguments.of("basePrice", "Product base price cannot be null",
                            PRODUCT_NAME, null, STATUS, TYPE),
                    Arguments.of("status", "Product status cannot be null",
                            PRODUCT_NAME, BASE_PRICE, null, TYPE),
                    Arguments.of("productType", "Product type cannot be null",
                            PRODUCT_NAME, BASE_PRICE, STATUS, null)
            );
        }

        // Verify that inherited numeric fields reject illegal values.
        @ParameterizedTest(name = "Reject invalid {0}")
        @MethodSource("illegalArguments")
        @DisplayName("Throw IllegalArgumentException for illegal data")
        void constructDigitalProduct_illegalArguments(
                String fieldName, String expectedMessage,
                int stockQuantity, BigDecimal basePrice
        ) {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new DigitalProduct(
                            PRODUCT_NAME, stockQuantity, basePrice, STATUS, TYPE
                    )
            );

            Assertions.assertEquals(expectedMessage, exception.getMessage(), fieldName);
        }

        static Stream<Arguments> illegalArguments() {
            return Stream.of(
                    Arguments.of("negative stockQuantity", "Stock quantity cannot be negative",
                            -1, BASE_PRICE),
                    Arguments.of("zero basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, BigDecimal.ZERO),
                    Arguments.of("negative basePrice", "Product base price cannot be negative",
                            STOCK_QUANTITY, new BigDecimal("-0.01"))
            );
        }
    }

}
