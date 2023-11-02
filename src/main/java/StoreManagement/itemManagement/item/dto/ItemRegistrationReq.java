package StoreManagement.itemManagement.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRegistrationReq {
    @NotBlank(message = "Item Name is required")
    @Size(min = 2, message = "Item Name must be at least 2 characters")
    private String itemName;

    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    @NotNull(message = "price is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Initial Quantity is required")
    @Positive(message = "Initial Quantity must be greater than 0")
    private Integer initialQuantity;

    @NotNull(message = "Category is required")
    private Integer categoryId;

}