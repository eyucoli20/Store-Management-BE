package StoreManagement.inventoryManagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemSaleFromInventoryReq {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Initial must be greater than 0")
    private int quantity;
}
