package StoreManagement.storeManagement;

import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.storeManagement.dto.StoreMapper;
import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public StoreResponse createStore(StoreRegistrationReq registrationReq) {
        if (storeRepository.findByContactInformation((registrationReq.getContactInformation())).isPresent())
            throw new ResourceAlreadyExistsException("Contact Information is already taken");

        Store store = Store.builder()
                .storeName(registrationReq.getStoreName())
                .location(registrationReq.getLocation())
                .contactInformation(registrationReq.getContactInformation())
                .openingDate(registrationReq.getOpeningDate() != null ?
                        registrationReq.getOpeningDate() : LocalDate.now().plusDays(7))
                .storeType(StoreType.getStoreTypeEnum(registrationReq.getStoreType()))
                .createdBy(currentlyLoggedInUser.getUser())
                .build();

        Store savedStore = storeRepository.save(store);
        return StoreMapper.toStoreResponse(savedStore);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public StoreResponse updateStore(Long id, StoreUpdateReq updateReq) {
        Store store = getById(id);

        if (updateReq.getStoreName() != null)
            store.setStoreName(updateReq.getStoreName());

        if (updateReq.getLocation() != null)
            store.setLocation(updateReq.getLocation());

        if (updateReq.getContactInformation() != null && !store.getContactInformation().equals(updateReq.getContactInformation())) {
            // Check if the new contactInformation is already taken
            if (storeRepository.findByContactInformation((updateReq.getContactInformation())).isPresent())
                throw new ResourceAlreadyExistsException("Contact Information is already taken");

            store.setContactInformation(updateReq.getContactInformation());
        }

        if (updateReq.getOpeningDate() != null)
            store.setOpeningDate(updateReq.getOpeningDate());

        if (updateReq.getStoreType() != null)
            store.setStoreType(StoreType.getStoreTypeEnum(updateReq.getStoreType()));

        Store savedStore = storeRepository.save(store);
        return StoreMapper.toStoreResponse(savedStore);
    }

    @Override
    public List<StoreResponse> getAllStores() {
        List<Store> stores = storeRepository.findAll(Sort.by(Sort.Order.asc("storeId")));

        return stores.stream().
                map(StoreMapper::toStoreResponse)
                .toList();
    }

    @Override
    public StoreResponse getStoreById(Long storeId) {
        Store store = getById(storeId);
        return StoreMapper.toStoreResponse(store);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteStore(Long storeId) {
        utilGetStoreById(storeId);
        storeRepository.deleteById(storeId);
    }

    @Override
    public Store utilGetStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with ID: " + storeId));
    }

    @Override
    public List<StoreResponse> searchStoresByNameOrLocation(String query) {
        if (query == null)
            return getAllStores();

        List<Store> stores = storeRepository.searchStores(query);
        return stores.stream().
                map(StoreMapper::toStoreResponse)
                .toList();
    }

    @Override
    public List<StoreResponse> getStoresByStoreType(StoreType storeType) {
        List<Store> stores = storeRepository.findByStoreType(storeType);
        return stores.stream().
                map(StoreMapper::toStoreResponse)
                .toList();
    }

    @Override
    public List<StoreResponse> getStoresByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Store> stores = storeRepository.findByOpeningDateBetween(startDate, endDate);
        //sort by openingDate
        stores.sort(Comparator.comparing(Store::getOpeningDate));
        return stores.stream().
                map(StoreMapper::toStoreResponse)
                .toList();
    }

    @Override
    public List<StoreResponse> getStoresByOpeningDate(LocalDate openingDate) {
        List<Store> stores = storeRepository.findByOpeningDate(openingDate);
        return stores.stream().
                map(StoreMapper::toStoreResponse)
                .toList();
    }

    public Store getById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

}
