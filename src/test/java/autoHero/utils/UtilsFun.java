package autoHero.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

import autoHero.configs.ExtentManager;

public class UtilsFun {

	

	public static void FailResult(String failMessage) {
		Log.info(failMessage);
		ExtentManager.test.fail(failMessage);
		Reporter.log(failMessage);
		
		
	}

	public static void PassResult(String passMessage) {
		Log.info(passMessage);
		Reporter.log(passMessage);
		ExtentManager.test.pass(passMessage);
	
	}


public static WebDriver openBrowser() {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Paul\\Documents\\Tismy\\MA\\Selenium\\downloads\\chromedriver_win32\\chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	options.addArguments("disable-infobars");
	ChromeDriver driver = new ChromeDriver(options);
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	return driver;
}



public static String stackTrace(Exception exception) {
	StringWriter writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter( writer );
	exception.printStackTrace( printWriter );
	printWriter.flush();

	String stackTrace = writer.toString();
	return stackTrace;
}
}
