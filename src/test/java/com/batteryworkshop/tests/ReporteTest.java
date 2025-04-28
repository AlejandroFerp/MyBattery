package com.batteryworkshop.tests;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.Reporte;
import com.batteryworkshop.models.ItemReparacion;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de la entidad Reporte.
 */
public class ReporteTest {

    @Test
    public void testCrearReporteBasico() {
        Reporte reporte = new Reporte();
        reporte.setPasoActual(2);
        reporte.setEstado("en_proceso");
        reporte.setAprobadoPorCliente(true);
        reporte.setCosteExtra(10f);
        reporte.setMotivoExtra("carcasa dañada");
        reporte.setTotal(75f);

        assertEquals(2, reporte.getPasoActual());
        assertEquals("en_proceso", reporte.getEstado());
        assertTrue(reporte.isAprobadoPorCliente());
        assertEquals(10f, reporte.getCosteExtra());
        assertEquals("carcasa dañada", reporte.getMotivoExtra());
        assertEquals(75f, reporte.getTotal());
    }

    @Test
    public void testReporteConBatteryYItems() {
        Battery battery = new Battery();
        battery.setModel("Model-X");
        battery.setBaseCost(50f);

        ItemReparacion item1 = new ItemReparacion();
        item1.setNombre("BMS");
        item1.setPrecio(10f);

        ItemReparacion item2 = new ItemReparacion();
        item2.setNombre("Carcasa");
        item2.setPrecio(15f);

        Reporte reporte = new Reporte();
        reporte.setBattery(battery);
        reporte.setItems(Arrays.asList(item1, item2));
        reporte.setCosteExtra(5f);
        reporte.setTotal(80f); // Simulación manual

        assertEquals("Model-X", reporte.getBattery().getModel());
        assertEquals(2, reporte.getItems().size());
        assertEquals(80f, reporte.getTotal());
    }

    @Test
    public void testGetJson() {
        Battery battery = new Battery();
        battery.setModel("BAT123");
        battery.setBaseCost(60f);

        ItemReparacion item1 = new ItemReparacion();
        item1.setNombre("BMS");
        item1.setPrecio(10f);

        ItemReparacion item2 = new ItemReparacion();
        item2.setNombre("Celdas");
        item2.setPrecio(5f);

        Reporte reporte = new Reporte();
        reporte.setBattery(battery);
        reporte.setItems(Arrays.asList(item1, item2));
        reporte.setCosteExtra(8f);
        reporte.setMotivoExtra("reparación difícil");
        reporte.setTotal(83f);

        String json = reporte.getJson();

        assertTrue(json.contains("\"bateria_modelo\": \"BAT123\""));
        assertTrue(json.contains("\"total\": 83.0"));
        assertTrue(json.contains("\"nombre\": \"BMS\""));
        assertTrue(json.contains("\"precio\": 10.0"));
        assertTrue(json.contains("\"motivo_extra\": \"reparación difícil\""));
    }
}
