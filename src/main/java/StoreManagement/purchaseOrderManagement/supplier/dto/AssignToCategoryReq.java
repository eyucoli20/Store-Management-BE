package StoreManagement.purchaseOrderManagement.supplier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignToCategoryReq {
    @NotNull(message = "Supplier Id is required")
    private Long supplierId;

    @NotNull(message = "Category Id is required")
    private Integer categoryId;
}
