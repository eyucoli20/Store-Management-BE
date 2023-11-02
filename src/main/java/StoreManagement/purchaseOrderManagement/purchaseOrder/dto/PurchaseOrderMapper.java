package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.purchaseOrderManagement.purchaseOrder.PurchaseOrder;
import StoreManagement.purchaseOrderManagement.supplier.Supplier;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.user.Users;

public class PurchaseOrderMapper {
    public static PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder) {

        Users user = purchaseOrder.getOrderedBy();
        UserResponse orderedBy = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .role(user.getRole().getRoleName())
                .build();

        Item item = purchaseOrder.getItem();
        ItemResponse itemResponse = ItemResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .category(item.getCategory().getCategoryName())
                .build();

        Store store = purchaseOrder.getStore();
        StoreResponse storeResponse = StoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeType(store.getStoreType())
                .build();

        Supplier supplier = purchaseOrder.getSupplier();
        Supplier supplierResponse = new Supplier();
        supplierResponse.setSupplierId(supplier.getSupplierId());
        supplierResponse.setSupplierName(supplier.getSupplierName());
        supplierResponse.setSupplierAddress(supplier.getSupplierAddress());

        return PurchaseOrderResponse.builder()
                .purchaseOrderId(purchaseOrder.getPurchaseOrderId())
                .store(storeResponse)
                .item(itemResponse)

                .supplier(supplierResponse)
                .orderedBy(orderedBy)
                .quantity(purchaseOrder.getQuantity())

                .orderNumber(purchaseOrder.getOrderNumber())
                .purchaseOrderStatus(purchaseOrder.getPurchaseOrderStatus())
                .createdAt(purchaseOrder.getCreatedAt())
                .updatedAt(purchaseOrder.getUpdatedAt())
                .build();
    }
}
