package steps;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.ItemReparacion;
import com.batteryworkshop.models.RepairReport;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class containing step definitions for Cucumber tests
 * related to battery repair reports.
 */
public class StepDefinitions {

    /**
     * Model of the battery
     */
    private String modelo;

    /**
     * Base cost of the battery
     */
    private BigDecimal baseCost = BigDecimal.valueOf(45);

    /**
     * Repair report object
     */
    private RepairReport informe;

    /**
     * Registers a client with a battery model and a fixed base cost.
     *
     * @param modelo Battery model provided by the client.
     */
    @Given("el cliente tiene una batería con modelo {string} y coste 45")
    public void el_cliente_tiene_una_batería_con_número_de_serie(String modelo) {
        // Simula la creación de una batería a partir del número de serie proporcionado
        this.modelo = modelo;
        System.out.println("Cliente registrado con modelo: " + modelo);
        System.out.println("Cliente registrado con coste: " + baseCost);
    }

    /**
     * Creates a new repair report with a description.
     *
     * @param descripcion Description of the repair report.
     */
    @When("creo un nuevo informe de reparación con descripción {string}")
    public void creo_un_nuevo_informe_de_reparación_con_descripción(String descripcion) {
        // Crear un nuevo informe de reparación
        this.informe = new RepairReport(new Battery(modelo, descripcion, baseCost));
        this.informe.setStatus("Pendiente");  // Estado inicial del informe
        System.out.println("Informe de reparación creado con descripción: " + descripcion);
    }

    /**
     * Verifies that the repair report is created correctly.
     */
    @Then("el informe se crea correctamente")
    public void el_informe_se_crea_correctamente() {
        // Validar que el informe fue creado correctamente
        assertNotNull(informe, "El informe no debe ser null");
        assertEquals(modelo, informe.getBattery().getModel(), "El modelo de la batería en el informe no coincide");
        System.out.println("Se verificó que el informe fue creado correctamente.");
    }

    /**
     * Validates the status of the repair report.
     *
     * @param estadoEsperado Expected status of the repair report.
     */
    @Then("el estado del informe es {string}")
    public void el_estado_del_informe_es(String estadoEsperado) {
        // Validar el estado del informe
        assertEquals(estadoEsperado, informe.getStatus(), "El estado del informe no coincide con el esperado");
        System.out.println("El estado del informe fue verificado: " + estadoEsperado);
    }

    /**
     * Creates a repair report for a specific battery model.
     *
     * @param modelo Model of the battery for which the repair report is created.
     */
    @Given("existe un informe de reparación para la batería {string}")
    public void existe_un_informe_de_reparación_para_la_batería(String modelo) {
        // Crear un informe de reparación para un modelo específico
        this.informe = new RepairReport(new Battery(modelo, "Descripción por defecto", baseCost));
        System.out.println("Se creó un informe para la batería: " + modelo);
    }

    /**
     * Adds an item with a description and price to the repair report.
     *
     * @param descripcion Description of the item.
     * @param precio      Price of the item in string format.
     */
    @When("agrego un item {string} con precio {string}")
    public void agrego_un_item_con_precio(String descripcion, String precio) {
        // Agregar un item al informe de reparación
        ItemReparacion item = new ItemReparacion(descripcion, new BigDecimal(precio));
        this.informe.addItem(item);
        System.out.println("Se agregó el item: " + descripcion + " con precio: " + precio);
    }

    /**
     * Validates the number of items in the repair report.
     *
     * @param cantidadEsperada Expected number of items in the report.
     */
    @Then("el informe tiene {int} items")
    public void el_informe_tiene_items(int cantidadEsperada) {
        // Validar la cantidad de items del informe
        assertEquals(cantidadEsperada, this.informe.getItems().size(), "La cantidad de items no coincide.");
        System.out.println("El informe tiene la cantidad de items esperada: " + cantidadEsperada);
    }

    /**
     * Validates the total price of the repair report.
     *
     * @param precioEsperado Expected total price of the repair report.
     */
    @Then("el precio total del informe es {string}")
    public void el_precio_total_del_informe_es(String precioEsperado) {
        // Validar el precio total del informe
        assertEquals(new BigDecimal(precioEsperado), this.informe.TotalCalculator(), "El precio total del informe no coincide.");
        System.out.println("El precio total del informe es correcto: " + precioEsperado);
    }
}