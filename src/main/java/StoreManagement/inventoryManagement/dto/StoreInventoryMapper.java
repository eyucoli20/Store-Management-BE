package StoreManagement.inventoryManagement.dto;

import StoreManagement.inventoryManagement.StoreInventory;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.user.Users;

public class StoreInventoryMapper {
    public static StoreInventoryResponse toStoreInventoryResponse(StoreInventory storeInventory) {

        Item item = storeInventory.getItem();
        ItemResponse itemResponse = ItemResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .category(item.getCategory().getCategoryName())
                .build();

        Store store = storeInventory.getStore();
        StoreResponse storeResponse = StoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .storeType(store.getStoreType())
                .build();

        Users user = storeInventory.getAddedBy();
        UserResponse addedBy = UserResponse
                .builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .role(user.getRole().getRoleName())
                .build();

        return StoreInventoryResponse.builder()
                .storeInventoryId(storeInventory.getStoreInventoryId())
                .store(storeResponse)
                .item(itemResponse)
                .quantity(storeInventory.getQuantity())
                .minThreshHold(storeInventory.getMinThreshold())
                .maxThreshHold(storeInventory.getMaxThreshold())
                .addedBy(addedBy)
                .createdAt(storeInventory.getCreatedAt())
                .updatedAt(storeInventory.getUpdatedAt())
                .build();
    }
}

