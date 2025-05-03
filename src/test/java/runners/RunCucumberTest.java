package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Test runner for Cucumber tests.
 * <p>
 * This test runner is configured to use the JUnit Platform and the Cucumber engine.
 * It looks for feature files located in the "features" directory on the classpath
 * and defines the glue code location and reporting format.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps") // Specifies the package containing step definitions.
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json")
// Configures the output format for the report.
public class RunCucumberTest {
}