package StoreManagement.itemManagement.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNameContainingIgnoreCase(String itemName);

    List<Item> findByCategoryCategoryId(Integer categoryId);

    List<Item> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

}

