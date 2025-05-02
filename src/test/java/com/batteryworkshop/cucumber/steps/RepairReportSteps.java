package com.batteryworkshop.cucumber.steps;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.models.RepairReport;
import com.batteryworkshop.controllers.ReporteController;
import com.batteryworkshop.repositories.BatteryRepository;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RepairReportSteps {

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private ReporteController reporteController;

    private Battery battery;
    private RepairReport report;

    @Given("el cliente tiene una batería con número de serie {string}")
    public void elClienteTieneUnaBatería(String serialNumber) {
        battery = new Battery("Basic Model", "Test battery " + serialNumber, new BigDecimal("100.00"));
        battery = batteryRepository.save(battery);
        assertNotNull(battery.getId());
    }

    @When("creo un nuevo informe de reparación con descripción {string}")
    public void creoUnNuevoInformeDeReparación(String description) {
        report = reporteController.crearReporte(battery.getId());
        assertNotNull(report.getId());
    }

    @Then("el informe se crea correctamente")
    public void elInformeSeCreCorrectamente() {
        assertNotNull(report);
        assertNotNull(report.getId());
    }

    @And("el estado del informe es {string}")
    public void elEstadoDelInformeEs(String status) {
        assertEquals(status, report.getEstado());
    }

    @Given("existe un informe de reparación para la batería {string}")
    public void existeUnInformeDeReparación(String serialNumber) {
        elClienteTieneUnaBatería(serialNumber);
        creoUnNuevoInformeDeReparación("Informe de prueba");
    }

    @When("agrego un item {string} con precio {double}")
    public void agregoUnItem(String nombre, Double precio) {
        ItemReparacion item = new ItemReparacion(nombre, BigDecimal.valueOf(precio));
        reporteController.agregarItem(report.getId(), item);
    }

    @Then("el informe tiene {int} items")
    public void elInformeTieneItems(int cantidad) {
        report = reporteController.getReporte(report.getId());
        assertEquals(cantidad, report.getItems().size());
    }

    @And("el precio total del informe es {double}")
    public void elPrecioTotalDelInformeEs(double total) {
        reporteController.finalizarReporte(report.getId());
        report = reporteController.getReporte(report.getId());
        assertEquals(0, BigDecimal.valueOf(total).compareTo(report.getTotal()));
    }
}