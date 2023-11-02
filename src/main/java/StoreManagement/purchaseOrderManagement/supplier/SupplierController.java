package StoreManagement.purchaseOrderManagement.supplier;

import StoreManagement.purchaseOrderManagement.supplier.dto.AssignToCategoryReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierRegReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierUpdateReq;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@Tag(name = "Supplier API.")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSupplier() {
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long supplierId) {
        return ResponseEntity.ok(supplierService.getSupplierById(supplierId));
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Valid SupplierRegReq supplierRegReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(supplierRegReq));
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long supplierId, @RequestBody @Valid SupplierUpdateReq updateReq) {
        return ResponseEntity.ok(supplierService.updateSupplier(supplierId, updateReq));
    }

    @PutMapping("/assign-to-category")
    public ResponseEntity<Supplier> assignSupplierToCategory(@RequestBody @Valid AssignToCategoryReq assignToCategoryReq) {
        return ResponseEntity.ok(supplierService.assignSupplierToCategory(assignToCategoryReq));
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<ApiResponse> deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ApiResponse.success("Supplier Detail deleted successfully");
    }
}
