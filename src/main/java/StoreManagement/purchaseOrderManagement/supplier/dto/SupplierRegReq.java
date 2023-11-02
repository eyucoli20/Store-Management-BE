package StoreManagement.purchaseOrderManagement.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRegReq {
    @NotBlank(message = "Supplier Name is required")
    @Size(min = 2, message = "Supplier Name must be at least 2 characters")
    private String supplierName;

    @NotBlank(message = "Supplier Address is required")
    private String supplierAddress;
}
