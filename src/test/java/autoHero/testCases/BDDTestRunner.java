package autoHero.testCases;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/autoHero/features/search.feature", 
				glue = { "autoHero.stepDefinitions" },
				plugin = {"html:test-output/cucumber-reports"},
				monochrome=true	
)

public class BDDTestRunner {

}
