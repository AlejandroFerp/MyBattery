package com.batteryworkshop.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief Represents a user in the system.
 * @details This class manages user data, authentication, and permission checks.
 */
public class User {
    /**
     * @brief User's name or identifier.
     */
    private String name;

    /**
     * @brief User's password.
     */
    private String password;

    /**
     * @brief User's role, such as "SUPERVISOR" or "OPERATOR".
     */
    private String role;

    /**
     * @brief Defines permissions based on role.
     * @details The keys are roles, and the values are arrays of permissions for the role.
     */
    private static final Map<String, String[]> PERMISSIONS = new HashMap<>();

    static {
        PERMISSIONS.put("SUPERVISOR", new String[]{"RESTRICTED", "BASIC"});
        PERMISSIONS.put("OPERATOR", new String[]{"BASIC"});
    }

    /**
     * @param name     The name of the user.
     * @param password The password of the user.
     * @brief Constructs a new User with a default role of "OPERATOR".
     */
    public User(String name, String password) {
        this(name, password, "OPERATOR");
    }

    /**
     * @param name     The name of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     * @brief Constructs a new User with a specified role.
     */
    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role.toUpperCase();
    }

    /**
     * @param name     The name to authenticate.
     * @param password The password to authenticate.
     * @return True if the name and password match, otherwise false.
     * @brief Authenticates the user by validating the name and password.
     */
    public boolean login(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }

    /**
     * @param permission The permission to check.
     * @return True if the user has the specified permission, otherwise false.
     * @brief Checks if the user has a specific permission.
     */
    public boolean hasPermission(String permission) {
        String[] permissions = PERMISSIONS.get(this.role);
        if (permissions == null) return false;
        for (String p : permissions) {
            if (p.equals(permission)) return true;
        }
        return false;
    }

    /**
     * @return The name of the user.
     * @brief Gets the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The password of the user.
     * @brief Gets the password of the user.
     */
    public String getPassword() {
        return password;
    }
}