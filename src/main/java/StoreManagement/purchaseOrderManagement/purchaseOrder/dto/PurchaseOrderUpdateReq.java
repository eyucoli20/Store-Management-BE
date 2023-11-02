package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseOrderUpdateReq {

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;
}
