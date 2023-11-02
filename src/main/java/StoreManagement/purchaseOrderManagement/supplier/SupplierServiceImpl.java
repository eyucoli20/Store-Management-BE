package StoreManagement.purchaseOrderManagement.supplier;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.category.Category;
import StoreManagement.itemManagement.category.CategoryService;
import StoreManagement.purchaseOrderManagement.supplier.dto.AssignToCategoryReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierRegReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierUpdateReq;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final CategoryService categoryService;

    public SupplierServiceImpl(SupplierRepository supplierRepository, CategoryService categoryService) {
        this.supplierRepository = supplierRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with ID: " + supplierId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Supplier createSupplier(SupplierRegReq supplierRegReq) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierRegReq.getSupplierName());
        supplier.setSupplierAddress(supplierRegReq.getSupplierAddress());

        return supplierRepository.save(supplier);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Supplier updateSupplier(Long supplierId, SupplierUpdateReq updateReq) {
        Supplier existingSupplier = getSupplierById(supplierId);

        if (updateReq.getSupplierName() != null)
            existingSupplier.setSupplierName(updateReq.getSupplierName());

        if (updateReq.getSupplierAddress() != null)
            existingSupplier.setSupplierAddress(updateReq.getSupplierAddress());

        return supplierRepository.save(existingSupplier);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Supplier assignSupplierToCategory(AssignToCategoryReq assignToCategoryReq) {
        Supplier supplier = getSupplierById(assignToCategoryReq.getSupplierId());
        Category category = categoryService.getCategoryById(assignToCategoryReq.getCategoryId());

        category.setSupplier(supplier);
        categoryService.utilSaveCategory(category);
        return supplier;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSupplier(Long supplierId) {
        Supplier existingSupplier = getSupplierById(supplierId);
        supplierRepository.delete(existingSupplier);
    }
}
