package product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @PositiveOrZero(message = "Stock quantity must not be negative")
    private int stockQuantity;

    @NotNull(message = "Base price cannot be null")
    @Positive(message = "Base price must be greater than zero")
    private BigDecimal basePrice;
}
