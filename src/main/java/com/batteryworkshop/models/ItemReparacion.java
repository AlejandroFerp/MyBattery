package com.batteryworkshop.models;

import jakarta.persistence.*;

/**
 * Ítem usado durante una reparación (como BMS, carcasa o celdas).
 * Cada ítem tiene un precio que se suma al total del reporte.
 */
@Entity
public class ItemReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del ítem (ej: "BMS", "Carcasa", "Celdas").
     */
    private String nombre;

    /**
     * Precio del ítem individual, usado para calcular el total del reporte.
     */
    private float precio;

    /**
     * Reporte al cual pertenece este ítem.
     */
    @ManyToOne
    private Reporte reporte;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public Reporte getReporte() { return reporte; }
    public void setReporte(Reporte reporte) { this.reporte = reporte; }
}
