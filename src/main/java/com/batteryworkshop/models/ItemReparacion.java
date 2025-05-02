package com.batteryworkshop.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @brief Repair item used during battery repairs
 * @details Each item has an identifying name and price that contributes to the total report cost.
 * Items can be components like BMS, casing or cells.
 * @since 1.0
 */
@Entity
@Table(name = "item_reparacion")
public class ItemReparacion {

    /**
     * @brief Unique identifier for the repair item
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    /**
     * @brief Identifying name of the item
     * @details Name cannot be null and has max length of 100 characters
     */
    @Column(nullable = false, length = 100)
    private final String nombre;

    /**
     * @brief Individual item price in base currency
     * @details Price stored with precision of 10 digits and 2 decimal places
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private final BigDecimal precio;

    /**
     * @brief Associated repair report
     * @details Many-to-one relationship with lazy loading. Can be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id", nullable = true)
    private RepairReport reporte;

    /**
     * @brief Protected no-args constructor required by JPA
     */
    protected ItemReparacion() {
        this.id = null;
        this.nombre = null;
        this.precio = null;
        this.reporte = null;
    }

    /**
     * @param nombre Name of the item (cannot be null or empty)
     * @param precio Price of the item (cannot be null or negative)
     * @throws IllegalArgumentException if nombre or precio parameters are invalid
     * @brief Creates a new repair item instance without an associated report
     */
    public ItemReparacion(String nombre, BigDecimal precio) {
        this(nombre, precio, null);
    }

    /**
     * @param nombre  Name of the item (cannot be null or empty)
     * @param precio  Price of the item (cannot be null or negative)
     * @param reporte Associated repair report (optional)
     * @throws IllegalArgumentException if nombre or precio parameters are invalid
     * @brief Creates a new repair item instance
     */
    public ItemReparacion(String nombre, BigDecimal precio, RepairReport reporte) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser null o negativo");
        }

        this.id = null;
        this.nombre = nombre;
        this.precio = precio;
        this.reporte = reporte;
    }

    /**
     * @return ID of the repair item
     * @brief Gets the unique identifier of the item
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Name of the repair item
     * @brief Gets the name of the item
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Price of the repair item
     * @brief Gets the price of the item
     */
    public BigDecimal getPrecio() {
        return this.precio;
    }

    /**
     * @return Associated repair report
     * @brief Gets the associated repair report
     */
    public RepairReport getReporte() {
        return reporte;
    }

    /**
     * @param o Object to compare with
     * @return true if items have same ID, false otherwise
     * @brief Checks if two repair items are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReparacion that = (ItemReparacion) o;
        return Objects.equals(id, that.id);
    }

    /**
     * @return Hash code based on item ID
     * @brief Generates hash code for the repair item
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @param repairReport Report to associate with this item
     * @brief Sets the associated repair report
     */
    public void setReporte(RepairReport repairReport) {
        this.reporte = repairReport;
    }
}