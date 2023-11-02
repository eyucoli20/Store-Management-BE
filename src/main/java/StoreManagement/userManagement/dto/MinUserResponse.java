package StoreManagement.userManagement.dto;

import lombok.Data;

@Data
public class MinUserResponse {

    private Long userId;

    private String fullName;

    private String role;
}
