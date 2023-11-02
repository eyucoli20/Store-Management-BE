package StoreManagement.itemManagement.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryReq {

    @NotBlank(message = "Category Name is required")
    @Size(min = 2, message = "Category Name must be at least 2 characters")
    private String categoryName;
}
