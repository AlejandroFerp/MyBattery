package com.batteryworkshop.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una batería registrada en el sistema del taller.
 * Esta entidad almacena la información básica de una batería y sus reportes de reparación asociados.
 * La batería tiene un modelo, descripción y coste base de reparación predefinido.
 *
 * @since 1.0
 */
@Entity
public class Battery {
    /**
     * Identificador único de la batería.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /**
     * Modelo o tipo de la batería.
     * Representa la referencia técnica o código interno del modelo.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private final String model;

    /**
     * Descripción detallada del modelo de batería.
     * Puede incluir especificaciones técnicas o notas adicionales.
     */
    private final String description;

    /**
     * Coste base de reparación determinado por el modelo de batería.
     * Se utiliza BigDecimal para garantizar precisión en los cálculos monetarios.
     * Este campo no puede ser nulo y tiene una precisión de 10 dígitos con 2 decimales.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private final BigDecimal baseCost;

    /**
     * Lista de reportes de reparación asociados a esta batería.
     * La relación es bidireccional y los reportes se eliminan en cascada si se elimina la batería.
     */
    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL)
    private final List<RepairReport> repairReports = new ArrayList<>();

    /**
     * Constructor protegido sin argumentos requerido por JPA.
     * No debe utilizarse directamente en el código de la aplicación.
     */
    protected Battery() {
        this.id = null;
        this.model = null;
        this.description = null;
        this.baseCost = null;
    }

    /**
     * Crea una nueva instancia de Battery con los datos básicos requeridos.
     *
     * @param model       El modelo o tipo de la batería (no puede ser null)
     * @param description La descripción detallada de la batería (puede ser null)
     * @param baseCost    El coste base de reparación (no puede ser null)
     * @throws IllegalArgumentException si model o baseCost son null
     */
    public Battery(String model, String description, BigDecimal baseCost) {
        if (model == null || baseCost == null) {
            throw new IllegalArgumentException("Model and baseCost cannot be null");
        }
        this.id = null; // JPA lo generará
        this.model = model;
        this.description = description;
        this.baseCost = baseCost;
    }

    /**
     * Obtiene el identificador único de la batería.
     *
     * @return el ID de la batería
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el modelo de la batería.
     *
     * @return el modelo de la batería
     */
    public String getModel() {
        return model;
    }

    /**
     * Obtiene la descripción de la batería.
     *
     * @return la descripción de la batería, puede ser null
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el coste base de reparación de la batería.
     *
     * @return el coste base de reparación
     */
    public BigDecimal getBaseCost() {
        return baseCost;
    }

    /**
     * Obtiene una lista inmutable de los reportes de reparación asociados a esta batería.
     * La lista retornada no puede ser modificada, para mantener la encapsulación.
     *
     * @return lista inmutable de reportes de reparación
     */
    public List<RepairReport> getRepairReports() {
        return Collections.unmodifiableList(repairReports);
    }

    /**
     * Crea y añade un nuevo reporte de reparación a la batería.
     * Mantiene la consistencia bidireccional de la relación entre Battery y RepairReport.
     *
     * @return el nuevo reporte de reparación creado y añadido
     */
    public RepairReport addRepairReport() {
        RepairReport report = new RepairReport(this);
        repairReports.add(report);
        return report;
    }
}