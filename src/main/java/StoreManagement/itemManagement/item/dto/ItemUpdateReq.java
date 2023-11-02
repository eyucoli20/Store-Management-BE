package StoreManagement.itemManagement.item.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemUpdateReq {
    @Size(min = 2, message = "Item name must be at least 2 characters")
    private String itemName;

    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @Positive(message = "Initial Quantity must be greater than 0")
    private Integer initialQuantity;

    private Integer categoryId;
}
