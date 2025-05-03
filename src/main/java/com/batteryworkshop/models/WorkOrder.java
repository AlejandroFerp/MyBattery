package com.batteryworkshop.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkOrder {
    private int workorder_id; // PK
    private int user_id; // FK
    private String description;
    private Date open_date;
    private Date close_date;
    private Status status;
    private List<Battery> batteries = new ArrayList<>();

    public enum Status {
        PENDING,
        STARTED,
        DELAYED,
        COMPLETED
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Getters y Setters para cada campo

    public int getWorkOrderId() {
        return workorder_id;
    }

    public void setWorkOrderId(int workorder_id) {
        this.workorder_id = workorder_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOpenDate() {
        return open_date;
    }

    public void setOpenDate(Date open_date) {
        this.open_date = open_date;
    }

    public Date getCloseDate() {
        return close_date;
    }

    public void setCloseDate(Date close_date) {
        this.close_date = close_date;
    }

    public List<Battery> getBatteries() {
        return batteries;
    }

    public void addBattery(@org.jetbrains.annotations.NotNull Battery battery) {
        battery.setCompleted(false);
        batteries.add(battery);
    }

    public void markBatteryAsCompleted(Battery battery) {
        if (batteries.contains(battery)) {
            battery.setCompleted(true); // Mark as completed (new property)
        }
    }

    public List<Battery> getCompletedBatteries() {
        return batteries.stream().filter(Battery::isCompleted).toList();
    }

    public List<Battery> getIncompleteBatteries() {
        return batteries.stream().filter(battery -> !battery.isCompleted()).toList();
    }

    public void removeBattery(Battery battery) {
        batteries.remove(battery);
    }
}

