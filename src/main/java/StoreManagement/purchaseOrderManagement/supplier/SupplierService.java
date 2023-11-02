package StoreManagement.purchaseOrderManagement.supplier;

import StoreManagement.itemManagement.category.Category;
import StoreManagement.purchaseOrderManagement.supplier.dto.AssignToCategoryReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierRegReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierUpdateReq;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSupplier();

    Supplier getSupplierById(Long supplierId);

    Supplier createSupplier(SupplierRegReq supplierRegReq);

    Supplier updateSupplier(Long supplierId, SupplierUpdateReq updateReq);

    Supplier assignSupplierToCategory(AssignToCategoryReq assignToCategoryReq);

    void deleteSupplier(Long supplierId);
}
