package com.batteryworkshop.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa un informe de reparación para una batería.
 * Esta entidad mantiene el registro completo del proceso de reparación,
 * incluyendo el estado actual, costes y detalles del servicio.
 *
 * @since 1.0
 */
@Entity
public class RepairReport {
    /** Plantilla para la generación del resumen JSON del informe. */
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

    /** Identificador único del informe. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /** Paso actual en el proceso de reparación. */
    private int currentStep;

    /** Estado actual de la reparación. */
    private String status;

    /** Motivo de cancelación si la reparación fue cancelada. */
    private String cancellationReason;

    /** Indica si el cliente ha aprobado la reparación. */
    private boolean customerApproved;

    /** Costes adicionales no incluidos en el presupuesto inicial. */
    private BigDecimal extraCost;

    /** Justificación de los costes adicionales. */
    private String extraReason;

    /** Coste total de la reparación incluyendo todos los conceptos. */
    private BigDecimal total;

    /** Resumen del informe en formato JSON. */
    @Column(columnDefinition = "TEXT")
    private String jsonSummary;

    /** Batería asociada a este informe de reparación. */
    @ManyToOne
    private final Battery battery;

    /** Lista de ítems incluidos en la reparación. */
    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL)
    private final List<ItemReparacion> items = new ArrayList<>();

    /**
     * Constructor protegido sin argumentos requerido por JPA.
     * No debe utilizarse directamente en el código de la aplicación.
     */
    public RepairReport() {
        this.id = null;
        this.battery = null;
    }

    /**
     * Crea un nuevo informe de reparación para una batería específica.
     *
     * @param battery la batería asociada al informe
     * @throws IllegalArgumentException si battery es null
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
     * Genera un resumen del informe en formato JSON.
     * Incluye información sobre la batería, costes y elementos de reparación.
     *
     * @return cadena JSON con el resumen del informe
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
     * Genera la sección JSON correspondiente a los ítems de reparación.
     *
     * @return cadena JSON con los detalles de los ítems
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
     * Obtiene el modelo de la batería asociada.
     *
     * @return modelo de la batería o "desconocido" si no hay batería asociada
     */
    private String getBatteryModel() {
        return battery != null ? battery.getModel() : "desconocido";
    }

    /**
     * Obtiene el coste base de la batería.
     *
     * @return coste base de la batería o ZERO si no hay batería asociada
     */
    private BigDecimal getBatteryBaseCost() {
        return battery != null ? battery.getBaseCost() : BigDecimal.ZERO;
    }

    // Getters
    public Long getId() { return id; }
    public int getCurrentStep() { return currentStep; }
    public String getStatus() { return status; }
    public boolean isCustomerApproved() { return customerApproved; }
    public BigDecimal getExtraCost() { return extraCost; }
    public BigDecimal getTotal() { return total; }
    public Battery getBattery() { return battery; }

    /**
     * Obtiene una vista inmutable de la lista de ítems de reparación.
     *
     * @return lista inmutable de ítems de reparación
     */
    public List<ItemReparacion> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Añade un nuevo ítem a la reparación.
     *
     * @param item el ítem a añadir
     * @throws IllegalArgumentException si item es null
     */
    public void addItem(ItemReparacion item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
        item.setReporte(this);
    }

    // Setters necesarios
    public void setCurrentStep(int currentStep) { this.currentStep = currentStep; }
    public void setStatus(String status) { this.status = status; }
    public void setCancellationReason(String reason) { this.cancellationReason = reason; }
    public void setCustomerApproved(boolean approved) { this.customerApproved = approved; }
    public void setExtraCost(BigDecimal cost) { this.extraCost = cost; }
    public void setExtraReason(String reason) { this.extraReason = reason; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getEstado() {
        return this.status;
    }


    public void setEstado(String newStatus) {
        this.status=newStatus;
    }

    public void setPasoActual(int currentStep) {
        this.currentStep=currentStep;
    }

    public String getExtraReason() {
        return this.extraReason;
    }
}