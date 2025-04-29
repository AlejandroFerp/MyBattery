package com.batteryworkshop.tests;

import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.models.RepairReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para validar el comportamiento de ItemReparacion.
 */
public class ItemReparacionTest {

    private static final String NOMBRE_BMS = "BMS";
    private static final String NOMBRE_CARCASA = "Carcasa";
    private static final BigDecimal PRECIO_BMS = new BigDecimal("15.00");
    private static final BigDecimal PRECIO_CARCASA = new BigDecimal("20.00");
    private static final String ESTADO_EN_PROCESO = "en_proceso";

    /**
     * Verifica la creación correcta de un ItemReparacion con sus propiedades básicas.
     */
    @Test
    public void deberiaCrearItemConPropiedadesBasicas() {
        RepairReport reporte = new RepairReport();
        ItemReparacion item = crearItem(NOMBRE_BMS, PRECIO_BMS, reporte);

        assertEquals(NOMBRE_BMS, item.getNombre(), "El nombre del ítem debe coincidir");
        assertEquals(PRECIO_BMS, item.getPrecio(), "El precio del ítem debe coincidir");
    }

    /**
     * Verifica la correcta asociación entre un ItemReparacion y su RepairReport.
     */
    @Test
    public void deberiaAsociarItemConReporteCorrectamente() {
        RepairReport reporte = new RepairReport();
        reporte.setEstado(ESTADO_EN_PROCESO);

        ItemReparacion item = crearItem(NOMBRE_CARCASA, PRECIO_CARCASA, reporte);

        assertNotNull(item.getReporte(), "El reporte no debe ser null");
        assertEquals(ESTADO_EN_PROCESO, item.getReporte().getEstado(), "El estado del reporte debe coincidir");
        assertEquals(NOMBRE_CARCASA, item.getNombre(), "El nombre del ítem debe coincidir");
    }

    /**
     * Crea un nuevo ItemReparacion para pruebas.
     */
    private ItemReparacion crearItem(String nombre, BigDecimal precio, RepairReport reporte) {
        return new ItemReparacion(nombre, precio, reporte);
    }
}