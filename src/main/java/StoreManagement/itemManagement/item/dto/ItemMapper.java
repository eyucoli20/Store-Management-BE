package StoreManagement.itemManagement.item.dto;

import StoreManagement.itemManagement.category.Category;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.userManagement.dto.MinUserResponse;
import StoreManagement.userManagement.user.Users;

public class ItemMapper {
    public static ItemResponse toItemResponse(Item item) {

        Users user = item.getAddedBy();

        MinUserResponse createdBy = new MinUserResponse();
        createdBy.setUserId(user.getId());
        createdBy.setFullName(user.getFullName());
        createdBy.setRole(user.getRole().getRoleName());

        return ItemResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .description(item.getDescription())
                .price(item.getPrice())
                .initialQuantity(item.getInitialQuantity())
                .category(item.getCategory().getCategoryName())
                .createdBy(createdBy)
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public static Item convertToEntity(ItemRegistrationReq itemRegistrationReq, Category category, Users loggedInUserUser) {
        Item item = new Item();
        item.setItemName(itemRegistrationReq.getItemName());
        item.setDescription(itemRegistrationReq.getDescription());
        item.setPrice(itemRegistrationReq.getPrice());
        item.setInitialQuantity(itemRegistrationReq.getInitialQuantity());
        item.setCategory(category);
        item.setAddedBy(loggedInUserUser);

        return item;
    }
}

