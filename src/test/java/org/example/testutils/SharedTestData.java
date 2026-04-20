package org.example.testutils;

public class SharedTestData {

    private static final ThreadLocal<String> adminToken = new ThreadLocal<>();
    private static final ThreadLocal<String> customerToken = new ThreadLocal<>();
    private static final ThreadLocal<String> customerUsername = new ThreadLocal<>();
    private static final ThreadLocal<String> createdItemId = new ThreadLocal<>();
    private static final ThreadLocal<String> createdOrderId = new ThreadLocal<>();

    private static final String BACKUP_ITEM_ID = "1770891560866";

    public static void setAdminToken(String token) {
        adminToken.set(token);
    }

    public static String getAdminToken() {
        return adminToken.get();
    }

    public static void setCustomerToken(String token) {
        customerToken.set(token);
    }

    public static String getCustomerToken() {
        return customerToken.get();
    }

    public static void setCustomerUsername(String username) {
        customerUsername.set(username);
    }

    public static String getCustomerUsername() {
        return customerUsername.get();
    }

    public static void setCreatedItemId(String id) {
        createdItemId.set(id);
    }

    public static String getCreatedItemId() {
        String id = createdItemId.get();
        return id != null ? id : BACKUP_ITEM_ID;
    }

    public static void setCreatedOrderId(String id) {
        createdOrderId.set(id);
    }

    public static String getCreatedOrderId() {
        return createdOrderId.get();
    }

    public static String getBackupItemId() {
        return BACKUP_ITEM_ID;
    }
}
