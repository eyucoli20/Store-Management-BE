package StoreManagement.userManagement.user;

public enum UserStatus {
    ACTIVE,
    SUSPENDED,
    BANNED;

    public static UserStatus getEnum(String roleName) {
        return UserStatus.valueOf(roleName.toUpperCase());
    }
}
