package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/java/feature", glue = "steps" , monochrome = true, dryRun = false, tags = "@AddProductAndCheckout", plugin = {"pretty", "html:src/test/resources/reports.html"})
public class TestNGRunner extends AbstractTestNGCucumberTests {
}
