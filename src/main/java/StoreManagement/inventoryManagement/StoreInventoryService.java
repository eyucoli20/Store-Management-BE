package StoreManagement.inventoryManagement;

import StoreManagement.inventoryManagement.dto.ItemSaleFromInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;

import java.util.List;

public interface StoreInventoryService {
    StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq);

    StoreInventoryResponse adjustInventoryQuantityAfterPurchaseOrder(Long storeInventoryId, StoreInventoryUpdateReq updateReq);

    void adjustInventoryQuantityAfterPurchaseOrder(Long storeId, Long itemId, Integer quantity);

    StoreInventoryResponse processItemSaleFromInventory(Long storeInventoryId, ItemSaleFromInventoryReq itemSaleFromInventoryReq);

    void monitorInventoryThresholdAndSendNotification(StoreInventory storeInventory);

    List<StoreInventoryResponse> getInventoriesByStore(Long storeId);

    List<StoreInventoryResponse> getAllStoreInventory();

    StoreInventoryResponse getStoreInventoryById(Long storeInventoryId);

    void deleteStoreInventory(Long storeInventoryId);

}
