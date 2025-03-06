package cucumberoptions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "stepDefinition",
        plugin = {"pretty", "json:target/cucumber-reports.json", "html:target/cucumber-html-reports"},
        monochrome = true
)
public class TestRunner {
}