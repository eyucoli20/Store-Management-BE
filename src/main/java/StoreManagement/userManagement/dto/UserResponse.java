package StoreManagement.userManagement.dto;

import StoreManagement.userManagement.user.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String role;

    private UserStatus userStatus;

    private LocalDateTime lastLogin;

    private LocalDateTime registeredAt;

    private String registeredBy;

    private LocalDateTime updatedAt;

}
