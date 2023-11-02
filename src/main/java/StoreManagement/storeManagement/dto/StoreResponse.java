package StoreManagement.storeManagement.dto;

import StoreManagement.storeManagement.StoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreResponse {

    private Long storeId;

    private String storeName;

    private String location;

    private String contactInformation;

    private StoreType storeType;

    private LocalDate openingDate;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;
}
