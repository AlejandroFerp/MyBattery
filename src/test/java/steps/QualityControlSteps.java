package steps;

import com.batteryworkshop.models.Battery;
import com.batteryworkshop.models.RepairReport;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QualityControlSteps{
    private Battery battery;
    private boolean calidadAprobada;
    private BigDecimal baseCost = BigDecimal.valueOf(45);

    @Given("una batería con voltaje {float}, amperaje {float}, carga {int} y descarga {int}")
    public void unaBateriaConParametros(float voltaje, float amperaje, int carga, int descarga) {
        battery = new Battery("TEST-001", "Prueba QC", baseCost);
        battery.setVoltaje(BigDecimal.valueOf(voltaje));
        battery.setAmperaje(BigDecimal.valueOf(amperaje));
        battery.setCarga(carga==1);
        battery.setDescarga(descarga==1);
    }

    @When("realizo el control de calidad con voltaje {float}, amperaje {float}, carga {int} y descarga {int}")
    public void realizoElControlDeCalidad(float voltaje, float amperaje, int carga, int descarga) {
        calidadAprobada = RepairReport.isQualityOk(battery,BigDecimal.valueOf(voltaje),BigDecimal.valueOf(amperaje),carga==1,descarga==1);
    }

    @Then("el resultado de calidad debe ser {word}")
    public void elResultadoDeCalidadDebeSer(String esperado) {
        boolean esperadoBool = Boolean.parseBoolean(esperado);
        assertEquals(esperadoBool, calidadAprobada);
    }


}
