package StoreManagement.inventoryManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    Optional<StoreInventory> findByStoreStoreIdAndItemItemId(Long storeId, Long itemId);
    List<StoreInventory> findByStoreStoreId(Long storeId);
}

