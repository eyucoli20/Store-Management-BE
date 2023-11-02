package StoreManagement.storeManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByContactInformation(String contactInformation);

    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.storeName) LIKE CONCAT('%', LOWER(:query), '%') " +
            "OR LOWER(s.location) LIKE CONCAT('%', LOWER(:query), '%')")
    List<Store> searchStores(String query);

    List<Store> findByStoreType(StoreType storeType);

    List<Store> findByOpeningDate(LocalDate openingDate);

    List<Store> findByOpeningDateBetween(LocalDate startDate, LocalDate endDate);
}
