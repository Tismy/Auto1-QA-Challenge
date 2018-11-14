package autoHero.configs;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.
 
public class ExtentManager {
 
    private static ExtentReports extent;
    
    public static ExtentTest testsuite;
    public static ExtentTest test;
 
    public synchronized static ExtentReports getInstance(){
       
    	File extentfile = new File(Constants.extentReport);
    	if(extent == null){
            //Set HTML reporting file location
            //String workingDir = System.getProperty("user.dir");
            extent = CreateInstance(extentfile.getAbsolutePath());
        }
        return extent;
    }

	private static ExtentReports CreateInstance(String fileName) {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		// TODO Auto-generated method stub'
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		return extent;
	}
}