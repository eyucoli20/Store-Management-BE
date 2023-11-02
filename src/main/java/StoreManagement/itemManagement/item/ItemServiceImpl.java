package StoreManagement.itemManagement.item;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.category.Category;
import StoreManagement.itemManagement.category.CategoryService;
import StoreManagement.itemManagement.item.dto.ItemMapper;
import StoreManagement.itemManagement.item.dto.ItemRegistrationReq;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.itemManagement.item.dto.ItemUpdateReq;
import StoreManagement.userManagement.user.Users;
import StoreManagement.utils.CurrentlyLoggedInUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final CategoryService categoryService;

    @Override
    public ItemResponse createItem(ItemRegistrationReq itemRegistrationReq) {
        Users loggedInUserUser = currentlyLoggedInUser.getUser();
        Category category = categoryService.getCategoryById(itemRegistrationReq.getCategoryId());

        Item item = ItemMapper.convertToEntity(itemRegistrationReq, category, loggedInUserUser);
        item = itemRepository.save(item);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    @Transactional
    public ItemResponse updateItem(Long itemId, ItemUpdateReq itemUpdateReq) {
        Item item = utilGetItemById(itemId);
        if (itemUpdateReq.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(itemUpdateReq.getCategoryId());
            item.setCategory(category);
        }

        if (itemUpdateReq.getItemName() != null)
            item.setItemName(itemUpdateReq.getItemName());

        if (itemUpdateReq.getDescription() != null)
            item.setDescription(itemUpdateReq.getDescription());

        if (itemUpdateReq.getPrice() != null)
            item.setPrice(itemUpdateReq.getPrice());

        if (itemUpdateReq.getInitialQuantity() != null)
            item.setInitialQuantity(itemUpdateReq.getInitialQuantity());

        item = itemRepository.save(item);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll(Sort.by(Sort.Order.asc("itemId")));

        return items.stream()
                .map(ItemMapper::toItemResponse)
                .toList();
    }

    @Override
    public ItemResponse getItemById(Long itemId) {
        Item item = utilGetItemById(itemId);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    public List<ItemResponse> searchItemsByItemName(String itemName) {
        if (itemName == null)
            return getAllItems();

        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(itemName);
        return items.stream()
                .map(ItemMapper::toItemResponse)
                .toList();
    }

    @Override
    public List<ItemResponse> searchItemsByCategory(Integer categoryId) {
        List<Item> items = itemRepository.findByCategoryCategoryId(categoryId);
        return items.stream()
                .map(ItemMapper::toItemResponse)
                .toList();
    }

    @Override
    public List<ItemResponse> searchItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null)
            minPrice = BigDecimal.ZERO;

        if (maxPrice == null)
            maxPrice = BigDecimal.valueOf(Double.MAX_VALUE);

        if (maxPrice.compareTo(minPrice) < 0)
            throw new BadRequestException("Max price must be greater than or equal to min price");

        List<Item> items = itemRepository.findByPriceBetween(minPrice, maxPrice);
        return items.stream()
                .map(ItemMapper::toItemResponse)
                .toList();
    }


    @Override
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId))
            throw new ResourceNotFoundException("Item not found with ID: " + itemId);

        itemRepository.deleteById(itemId);
    }

    @Override
    public Item utilGetItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));
    }
}
