package com.batteryworkshop.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @brief Represents a work order in the system.
 * @details A work order contains a list of batteries, user information, status, and important dates.
 */
public class WorkOrder {
    private int workorder_id;
    /// < Primary Key of the work order
    private int user_id;
    /// < Foreign Key referring to the user associated with the work order
    private String description;
    /// < Description of the work order
    private Date open_date;
    /// < Date the work order was opened
    private Date close_date;
    /// < Date the work order was closed
    private Status status;
    /// < Current status of the work order
    private List<Battery> batteries = new ArrayList<>(); ///< List of batteries associated with the work order

    /**
     * @brief Enum representing the possible statuses of a work order.
     */
    public enum Status {
        PENDING,
        /// < Work order is pending
        STARTED,
        /// < Work order has started
        DELAYED,
        /// < Work order is delayed
        COMPLETED ///< Work order is completed
    }

    /**
     * @return The current status of the work order.
     * @brief Gets the status of the work order.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The new status of the work order.
     * @brief Sets the status of the work order.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The ID of the work order.
     * @brief Gets the work order ID.
     */
    public int getWorkOrderId() {
        return workorder_id;
    }

    /**
     * @param workorder_id The new work order ID.
     * @brief Sets the work order ID.
     */
    public void setWorkOrderId(int workorder_id) {
        this.workorder_id = workorder_id;
    }

    /**
     * @return The user ID associated with the work order.
     * @brief Gets the user ID.
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * @param user_id The new user ID.
     * @brief Sets the user ID associated with the work order.
     */
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return The description of the work order.
     * @brief Gets the description of the work order.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The new description of the work order.
     * @brief Sets the description of the work order.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The date the work order was opened.
     * @brief Gets the open date of the work order.
     */
    public Date getOpenDate() {
        return open_date;
    }

    /**
     * @param open_date The new open date of the work order.
     * @brief Sets the open date of the work order.
     */
    public void setOpenDate(Date open_date) {
        this.open_date = open_date;
    }

    /**
     * @return The date the work order was closed.
     * @brief Gets the close date of the work order.
     */
    public Date getCloseDate() {
        return close_date;
    }

    /**
     * @param close_date The new close date of the work order.
     * @brief Sets the close date of the work order.
     */
    public void setCloseDate(Date close_date) {
        this.close_date = close_date;
    }

    /**
     * @return The list of batteries in the work order.
     * @brief Gets the batteries associated with the work order.
     */
    public List<Battery> getBatteries() {
        return batteries;
    }

    /**
     * @param battery The battery to be added to the work order.
     * @brief Adds a battery to the work order.
     * @details Newly added batteries are marked as incomplete by default.
     */
    public void addBattery(@org.jetbrains.annotations.NotNull Battery battery) {
        battery.setCompleted(false);
        batteries.add(battery);
    }

    /**
     * @param battery The battery to mark as completed.
     * @brief Marks a specified battery as completed.
     * @details Ensures the battery is part of the current work order before marking it as completed.
     */
    public void markBatteryAsCompleted(Battery battery) {
        if (batteries.contains(battery)) {
            battery.setCompleted(true); // Mark as completed (new property)
        }
    }

    /**
     * @return A list of completed batteries in the work order.
     * @brief Gets all completed batteries.
     */
    public List<Battery> getCompletedBatteries() {
        return batteries.stream().filter(Battery::isCompleted).toList();
    }

    /**
     * @return A list of incomplete batteries in the work order.
     * @brief Gets all incomplete batteries.
     */
    public List<Battery> getIncompleteBatteries() {
        return batteries.stream().filter(battery -> !battery.isCompleted()).toList();
    }

    /**
     * @param battery The battery to be removed from the work order.
     * @brief Removes a battery from the work order.
     */
    public void removeBattery(Battery battery) {
        batteries.remove(battery);
    }
}

