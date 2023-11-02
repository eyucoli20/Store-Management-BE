package StoreManagement.storeManagement;

public enum StoreType {
    RETAIL("Retail"),
    WHOLESALE("Wholesale"),
    ONLINE("Online");

    private final String displayName;

    StoreType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static StoreType getStoreTypeEnum(String roleName) {
        return StoreType.valueOf(roleName.toUpperCase());
    }
}

