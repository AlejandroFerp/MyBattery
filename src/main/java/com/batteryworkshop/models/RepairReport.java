package com.batteryworkshop.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
     * @param battery Battery associated with report
     * @throws IllegalArgumentException if battery is null
     * @brief Creates a new repair report for a specific battery.
     * @details Initializes a repair report with zero extra cost and total cost.
     */
    public RepairReport(Battery battery) {
        if (battery == null) {
            throw new IllegalArgumentException("Battery cannot be null");
        }
        this.id = null;
        this.battery = battery;
        this.extraCost = BigDecimal.ZERO;
        this.total = TotalCalculator();
    }

    /**
     * Verifies the quality of a battery using parameters such as voltage and amperage differences,
     * and charging/discharging statuses.
     *
     * @param battery  The battery to verify.
     * @param voltaje  Voltage to compare with battery's voltage.
     * @param amperaje Amperage to compare with battery's amperage.
     * @param carga    Expected charging status.
     * @param descarga Expected discharging status.
     * @return True if battery quality meets the defined criteria; false otherwise.
     */
    public static boolean isQualityOk(
            Battery battery,
            BigDecimal voltaje,
            BigDecimal amperaje,
            boolean carga,
            boolean descarga) {
        if (battery == null || voltaje == null || amperaje == null
                || battery.getVoltage() == null || battery.getAmpere() == null) {
            return false;
        }

        BigDecimal voltajeDiff = battery.getVoltage().subtract(voltaje).abs();
        BigDecimal amperajeDiff = battery.getAmpere().subtract(amperaje).abs();
        BigDecimal margin = BigDecimal.valueOf(0.5); // Margen configurable

        return voltajeDiff.compareTo(margin) <= 0
                && amperajeDiff.compareTo(margin) <= 0
                && battery.isCharge() == carga
                && battery.isDischarge() == descarga;
    }

    /**
     * Calculates total repair cost by adding battery base cost,
     * repair items cost, and extra costs if any.
     *
     * @return Total cost of the repair work in euros.
     */
    public BigDecimal TotalCalculator() {
        // Punto 1: Inicio del método
        System.out.println("=== MEMORIA DE DEPURACIÓN ===");
        System.out.println("Inicio del cálculo del total");

        BigDecimal costeTotalItems = Optional.ofNullable(items)
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> {
                    // Punto 2: Por cada item
                    System.out.printf("Item procesado: %s - Precio: %s%n",
                            item.getNombre(), item.getPrecio());
                    return item.getPrecio();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Punto 3: Resultados parciales
        System.out.println("Resultados parciales:");
        System.out.printf("- Costo base de batería: %s%n", battery.getBaseCost());
        System.out.printf("- Total de items: %s%n", costeTotalItems);
        System.out.printf("- Costo extra: %s%n", this.extraCost);

        BigDecimal total = battery.getBaseCost()
                .add(costeTotalItems)
                .add(this.extraCost);

        // Punto 4: Resultado final
        System.out.printf("Resultado final: %s%n", total);
        System.out.println("=== FIN DE MEMORIA ===");

        return total;
    }

    /**
     * Generates a JSON summary of the repair report.
     * Includes battery information, costs, and repair items.
     *
     * @return JSON string containing the repair report summary.
     */
    public String generateJsonSummary() {
        this.total = TotalCalculator();
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
     * Generates the JSON section for repair items.
     *
     * @return JSON string containing the details of repair items.
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
     * Retrieves the associated battery's model.
     *
     * @return Battery model as a string, or "desconocido" if battery is null.
     */
    private String getBatteryModel() {
        return battery != null ? battery.getModel() : "desconocido";
    }

    /**
     * Retrieves the base cost of the associated battery.
     *
     * @return Battery base cost as a BigDecimal, or ZERO if battery is null.
     */
    private BigDecimal getBatteryBaseCost() {
        return battery != null ? battery.getBaseCost() : BigDecimal.ZERO;
    }

    // Getters

    /**
     * Retrieves the report ID.
     *
     * @return The unique report identifier as a Long.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the current step in the repair process.
     *
     * @return The current repair step as an integer.
     */
    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Retrieves the repair status.
     *
     * @return The current status of the repair as a string.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Checks whether the customer approved the repair process.
     *
     * @return True if customer approved the repair; false otherwise.
     */
    public boolean isCustomerApproved() {
        return customerApproved;
    }

    /**
     * Retrieves the additional costs incurred during the repair.
     *
     * @return Extra costs as a BigDecimal.
     */
    public BigDecimal getExtraCost() {
        return extraCost;
    }

    /**
     * Retrieves the total repair cost.
     *
     * @return Total cost as a BigDecimal.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Retrieves the battery associated with the repair report.
     *
     * @return The associated Battery object.
     */
    public Battery getBattery() {
        return battery;
    }

    /**
     * Retrieves an immutable view of the list of repair items.
     *
     * @return An unmodifiable list of repair items.
     */
    public List<ItemReparacion> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Adds new repair item to the repair report.
     *
     * @param item The repair item to add.
     * @throws IllegalArgumentException if item is null.
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
     * Updates the current step in the repair process.
     *
     * @param currentStep New step value to set.
     */
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Updates the repair status.
     *
     * @param status New status value to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Updates the cancellation reason of the repair.
     *
     * @param reason The reason for cancellation.
     */
    public void setCancellationReason(String reason) {
        this.cancellationReason = reason;
    }

    /**
     * Updates the customer's approval status.
     *
     * @param approved True if customer approved; false otherwise.
     */
    public void setCustomerApproved(boolean approved) {
        this.customerApproved = approved;
    }

    /**
     * Updates the extra cost value for the repair.
     *
     * @param cost New extra cost as a BigDecimal.
     */
    public void setExtraCost(BigDecimal cost) {
        this.extraCost = cost;
    }

    /**
     * Updates the reason provided for additional repair costs.
     *
     * @param reason Reason for extra costs.
     */
    public void setExtraReason(String reason) {
        this.extraReason = reason;
    }

    /**
     * Updates the total repair cost.
     *
     * @param total New total value to set as a BigDecimal.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Retrieves the repair status.
     *
     * @return Repair status as a string.
     */
    public String getEstado() {
        return this.status;
    }

    /**
     * Updates the repair status.
     *
     * @param newStatus New status value to update.
     */
    public void setEstado(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Updates the current step in the repair process.
     *
     * @param currentStep New step value to set.
     */
    public void setPasoActual(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Retrieves the justification for extra repair costs.
     *
     * @return Reason for extra costs as a string.
     */
    public String getExtraReason() {
        return this.extraReason;
    }

    /**
     * Retrieves the reason for repair cancellation.
     *
     * @return Cancellation reason as a string.
     */
    public String getCancellationReason() {
        return cancellationReason;
    }
}