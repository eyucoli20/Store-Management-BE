package StoreManagement.userManagement.dto;

import StoreManagement.userManagement.user.Users;

public class UserMapper {
    public static UserResponse toUserResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().getRoleName())
                .userStatus(user.getUserStatus())
                .lastLogin(user.getLastLogin())
                .registeredAt(user.getRegisteredAt())
                .registeredBy(user.getRegisteredBy() != null ? user.getRegisteredBy().getFullName(): "")
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

