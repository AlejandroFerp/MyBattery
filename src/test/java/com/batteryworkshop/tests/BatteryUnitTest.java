package com.batteryworkshop.tests;

import com.batteryworkshop.models.Battery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Pruebas unitarias para la clase Battery.
 * @details Verifican el correcto funcionamiento de la creación y
 * gestión de objetos Battery.
 */
public class BatteryUnitTest {

    /**
     * @brief Prueba la creación exitosa de una batería con todos los campos válidos.
     * @details Verifica que el modelo, la descripción y el coste base se almacenen correctamente
     * y que inicialmente no haya reportes asociados.
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
     * @throws IllegalArgumentException Si el modelo es null.
     * @brief Prueba que se lance una excepción al crear una batería con modelo null.
     * @details Verifica que la clase Battery valide correctamente el modelo durante su creación.
     */
    @Test
    public void testCreateBatteryWithNullModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Battery(null, "Description", new BigDecimal("60.00"));
        });
    }

    /**
     * @throws IllegalArgumentException Si el coste base es null.
     * @brief Prueba que se lance una excepción al crear una batería con un coste base null.
     * @details Verifica que la clase Battery valide correctamente el coste base durante su creación.
     */
    @Test
    public void testCreateBatteryWithNullBaseCost() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Battery("XP-1000", "Description", null);
        });
    }

    /**
     * @brief Prueba la creación de una batería con una descripción null.
     * @details Verifica que las descripciones nulas sean permitidas por la clase Battery.
     */
    @Test
    public void testCreateBatteryWithNullDescription() {
        Battery battery = new Battery("XP-1000", null, new BigDecimal("60.00"));
        assertNull(battery.getDescription());
    }

    /**
     * @brief Prueba la adición de reportes de reparación a una batería.
     * @details Verifica que al añadir reportes de reparación, se actualice correctamente la lista en la batería.
     */
    @Test
    public void testAddRepairReport() {
        Battery battery = new Battery("XP-1000", "Description", new BigDecimal("60.00"));

        battery.addRepairReport();
        battery.addRepairReport();

        assertEquals(2, battery.getRepairReports().size());
    }
}
