package autoHero.testCases;

import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.xalan.extensions.ExtensionsTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.PageFactory;

import org.testng.annotations.Test;
import org.testng.internal.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import autoHero.configs.BaseTest;
import autoHero.configs.Constants;
import autoHero.configs.ExtentManager;
import autoHero.pageObjects.SearchPage;
import autoHero.utils.Log;
import autoHero.utils.UtilsFun;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

public class SearchTest  extends BaseTest{

	public  WebDriver driver;

	SearchPage SearchPage;

	public static ExtentReports extent;

	@BeforeSuite
	public void beforesuite() {
		DOMConfigurator.configure("./src/test/java/autoHero/resources/log4j.xml");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		extent = ExtentManager.getInstance();

	}

	@BeforeTest
	public void beforeTest() {
		driver = UtilsFun.openBrowser();
		driver.get(Constants.autoHerourl);
//		SearchPage = PageFactory.initElements(driver, SearchPage.class);
		Log.info("beforeTest method");
		Reporter.log("beforeTest method");
		ExtentManager.testsuite = extent.createTest("Test satrted");
		
		
		System.out.println("driver 1: "+ driver);
	}

	@Test
	public void testApplyFilter() throws InterruptedException {

		try {
			BaseTest.testResult = true;

			ExtentTest child = null;
			child = ExtentManager.testsuite.createNode("testApplyFilter");
			ExtentManager.test = child;

			SearchPage = PageFactory.initElements(driver, SearchPage.class);
			// function to apply filters
			SearchPage.SearchApplyFilter_Action(2015, "Höchster Preis");

			if (BaseTest.testResult == false) {
				Assert.fail("testcase failed");
			}
		} catch (Exception e) {
			UtilsFun.FailResult("Test case failed");
			UtilsFun.FailResult(UtilsFun.stackTrace(e));
		}
		System.out.println("driver 2: "+ driver);
	}

	@Test(dependsOnMethods = { "testApplyFilter" }, enabled = true)
	public void testVerifyRegistrationYear() throws InterruptedException {

		try {
			BaseTest.testResult = true;

			ExtentTest child = null;
			child = ExtentManager.testsuite.createNode("testVerifyRegistrationYear");
			ExtentManager.test = child;

			
			SearchPage = PageFactory.initElements(driver, SearchPage.class);
			SearchPage.VerifyRegistrationYear( 2015);

			if (BaseTest.testResult == false) {
				Assert.fail("testcase failed");
			}
		} catch (Exception e) {
			UtilsFun.FailResult("Test case failed");
			UtilsFun.FailResult(UtilsFun.stackTrace(e));
		}
		System.out.println("driver 3: "+ driver);
	}

	@Test(dependsOnMethods = { "testVerifyRegistrationYear" }, enabled = true)
	public void testVerifyOPriceOrder() throws InterruptedException {

		try {
			BaseTest.testResult = true;

			ExtentTest child = null;
			child = ExtentManager.testsuite.createNode("testVerifyOPriceOrder");
			ExtentManager.test = child;

		
			SearchPage.VerifyOPriceOrder();

			if (BaseTest.testResult == false) {
				Assert.fail("testcase failed");
			}
		} catch (Exception e) {
			UtilsFun.FailResult(UtilsFun.stackTrace(e));
			UtilsFun.FailResult("Test case failed");
		}
		System.out.println("driver 31: " + driver);
	}

	@AfterTest
	public void aftertest() throws InterruptedException {
		System.out.println("driver 5: "+ driver);
		
		//Thread.sleep(500);
		driver.close();
		if (driver != null) {
			driver.quit();
			
		}

		extent.flush();
	}

	@AfterSuite
	public void aftersuite() {
		extent.flush();
	}

}