package com.batteryworkshop.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repair report entity for batteries.
 * Contains complete repair process information including current status, costs and service details.
 *
 * @since 1.0
 */
@Entity
public class RepairReport {
    /**
     * Template for generating JSON report summary
     */
    private static final String JSON_TEMPLATE = """
            {
              "bateria_modelo": "%s",
              "coste_base": %s,
              "items": [
            %s
              ],
              "coste_extra": %s,
              "motivo_extra": "%s",
              "total": %s
            }""";

    /**
     * Unique report identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /**
     * Current step in repair process
     */
    private int currentStep;

    /**
     * Current repair status
     */
    private String status;

    /**
     * Reason if repair was cancelled
     */
    private String cancellationReason;

    /**
     * Indicates if customer approved repair
     */
    private boolean customerApproved;

    /**
     * Additional costs not included in initial budget
     */
    private BigDecimal extraCost;

    /**
     * Justification for additional costs
     */
    private String extraReason;

    /**
     * Total repair cost including all items
     */
    private BigDecimal total;

    /**
     * Report summary in JSON format
     */
    @Column(columnDefinition = "TEXT")
    private String jsonSummary;

    /**
     * Battery associated with this repair report
     */
    @ManyToOne
    private final Battery battery;

    /**
     * List of repair items included
     */
    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL)
    private final List<ItemReparacion> items = new ArrayList<>();

    /**
     * Protected no-args constructor required by JPA.
     * Should not be used directly in application code.
     */
    public RepairReport() {
        this.id = null;
        this.battery = null;
    }

    /**
     * Creates a new repair report for a specific battery
     *
     * @param battery Battery associated with report
     * @throws IllegalArgumentException if battery is null
     */
    public RepairReport(Battery battery) {
        if (battery == null) {
            throw new IllegalArgumentException("Battery cannot be null");
        }
        this.id = null;
        this.battery = battery;
        this.extraCost = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    /**
     * Generates report summary in JSON format.
     * Includes battery information, costs and repair items.
     *
     * @return JSON string with report summary
     */
    public String generateJsonSummary() {
        String itemsJson = generateItemsJson();
        return String.format(JSON_TEMPLATE,
                getBatteryModel(),
                getBatteryBaseCost(),
                itemsJson,
                extraCost,
                extraReason,
                total);
    }

    /**
     * Generates JSON section for repair items
     *
     * @return JSON string with items details
     */
    private String generateItemsJson() {
        if (items.isEmpty()) {
            return "";
        }
        return items.stream()
                .map(item -> String.format("    { \"nombre\": \"%s\", \"precio\": %s }",
                        item.getNombre(), item.getPrecio()))
                .collect(Collectors.joining(",\n"));
    }

    /**
     * Gets associated battery model
     *
     * @return Battery model or "unknown" if no battery associated
     */
    private String getBatteryModel() {
        return battery != null ? battery.getModel() : "desconocido";
    }

    /**
     * Gets base cost of associated battery
     *
     * @return Battery base cost or ZERO if no battery associated
     */
    private BigDecimal getBatteryBaseCost() {
        return battery != null ? battery.getBaseCost() : BigDecimal.ZERO;
    }

    // Getters

    /**
     * Gets report ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets current repair step
     */
    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Gets repair status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets customer approval status
     */
    public boolean isCustomerApproved() {
        return customerApproved;
    }

    /**
     * Gets extra costs
     */
    public BigDecimal getExtraCost() {
        return extraCost;
    }

    /**
     * Gets total cost
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Gets associated battery
     */
    public Battery getBattery() {
        return battery;
    }

    /**
     * Gets immutable view of repair items list
     *
     * @return Immutable list of repair items
     */
    public List<ItemReparacion> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Adds new repair item
     *
     * @param item Item to add
     * @throws IllegalArgumentException if item is null
     */
    public void addItem(ItemReparacion item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
        item.setReporte(this);
    }

    // Required setters

    /**
     * Sets current repair step
     */
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Sets repair status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets cancellation reason
     */
    public void setCancellationReason(String reason) {
        this.cancellationReason = reason;
    }

    /**
     * Sets customer approval
     */
    public void setCustomerApproved(boolean approved) {
        this.customerApproved = approved;
    }

    /**
     * Sets extra cost
     */
    public void setExtraCost(BigDecimal cost) {
        this.extraCost = cost;
    }

    /**
     * Sets extra cost reason
     */
    public void setExtraReason(String reason) {
        this.extraReason = reason;
    }

    /**
     * Sets total cost
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Gets repair status
     */
    public String getEstado() {
        return this.status;
    }

    /**
     * Sets repair status
     */
    public void setEstado(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Sets current step
     */
    public void setPasoActual(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Gets extra cost reason
     */
    public String getExtraReason() {
        return this.extraReason;
    }
    /**
     * Gets cancellation reason
     */
    public String getCancellationReason() {
        return cancellationReason;
    }
}