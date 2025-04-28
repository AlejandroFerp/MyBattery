package com.batteryworkshop.models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Representa una batería registrada en el sistema del taller.
 * Cada batería puede tener múltiples reportes de reparación.
 *
 * El coste base de reparación se define según el modelo de batería.
 */
@Entity
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Modelo o tipo de la batería (referencia técnica o código interno).
     */
    private String model;

    /**
     * Descripción adicional del modelo de batería.
     */
    private String description;

    /**
     * Coste base de reparación determinado por el modelo de batería.
     */
    private float baseCost;

    /**
     * Lista de reportes de reparación asociados a esta batería.
     */
    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL)
    private List<Reporte> reportes;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getBaseCost() { return baseCost; }
    public void setBaseCost(float baseCost) { this.baseCost = baseCost; }

    public List<Reporte> getReportes() { return reportes; }
    public void setReportes(List<Reporte> reportes) { this.reportes = reportes; }
}
