package com.batteryworkshop.models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Long id;
    private String name;
    private String email;

    /**
     * @brief List of batteries owned by the client.
     */
    private List<Battery> batteries;

    /**
     * @param id    The ID of the client.
     * @param name  The name of the client.
     * @param email The email of the client.
     * @brief Constructs a new Client and initializes the battery list.
     */
    public Client(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.batteries = new ArrayList<>();
    }

    /**
     * @return The ID of the client.
     * @brief Gets the client's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The ID to set for the client.
     * @brief Sets the client's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The name of the client.
     * @brief Gets the client's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set for the client.
     * @brief Sets the client's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The email of the client.
     * @brief Gets the client's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email to set for the client.
     * @brief Sets the client's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The list of batteries owned by the client.
     * @brief Gets the list of batteries owned by the client.
     */
    public List<Battery> getBatteries() {
        return batteries;
    }

    /**
     * @param battery The battery to add to the client's list.
     * @brief Adds a new battery to the client's list of batteries.
     */
    public void addBattery(Battery battery) {
        this.batteries.add(battery);
    }

    /**
     * @param batteries The list of batteries to set for the client.
     * @brief Sets the client's list of batteries.
     */
    public void setBatteries(List<Battery> batteries) {
        this.batteries = batteries;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", batteries=" + batteries +
                '}';
    }
}
