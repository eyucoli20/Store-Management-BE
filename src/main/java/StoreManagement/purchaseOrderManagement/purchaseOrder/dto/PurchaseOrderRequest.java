package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseOrderRequest {

    @NotNull(message = "Store is required")
    private Long storeId;

    @NotNull(message = "Item is required")
    private Long itemId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
}
