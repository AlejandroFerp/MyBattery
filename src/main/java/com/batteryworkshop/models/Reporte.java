package com.batteryworkshop.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reporte de reparación para una batería.
 * Representa el ciclo completo de trabajo con pasos, estado y coste final.
 */
@Entity
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int pasoActual;
    private String estado;
    private String motivoCancelacion;
    private boolean aprobadoPorCliente;
    private float costeExtra;
    private String motivoExtra;
    private float total;

    @Column(columnDefinition = "TEXT")
    private String jsonResumen;

    @ManyToOne
    private Battery battery;

    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL)
    private List<ItemReparacion> items;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPasoActual() { return pasoActual; }
    public void setPasoActual(int pasoActual) { this.pasoActual = pasoActual; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMotivoCancelacion() { return motivoCancelacion; }
    public void setMotivoCancelacion(String motivoCancelacion) { this.motivoCancelacion = motivoCancelacion; }

    public boolean isAprobadoPorCliente() { return aprobadoPorCliente; }
    public void setAprobadoPorCliente(boolean aprobadoPorCliente) { this.aprobadoPorCliente = aprobadoPorCliente; }

    public float getCosteExtra() { return costeExtra; }
    public void setCosteExtra(float costeExtra) { this.costeExtra = costeExtra; }

    public String getMotivoExtra() { return motivoExtra; }
    public void setMotivoExtra(String motivoExtra) { this.motivoExtra = motivoExtra; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public String getJsonResumen() { return jsonResumen; }
    public void setJsonResumen(String jsonResumen) { this.jsonResumen = jsonResumen; }

    public Battery getBattery() { return battery; }
    public void setBattery(Battery battery) { this.battery = battery; }

    public List<ItemReparacion> getItems() { return items; }
    public void setItems(List<ItemReparacion> items) { this.items = items; }

    /**
     * Devuelve un resumen del reporte en formato JSON como texto plano.
     * Esta versión no usa librerías externas, es una simulación simple.
     *
     * @return String JSON del resumen del reporte
     */
    public String getJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"bateria_modelo\": \"" + (battery != null ? battery.getModel() : "desconocido") + "\",\n");
        json.append("  \"coste_base\": " + (battery != null ? battery.getBaseCost() : 0.0f) + ",\n");
        json.append("  \"items\": [\n");

        if (items != null && !items.isEmpty()) {
            String itemsJson = items.stream()
                    .map(item -> "    { \"nombre\": \"" + item.getNombre() + "\", \"precio\": " + item.getPrecio() + " }")
                    .collect(Collectors.joining(",\n"));
            json.append(itemsJson).append("\n");
        }

        json.append("  ],\n");
        json.append("  \"coste_extra\": " + costeExtra + ",\n");
        json.append("  \"motivo_extra\": \"" + motivoExtra + "\",\n");
        json.append("  \"total\": " + total + "\n");
        json.append("}");

        return json.toString();
    }
}
