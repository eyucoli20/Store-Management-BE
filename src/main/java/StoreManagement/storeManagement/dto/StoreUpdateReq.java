package StoreManagement.storeManagement.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StoreUpdateReq {

    @Size(min = 2, message = "Store name must be at least 2 characters")
    private String storeName;

    private String location;

    private String contactInformation;

    private LocalDate openingDate;

    @Pattern(regexp = "^(?i)(RETAIL|WHOLESALE|ONLINE)$", message = "Invalid Store type. Valid roles are RETAIL, WHOLESALE or ONLINE.")
    private String storeType; // string representation of the storeType
}
