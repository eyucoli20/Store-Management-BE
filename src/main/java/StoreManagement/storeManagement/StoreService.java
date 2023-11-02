package StoreManagement.storeManagement;

import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;

import java.time.LocalDate;
import java.util.List;

public interface StoreService {
    StoreResponse createStore(StoreRegistrationReq registrationReq);

    StoreResponse updateStore(Long id, StoreUpdateReq updateReq);

    List<StoreResponse> getAllStores();

    StoreResponse getStoreById(Long storeId);

    List<StoreResponse> searchStoresByNameOrLocation(String query);

    List<StoreResponse> getStoresByStoreType(StoreType storeType);

    List<StoreResponse> getStoresByDateRange(LocalDate startDate, LocalDate endDate);

    List<StoreResponse> getStoresByOpeningDate(LocalDate openingDate);

    Store utilGetStoreById(Long storeId);

    void deleteStore(Long storeId);
}
