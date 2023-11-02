package StoreManagement.purchaseOrderManagement.purchaseOrder;


public enum PurchaseOrderStatus {
    PENDING,
    APPROVED,
    DELIVERED;

    public static PurchaseOrderStatus getEnum(String roleName) {
        return PurchaseOrderStatus.valueOf(roleName.toUpperCase());
    }
}
