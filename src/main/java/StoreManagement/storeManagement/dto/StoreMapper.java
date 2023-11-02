package StoreManagement.storeManagement.dto;

import StoreManagement.storeManagement.Store;

public class StoreMapper {
    public static StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .location(store.getLocation())
                .contactInformation(store.getContactInformation())
                .storeType(store.getStoreType())
                .openingDate(store.getOpeningDate())
                .createdAt(store.getCreatedAt())
                .createdBy(store.getCreatedBy().getFullName())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}

