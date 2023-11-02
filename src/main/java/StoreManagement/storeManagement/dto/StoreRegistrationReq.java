package StoreManagement.storeManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StoreRegistrationReq {

    @NotBlank(message = "Store Name is required")
    @Size(min = 2, message = "Store name must be at least 2 characters")
    private String storeName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Contact Information is required")
    private String contactInformation;

    @NotNull(message = "Opening Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openingDate;

    @NotBlank(message = "Store type is required")
    @Pattern(regexp = "^(?i)(RETAIL|WHOLESALE|ONLINE)$", message = "Invalid Store type. Valid store types are RETAIL, WHOLESALE or ONLINE.")
    private String storeType; // string representation of the storeType

}