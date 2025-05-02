package com.batteryworkshop.controllers;

import com.batteryworkshop.models.RepairReport;
import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.repositories.ReporteRepository;
import com.batteryworkshop.repositories.BatteryRepository;
import com.batteryworkshop.repositories.ItemReparacionRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

/**
 * Controller class for managing repair reports
 */
@RestController
@RequestMapping("/reportes")
public class ReporteController {
    /**
     * Status constant for reports in progress
     */
    private static final String ESTADO_EN_PROCESO = "en_proceso";
    /**
     * Status constant for finished reports
     */
    private static final String ESTADO_FINALIZADO = "finalizado";
    /**
     * Initial step constant
     */
    private static final int PASO_INICIAL = 1;

    /**
     * Repository for repair reports
     */
    private final ReporteRepository reporteRepository;
    /**
     * Repository for batteries
     */
    private final BatteryRepository bateriaRepository;
    /**
     * Repository for repair items
     */
    private final ItemReparacionRepository itemRepository;

    /**
     * Constructor for ReporteController
     *
     * @param reporteRepository Repository for repair reports
     * @param bateriaRepository Repository for batteries
     * @param itemRepository    Repository for repair items
     */
    public ReporteController(ReporteRepository reporteRepository,
                             BatteryRepository bateriaRepository,
                             ItemReparacionRepository itemRepository) {
        this.reporteRepository = reporteRepository;
        this.bateriaRepository = bateriaRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Creates a new repair report for a battery
     *
     * @param bateriaId ID of the battery
     * @return The created repair report
     * @throws NoSuchElementException if battery is not found
     */
    @PostMapping("/crear/{bateriaId}")
    public RepairReport crearReporte(@PathVariable Long bateriaId) {
        Battery bateria = bateriaRepository.findById(bateriaId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No se encontró la batería con ID: %d", bateriaId)));

        RepairReport reporte = new RepairReport(bateria);
        reporte.setEstado(ESTADO_EN_PROCESO);
        reporte.setPasoActual(PASO_INICIAL);
        return reporteRepository.save(reporte);
    }

    /**
     * Finalizes a repair report
     *
     * @param reporteId ID of the report to finalize
     * @return JSON summary of the finalized report
     * @throws NoSuchElementException if report is not found
     */
    @PostMapping("/finalizar/{reporteId}")
    public String finalizarReporte(@PathVariable Long reporteId) {
        RepairReport reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No se encontró el reporte con ID: %d", reporteId)));

        actualizarEstadoFinal(reporte);
        reporteRepository.save(reporte);
        return reporte.generateJsonSummary();
    }

    /**
     * Adds a repair item to a report
     *
     * @param reporteId ID of the report
     * @param item      Repair item to add
     * @return The saved repair item
     * @throws NoSuchElementException if report is not found
     */
    @PostMapping("/agregar-item/{reporteId}")
    public ItemReparacion agregarItem(@PathVariable Long reporteId,
                                      @RequestBody ItemReparacion item) {
        RepairReport reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No se encontró el reporte con ID: %d", reporteId)));

        reporte.addItem(item);
        return itemRepository.save(item);
    }

    /**
     * Updates the final state of a repair report
     * Calculates total cost and sets status to finished
     *
     * @param reporte Report to update
     */
    private void actualizarEstadoFinal(RepairReport reporte) {
        BigDecimal totalItems = reporte.getItems().stream()
                .map(ItemReparacion::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = reporte.getBattery().getBaseCost()
                .add(totalItems)
                .add(reporte.getExtraCost());

        reporte.setTotal(total);
        reporte.setEstado(ESTADO_FINALIZADO);
    }
    public RepairReport getReporte(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Informe no encontrado con ID: " + id));
    }

}