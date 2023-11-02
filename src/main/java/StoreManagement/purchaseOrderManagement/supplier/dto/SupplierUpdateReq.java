package StoreManagement.purchaseOrderManagement.supplier.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierUpdateReq {
    @Size(min = 2, message = "Supplier Name must be at least 2 characters")
    private String supplierName;
    private String supplierAddress;
}
