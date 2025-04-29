package com.batteryworkshop.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa un ítem utilizado durante una reparación de batería.
 * Cada ítem tiene un nombre identificativo y un precio que se suma al total del reporte.
 * Los ítems pueden ser componentes como BMS, carcasa o celdas.
 *
 * @since 1.0
 */
@Entity
@Table(name = "item_reparacion")
public class ItemReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /**
     * Nombre identificativo del ítem.
     */
    @Column(nullable = false, length = 100)
    private final String nombre;

    /**
     * Precio del ítem individual en la moneda base.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private final BigDecimal precio;

    /**
     * Reporte al cual está asociado este ítem.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id", nullable = false)
    private RepairReport reporte;

    /**
     * Constructor protegido requerido por JPA.
     */
    protected ItemReparacion() {
        this.id = null;
        this.nombre = null;
        this.precio = null;
        this.reporte = null;
    }

    /**
     * Crea una nueva instancia de ItemReparacion.
     *
     * @param nombre  nombre del ítem (no puede ser null o vacío)
     * @param precio  precio del ítem (no puede ser null o negativo)
     * @param reporte reporte asociado al ítem (no puede ser null)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public ItemReparacion(String nombre, BigDecimal precio, RepairReport reporte) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser null o negativo");
        }
        if (reporte == null) {
            throw new IllegalArgumentException("El reporte no puede ser null");
        }

        this.id = null;
        this.nombre = nombre;
        this.precio = precio;
        this.reporte = reporte;
    }

    /**
     * Obtiene el identificador único del ítem.
     *
     * @return el ID del ítem
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del ítem.
     *
     * @return el nombre del ítem
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del ítem.
     *
     * @return el precio del ítem
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Obtiene el reporte asociado al ítem.
     *
     * @return el reporte asociado
     */
    public RepairReport getReporte() {
        return reporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReparacion that = (ItemReparacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setReporte(RepairReport repairReport) {
        this.reporte=repairReport;
    }
}