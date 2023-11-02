package StoreManagement.inventoryManagement.dto;

import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.userManagement.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreInventoryResponse {

    private Long storeInventoryId;

    private StoreResponse store;
    private ItemResponse item;

    private int quantity;

    private int minThreshHold;

    private int maxThreshHold;

    private UserResponse addedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
