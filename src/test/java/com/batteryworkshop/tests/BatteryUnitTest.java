package com.batteryworkshop.tests;

import com.batteryworkshop.models.Battery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitario puro sin Spring Boot. Solo prueba la lógica de Java.
 */
public class BatteryUnitTest {

    @Test
    public void testCrearBattery() {
        Battery battery = new Battery();
        battery.setModel("XP-1000");
        battery.setDescription("Batería de prueba");
        battery.setBaseCost(60.0f);

        assertEquals("XP-1000", battery.getModel());
        assertEquals("Batería de prueba", battery.getDescription());
        assertEquals(60.0f, battery.getBaseCost(), 0.001);
    }
}
