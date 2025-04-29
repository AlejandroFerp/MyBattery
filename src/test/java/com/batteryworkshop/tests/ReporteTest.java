package com.batteryworkshop.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.RepairReport;

/**
 * Pruebas unitarias para la clase RepairReport.
 * Verifica el correcto funcionamiento de la creación y gestión de reportes de reparación.
 */
public class ReporteTest {

    /**
     * Batería de prueba utilizada en los tests
     */
    private Battery testBattery;

    /**
     * Reporte de prueba utilizado en los tests
     */
    private RepairReport report;

    /**
     * Configura el entorno de pruebas antes de cada test.
     * Crea una nueva batería y un reporte asociado.
     */
    @BeforeEach
    void setUp() {
        testBattery = new Battery("Test-Model", "Test Battery", new BigDecimal("100.00"));
        report = testBattery.addRepairReport();
    }

    /**
     * Verifica que la creación básica de un reporte sea correcta,
     * comprobando sus valores iniciales por defecto.
     */
    @Test
    @DisplayName("Test creación básica de un reporte")
    void testCreacionReporteBasico() {
        assertNotNull(report);
        assertEquals(testBattery, report.getBattery());
        assertEquals(0, report.getCurrentStep());
        assertEquals("PENDING", report.getEstado());
        assertFalse(report.isCustomerApproved());
        assertEquals(BigDecimal.ZERO, report.getExtraCost());
    }

    /**
     * Comprueba que la actualización del estado, paso actual y
     * aprobación del cliente se realice correctamente.
     */
    @Test
    @DisplayName("Test actualización del estado del reporte")
    void testActualizacionEstado() {
        report.setEstado("IN_PROGRESS");
        report.setPasoActual(2);
        report.setCustomerApproved(true);

        assertEquals("IN_PROGRESS", report.getEstado());
        assertEquals(2, report.getCurrentStep());
        assertTrue(report.isCustomerApproved());
    }

    /**
     * Verifica que el cálculo del coste total sea correcto
     * al añadir costes extra y razones adicionales.
     */
    @Test
    @DisplayName("Test cálculo del coste total")
    void testCalculoCosteTotal() {
        BigDecimal extraCost = new BigDecimal("50.00");
        report.setExtraCost(extraCost);
        report.setExtraReason("Reparación compleja");

        BigDecimal expectedTotal = testBattery.getBaseCost().add(extraCost);
        assertEquals(expectedTotal, report.getTotal());
        assertEquals("Reparación compleja", report.getExtraReason());
    }

    /**
     * Comprueba que se lance una excepción al intentar establecer
     * costes negativos en el reporte.
     */
    @Test
    @DisplayName("Test validación de costes negativos")
    void testValidacionCostesNegativos() {
        assertThrows(IllegalArgumentException.class, () -> {
            report.setExtraCost(new BigDecimal("-10.00"));
        });
    }

    /**
     * Verifica que se lance una excepción al intentar establecer
     * pasos de reparación inválidos (negativos o mayores al máximo permitido).
     */
    @Test
    @DisplayName("Test actualización de pasos inválidos")
    void testValidacionPasosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            report.setPasoActual(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            report.setPasoActual(11); // Asumiendo máximo 10 pasos
        });
    }

    /**
     * Comprueba que la generación del resumen JSON del reporte
     * incluya todos los campos necesarios con los valores correctos.
     */
    @Test
    @DisplayName("Test generación de informe en JSON")
    void testGeneracionJson() {
        report.setPasoActual(2);
        report.setEstado("IN_PROGRESS");
        report.setExtraCost(new BigDecimal("25.00"));
        report.setExtraReason("Componentes adicionales");

        String json = report.generateJsonSummary();

        assertTrue(json.contains("\"bateria_modelo\":\"Test-Model\""));
        assertTrue(json.contains("\"items\""));
        assertTrue(json.contains("\"coste_extra\":25.00"));
        assertTrue(json.contains("\"motivo_extra\":\"Componentes adicionales\""));
    }
}