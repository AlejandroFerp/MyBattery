package com.batteryworkshop.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.batteryworkshop.cucumber.steps",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberIntegrationTest {
}