package StoreManagement.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateReq {

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 2, message = "Full name must be at least 2 characters")
    private String fullName;

    @Size(min = 4, message = "Username must be at least 4 characters")
    private String username;

}
