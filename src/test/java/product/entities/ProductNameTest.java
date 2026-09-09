package product.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ProductNameTest {
    @Nested
    @DisplayName("Create a product name")
    class CreateProductName {
        // Verify that valid product names are trimmed before being stored.
        @Test
        @DisplayName("Create and trim a valid product name")
        void constructProductName_validData() {
            ProductName productName = new ProductName("  Mechanical Keyboard  ");

            Assertions.assertEquals("Mechanical Keyboard", productName.getName());
        }

        // Verify that a null product name is rejected with the entity's exact message.
        @Test
        @DisplayName("Throw NullPointerException when product name is null")
        void constructProductName_nullData() {
            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> new ProductName(null)
            );

            Assertions.assertEquals("Name Product cannot be null", exception.getMessage());
        }

        // Verify that a blank product name is rejected after whitespace is trimmed.
        @Test
        @DisplayName("Throw IllegalArgumentException when product name is blank")
        void constructProductName_blankData() {
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new ProductName("   ")
            );

            Assertions.assertEquals("Name Product cannot be empty", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Change a product name")
    class ChangeProductName {
        // Verify that setName stores valid input and returns the same ProductName instance.
        @Test
        @DisplayName("Change a product name using valid data")
        void setName_validData() {
            ProductName productName = new ProductName("Keyboard");

            ProductName returnedProductName = productName.setName("Gaming Keyboard");

            Assertions.assertAll(
                    () -> Assertions.assertSame(productName, returnedProductName),
                    () -> Assertions.assertEquals("Gaming Keyboard", productName.getName())
            );
        }

        // Verify that setName rejects null without changing the current product name.
        @Test
        @DisplayName("Throw NullPointerException when new product name is null")
        void setName_nullData() {
            ProductName productName = new ProductName("Keyboard");

            NullPointerException exception = Assertions.assertThrows(
                    NullPointerException.class,
                    () -> productName.setName(null)
            );

            Assertions.assertAll(
                    () -> Assertions.assertEquals("Name Product cannot be null", exception.getMessage()),
                    () -> Assertions.assertEquals("Keyboard", productName.getName())
            );
        }

        // Verify that setName rejects blank input without changing the current product name.
        @Test
        @DisplayName("Throw IllegalArgumentException when new product name is blank")
        void setName_blankData() {
            ProductName productName = new ProductName("Keyboard");

            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> productName.setName("   ")
            );

            Assertions.assertAll(
                    () -> Assertions.assertEquals("Name Product cannot be empty", exception.getMessage()),
                    () -> Assertions.assertEquals("Keyboard", productName.getName())
            );
        }
    }
}
