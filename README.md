# Auto1-QA-Challenge
Task Description
Create automation test for search functionality
Description: Search is a crucial part of all Auto1 platforms and Automation testing is essential
part of software development in Auto Group. Please create automation test which will do the
following. Upload solution to github.com: (You are free to use any programming language or any
framework for this task)
  * Open https://www.autohero.com/de/search/
  * Filter for First registration (Erstzulassung). Filter for FROM 2015
  * Apply Filter
  * Sort cars by Price Descending (Höchster Preis)
  * Verify all cars are filtered by first registration (2015+)
  * Verify all cars are sorted by price descending
 
## Concepts Included
* Selenium - Java based automation tests
* Cucumber tests for behavior driven development 
* Page Object Model design pattern with Page Factory 
* Modular Design
* Centralized Configuration (Using Properties file)
* Dependency management using Maven
* Testcase organization using TestNG 
* Capture Screenshots for failures
* Report Generation using Extent Reports
* Log4j enabled for logging
* Hooks for different browser support (using tag @chrome,@firefox...)
 
## Tools/version used
* JDK 1.8 
* Maven 
* Eclipse IDE - Photon
* Eclipse plugins for maven and cucumber
* Selenium WebDriver
* Cucumber
* Chrome and chrome driver
* TestNG
* JUnit
 
 
## Framework Architecture
In this project, I have used Modular driven framework by using Page Object Model design pattern with Page Factory.
 
**POM**: As per the Page Object Model, I have maintained a class for the web page. Each web page has a separate class and that class holds the functionality and members of that web page. Separate classes for every individual test.
 
**Packages**: There are separate packages for Pages and Tests. All the web page related classes come under Pages package and all the tests related classes come under Tests package.
 
    * Config
    * Pages
    • Util
    • testcases
    • Screenshots
    • Reports
    • Resources
    • TestData
 
 
**Config Package** : This package contains all the common functions used by all the pages. This class is responsible for loading the configurations from properties files, Initializing the WebDriver, Implicit Waits, Extent Reports. 
 
**Properties file**: Properties file stores the information that remains static throughout the framework such as browser specific information, application URL, screenshots path etc. All the details which change as per the environment and authorization such as URL, Login Credentials are kept in the config.properties file. Keeping these details in a separate file makes easy to maintain.
 
**Screenshots**:  Screenshots will be captured and stored in a separate folder and also the screenshots of a failed test cases will be added in the extent reports.
 
**TestNG**: Using TestNG for Assertions, Grouping and Parallel execution.
 
**Maven**: Using Maven for build, execution and dependency purpose. Integrating the TestNG dependency in POM.xml file and running this POM.xml file using Jenkins. Below maven dependencies are added in the project.
    * Maven dependency for JUNIT
    * Maven dependency for Selenium-Java (Selenium WebDriver)
    * Maven dependency for Cucumber-core
    * Maven dependency for Cucumber-java
    * Maven dependency for Cucumber-junit
    • etc
 
 
**Extent Reports**: For the reporting purpose, I have used Extent Reports. It generates beautiful HTML reports. These  extent reports for maintaining logs and also to include the screenshots of failed test cases in the Extent Report. 
Reports for each module are written into their respective `/target` directories after a successful run.
 
**Logging**: It is done using log4j.
 
**ReportNG** : Interactive and readable reports for selenium web-driver tests are generated using ReportNG
 
 
##  Running the tests - command line mode
 
Go to your project directory from terminal and hit following commands
 
mvn test 

 
Running the tests - from IDE
 
 
*NOTE*:
Below are not implemented in this framework:
Test Data:  All the test data can be kept in an excel sheet and this can be passed to the scripts and handle data driven testing.  Apache POI can be used to handle excel sheets.
Version Control Tool: TFS/Bit Bucket as a repository to store the test scripts. 
Jenkins: By using Jenkins CI (Continuous Integration) Tool, These test cases can be executed on daily basis and also for nightly execution based on the schedule. Test Result will be sent to the peers using Jenkins.
