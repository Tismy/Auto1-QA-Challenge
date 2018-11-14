package autoHero.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import autoHero.configs.BaseTest;
import autoHero.configs.Constants;
import autoHero.configs.ExtentManager;

public class UtilsFun extends BaseTest {

	
	/*** 
	 * Reporting function when a test fails
	 * @param failMessage
	 */
	public static void FailResult(String failMessage) {
		Log.info(failMessage);
		ExtentManager.test.fail(failMessage);
		Reporter.log(failMessage);
		CaptureScreenShotWithTestStepName(driver, "Failed Screenhot");
	}


	/*** 
	 * Reporting function when a test pass
	 * @param passMessage
	 */
	
	public static void PassResult(String passMessage) {
		Log.info(passMessage);
		Reporter.log(passMessage);
		ExtentManager.test.pass(passMessage);

	}

	
	/***
	 * hooks to use any browser
	 * @return
	 */
	
	public static WebDriver getDriver() {
		
		String driver = System.getProperty("selenium.driver");
		if(driver.equals("ie")) {
			return getIEDriver();
			
		}
		else if(driver.equals("chrome")) {
			return getChromeDriver();
		}
			else if(driver.equals("firefox")) {
				return getFirefoxriver();
			}
			else {
				throw new RuntimeException("System property selenium driver is not set");
		}
	}
	
	private static WebDriver getFirefoxriver() {
		// TODO Auto-generated method stub
		return null;
	}
 
	private static WebDriver getIEDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	public static WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", Constants.chromeDriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

	
	/***
	 * function to print stack trace
	 * @param exception
	 * @return
	 */
	public static String stackTrace(Exception exception) {
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		exception.printStackTrace(printWriter);
		printWriter.flush();

		String stackTrace = writer.toString();
		return stackTrace;
	}

	
	/***
	 * function in case if any element could not be retrieved
	 * @param by
	 * @return
	 */
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	
	/***
	 * function to handle stale elements
	 * @param element
	 */
	public static void waitforStaleElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));

	}


	/***
	 * function to capture screen shots
	 * @param driver
	 * @param testStepsName
	 * @return
	 */

	public static String CaptureScreenShotWithTestStepName(WebDriver driver, String testStepsName)
	{
		try{
			// down casting WebDriver to use getScreenshotAs method.
			TakesScreenshot ts= (TakesScreenshot)driver;
			// capturing screen shot as output type file
			File screenshotSRC= ts.getScreenshotAs(OutputType.FILE);
			// Defining path and extension of image
			String path=System.getProperty(Constants.screenShots+testStepsName+System.currentTimeMillis()+".png");
			// copying file from temp folder to desired location
			File screenshotDest= new File(path);
			FileUtils.copyFile(screenshotSRC, screenshotDest);
			return path;
		}catch(Exception e)
		{
			System.out.println("Some exception occured." + e.getMessage());
			return "";
		}
	}
	


}
