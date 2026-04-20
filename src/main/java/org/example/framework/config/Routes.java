package org.example.framework.config;

public enum Routes {
    ITEMS("/items"),
    LOGIN("/login"),
    ORDERS("/orders");

    private final String path;

    Routes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
