package steps;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.RepairReport;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @brief Step definitions for quality control scenarios in Cucumber.
 * @details Defines actions and validations to perform quality control processes
 * on batteries using Gherkin scenarios.
 */
public class QualityControlSteps {
    /**
     * @brief The battery to be tested in the quality control scenarios.
     */
    private Battery battery;

    /**
     * @brief Indicates whether the battery passed the quality control or not.
     */
    private boolean calidadAprobada;

    /**
     * @brief Base cost of the battery used in the tests.
     * @details Initialized to 45.
     */
    private BigDecimal baseCost = BigDecimal.valueOf(45);

    /**
     * @param voltaje  Voltage of the battery.
     * @param amperaje Ampere rating of the battery.
     * @param carga    Charging state of the battery (1 for true, 0 for false).
     * @param descarga Discharging state of the battery (1 for true, 0 for false).
     * @brief Initializes a battery with specified parameters for testing.
     */
    @Given("una batería con voltaje {float}, amperaje {float}, carga {int} y descarga {int}")
    public void unaBateriaConParametros(float voltaje, float amperaje, int carga, int descarga) {
        battery = new Battery("TEST-001", "Prueba QC", baseCost);
        battery.setVoltaje(BigDecimal.valueOf(voltaje));
        battery.setAmperaje(BigDecimal.valueOf(amperaje));
        battery.setCarga(carga == 1);
        battery.setDescarga(descarga == 1);
    }

    /**
     * @param voltaje  Expected voltage to test against.
     * @param amperaje Expected ampere rating to test against.
     * @param carga    Expected charging state (1 for true, 0 for false).
     * @param descarga Expected discharging state (1 for true, 0 for false).
     * @brief Performs the quality control test with specified parameters.
     */
    @When("realizo el control de calidad con voltaje {float}, amperaje {float}, carga {int} y descarga {int}")
    public void realizoElControlDeCalidad(float voltaje, float amperaje, int carga, int descarga) {
        calidadAprobada = RepairReport.isQualityOk(
                battery,
                BigDecimal.valueOf(voltaje),
                BigDecimal.valueOf(amperaje),
                carga == 1,
                descarga == 1
        );
    }

    /**
     * @param esperado Expected result ("true" for passing, "false" for failing).
     * @brief Verifies the result of the quality control test.
     */
    @Then("el resultado de calidad debe ser {word}")
    public void elResultadoDeCalidadDebeSer(String esperado) {
        boolean esperadoBool = Boolean.parseBoolean(esperado);
        assertEquals(esperadoBool, calidadAprobada);
    }
}
