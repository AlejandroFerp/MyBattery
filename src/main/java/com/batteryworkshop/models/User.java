package com.batteryworkshop.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String password;
    private String role;
    private static final Map<String, String[]> PERMISSIONS = new HashMap<>();

    static {
        PERMISSIONS.put("SUPERVISOR", new String[]{"RESTRICTED", "BASIC"});
        PERMISSIONS.put("OPERATOR", new String[]{"BASIC"});
    }

    public User(String name, String password) {
        this(name, password, "OPERATOR");
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role.toUpperCase();
    }

    public boolean login(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }

    public boolean hasPermission(String permission) {
        String[] permissions = PERMISSIONS.get(this.role);
        if (permissions == null) return false;
        for (String p : permissions) {
            if (p.equals(permission)) return true;
        }
        return false;
    }
}