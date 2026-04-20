package org.example.framework.auth;

public class AuthManager {

    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static void setToken(String value) {
        token.set(value);
    }

    public static String getToken() {
        return token.get();
    }

    public static void clear() {
        token.remove();
    }
}