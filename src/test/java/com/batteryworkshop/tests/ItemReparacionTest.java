package com.batteryworkshop.tests;

import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.models.Reporte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de la clase ItemReparacion.
 */
public class ItemReparacionTest {

    @Test
    public void testCrearItem() {
        ItemReparacion item = new ItemReparacion();
        item.setNombre("BMS");
        item.setPrecio(15.0f);

        assertEquals("BMS", item.getNombre());
        assertEquals(15.0f, item.getPrecio(), 0.001);
    }

    @Test
    public void testAsociarConReporte() {
        Reporte reporte = new Reporte();
        reporte.setEstado("en_proceso");

        ItemReparacion item = new ItemReparacion();
        item.setNombre("Carcasa");
        item.setPrecio(20.0f);
        item.setReporte(reporte);

        assertNotNull(item.getReporte());
        assertEquals("en_proceso", item.getReporte().getEstado());
        assertEquals("Carcasa", item.getNombre());
    }
}
