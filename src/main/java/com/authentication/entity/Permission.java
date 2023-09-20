package com.authentication.entity;

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("user:read");

    private final String permission;

    Permission(String permission) {

        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}