package com.batteryworkshop.controllers;

import com.batteryworkshop.models.Reporte;
import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.repositories.ReporteRepository;
import com.batteryworkshop.repositories.BatteryRepository;
import com.batteryworkshop.repositories.ItemReparacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.List;

/**
 * Controlador REST que gestiona la creación, finalización y edición de reportes de reparación.
 */
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private BatteryRepository batteryRepo;

    @Autowired
    private ItemReparacionRepository itemRepo;

    /**
     * Crea un nuevo reporte de reparación para una batería existente.
     *
     * @param batteryId ID de la batería a la que se asocia el reporte.
     * @return Reporte creado con estado inicial.
     */
    @PostMapping("/crear/{batteryId}")
    public Reporte crearReporte(@PathVariable Long batteryId) {
        Battery battery = batteryRepo.findById(batteryId)
                .orElseThrow(() -> new NoSuchElementException("Batería no encontrada"));

        Reporte reporte = new Reporte();
        reporte.setBattery(battery);
        reporte.setEstado("en_proceso");
        reporte.setPasoActual(1);
        reporte.setTotal(battery.getBaseCost()); // empieza con el coste base

        return reporteRepo.save(reporte);
    }

    /**
     * Finaliza un reporte sumando los ítems y el coste base de la batería.
     *
     * @param reporteId ID del reporte a finalizar.
     * @return JSON simulado con el resumen del reporte finalizado.
     */
    @PostMapping("/finalizar/{reporteId}")
    public String finalizarReporte(@PathVariable Long reporteId) {
        Reporte reporte = reporteRepo.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException("Reporte no encontrado"));

        float sumaItems = reporte.getItems().stream()
                .map(ItemReparacion::getPrecio)
                .reduce(0f, Float::sum);

        float total = reporte.getBattery().getBaseCost() + sumaItems + reporte.getCosteExtra();
        reporte.setTotal(total);
        reporte.setEstado("finalizado");

        // Simular generación de resumen JSON (podés usar una librería real como Jackson más adelante)
        String resumen = "{ \"cliente\": \"desconocido\", \"modelo\": \"" +
                reporte.getBattery().getModel() + "\", \"total\": " + total + " }";
        reporte.setJsonResumen(resumen);

        reporteRepo.save(reporte);
        return resumen;
    }

    /**
     * Agrega un ítem de reparación a un reporte existente.
     *
     * @param reporteId ID del reporte.
     * @param item Ítem con nombre y precio.
     * @return Ítem guardado.
     */
    @PostMapping("/agregar-item/{reporteId}")
    public ItemReparacion agregarItem(@PathVariable Long reporteId, @RequestBody ItemReparacion item) {
        Reporte reporte = reporteRepo.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException("Reporte no encontrado"));

        item.setReporte(reporte);
        return itemRepo.save(item);
    }
}
