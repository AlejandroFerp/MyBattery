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

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private static final String ESTADO_EN_PROCESO = "en_proceso";
    private static final String ESTADO_FINALIZADO = "finalizado";
    private static final int PASO_INICIAL = 1;

    private final ReporteRepository reporteRepository;
    private final BatteryRepository bateriaRepository;
    private final ItemReparacionRepository itemRepository;

    public ReporteController(ReporteRepository reporteRepository,
                             BatteryRepository bateriaRepository,
                             ItemReparacionRepository itemRepository) {
        this.reporteRepository = reporteRepository;
        this.bateriaRepository = bateriaRepository;
        this.itemRepository = itemRepository;
    }

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

    @PostMapping("/finalizar/{reporteId}")
    public String finalizarReporte(@PathVariable Long reporteId) {
        RepairReport reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No se encontró el reporte con ID: %d", reporteId)));

        actualizarEstadoFinal(reporte);
        reporteRepository.save(reporte);
        return reporte.generateJsonSummary();
    }

    @PostMapping("/agregar-item/{reporteId}")
    public ItemReparacion agregarItem(@PathVariable Long reporteId,
                                      @RequestBody ItemReparacion item) {
        RepairReport reporte = reporteRepository.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No se encontró el reporte con ID: %d", reporteId)));

        reporte.addItem(item);
        return itemRepository.save(item);
    }

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
}