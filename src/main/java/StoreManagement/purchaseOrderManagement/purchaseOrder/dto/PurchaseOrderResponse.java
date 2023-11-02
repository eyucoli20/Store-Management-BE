package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.purchaseOrderManagement.purchaseOrder.PurchaseOrderStatus;
import StoreManagement.purchaseOrderManagement.supplier.Supplier;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.user.Users;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseOrderResponse {
    private Long purchaseOrderId;
    private StoreResponse store;
    private ItemResponse item;
    private Supplier supplier;
    private UserResponse orderedBy;
    private int quantity;
    private String orderNumber;
    private PurchaseOrderStatus purchaseOrderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
