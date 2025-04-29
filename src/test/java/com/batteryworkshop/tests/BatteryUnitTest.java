package com.batteryworkshop.tests;

import com.batteryworkshop.models.Battery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Battery.
 * Verifican el correcto funcionamiento de la creación y
 * gestión de objetos Battery.
 */
public class BatteryUnitTest {

    /**
     * Prueba la creación exitosa de una batería con todos los campos válidos.
     */
    @Test
    public void testCreateValidBattery() {
        String model = "XP-1000";
        String description = "Batería de prueba";
        BigDecimal baseCost = new BigDecimal("60.00");

        Battery battery = new Battery(model, description, baseCost);

        assertEquals(model, battery.getModel());
        assertEquals(description, battery.getDescription());
        assertEquals(baseCost, battery.getBaseCost());
        assertTrue(battery.getRepairReports().isEmpty());
    }

    /**
     * Prueba que se lance IllegalArgumentException al crear una batería con modelo null.
     */
    @Test
    public void testCreateBatteryWithNullModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Battery(null, "Description", new BigDecimal("60.00"));
        });
    }

    /**
     * Prueba que se lance IllegalArgumentException al crear una batería con coste base null.
     */
    @Test
    public void testCreateBatteryWithNullBaseCost() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Battery("XP-1000", "Description", null);
        });
    }

    /**
     * Prueba la creación de una batería con descripción null (permitido).
     */
    @Test
    public void testCreateBatteryWithNullDescription() {
        Battery battery = new Battery("XP-1000", null, new BigDecimal("60.00"));
        assertNull(battery.getDescription());
    }

    /**
     * Prueba la adición de reportes de reparación a una batería.
     */
    @Test
    public void testAddRepairReport() {
        Battery battery = new Battery("XP-1000", "Description", new BigDecimal("60.00"));

        battery.addRepairReport();
        battery.addRepairReport();

        assertEquals(2, battery.getRepairReports().size());
    }
}
