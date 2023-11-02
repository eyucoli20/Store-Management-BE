package StoreManagement.itemManagement.item;

import StoreManagement.itemManagement.item.dto.ItemRegistrationReq;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.itemManagement.item.dto.ItemUpdateReq;

import java.math.BigDecimal;
import java.util.List;

public interface ItemService {
    ItemResponse createItem(ItemRegistrationReq itemRegistrationReq);

    ItemResponse updateItem(Long itemId, ItemUpdateReq itemUpdateReq);

    List<ItemResponse> getAllItems();

    ItemResponse getItemById(Long itemId);

    List<ItemResponse> searchItemsByItemName(String itemName);

    List<ItemResponse> searchItemsByCategory(Integer categoryId);

    List<ItemResponse> searchItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    void deleteItem(Long itemId);

    Item utilGetItemById(Long itemId);
}
