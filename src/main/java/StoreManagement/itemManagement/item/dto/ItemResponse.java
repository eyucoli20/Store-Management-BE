package StoreManagement.itemManagement.item.dto;

import StoreManagement.userManagement.dto.MinUserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponse {

    private Long itemId;

    private String itemName;

    private String description;

    private BigDecimal price;

    private Integer initialQuantity;

    private String category;

    private MinUserResponse createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
