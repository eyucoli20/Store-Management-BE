package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderResponse;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderUpdateReq;

import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrderResponse> getAllPurchaseOrders();
    PurchaseOrderResponse getPurchaseOrderById(Long orderId);
    PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest);
    PurchaseOrderResponse updatePurchaseOrder(Long orderId, PurchaseOrderUpdateReq updateReq);

    PurchaseOrderResponse updatePurchaseOrderStatus(Long orderId,String status);

    void deletePurchaseOrder(Long orderId);
}
