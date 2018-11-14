package autoHero.pageObjects;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import autoHero.configs.BaseTest;
import autoHero.utils.Log;
import autoHero.utils.UtilsFun;


/***
 * POM for Search page, This class holds the functionality and members of that class
 * @author Paul
 *
 */
public class SearchPage extends BaseTest {

	@FindBy(how = How.CSS, using = "div[data-qa-selector=\"filter-year\"]")
	public WebElement label_RegistrationDateFrom;

	@FindBy(how = How.CSS, using = "div[data-qa-selector=\"filter-year\"] select")
	public WebElement select_minyearRang;

	@FindBy(how = How.NAME, using = "sort")
	public WebElement select_sort;

	@FindBy(how = How.CSS, using = "div[data-qa-selector=\"results-amount\"]")
	public WebElement label_totalCarsFound;

	@FindBy(how = How.CSS, using = "a[data-qa-selector=\"ad\"]")
	public List<WebElement> totalCarsonCurrentPage;

	public SearchPage(WebDriver driver)
	{
		BaseTest.driver = driver;
	}

/***
 * This function set Registration year as 'setMinimumRegYear'
 * @param setMinimumRegYear
 * @throws InterruptedException
 */
	
	public void ApplyFilterMinimumRegYear_Action(int setMinimumRegYear) throws InterruptedException {
		try {

			label_RegistrationDateFrom.click();
			Select_Registration_Year(setMinimumRegYear);
		} catch (Exception e) {
			BaseTest.testResult = false;
			UtilsFun.FailResult("ApplyFilterMinimumRegYear_Action: failed");

		}
	}

	
	/***
	 * This function Set Sorting value as 'setSortBY'
	 * @param setSortBY
	 * @throws InterruptedException
	 */
	public void ApplyFilterPriceSort_Action(String setSortBY) throws InterruptedException {
		try {
			SetSortByFilter(setSortBY);
		} catch (Exception e) {
			BaseTest.testResult = false;
			UtilsFun.FailResult("ApplyFilterPriceSort_Action: failed");
		}
	}

/****
 * This function apply filter for Minimum registration year and sort by filter
 * @param setMinimumRegYear
 * @param setSortBY
 * @throws InterruptedException
 */
	
public void SearchApplyFilter_Action(int setMinimumRegYear, String setSortBY) throws InterruptedException {
		try {

			ApplyFilterMinimumRegYear_Action(setMinimumRegYear);
			ApplyFilterPriceSort_Action(setSortBY);
			Thread.sleep(100000);
		} catch (Exception e) {
			BaseTest.testResult = false;
			UtilsFun.FailResult("SearchApplyFilter_Action:failed");

		}
	}


/****
 * 
 * @param setMinimumRegYear
 */

public void Select_Registration_Year(int setMinimumRegYear) {
		String sMinimumRegYear = Integer.toString(setMinimumRegYear);
		Select selectRegistrationYear = new Select(select_minyearRang);
		selectRegistrationYear.selectByVisibleText(sMinimumRegYear);
		Log.info(" Select_Registration_Year: Filter applied " + select_minyearRang);
	}


/****
 * 
 * @param setSortBY
 * @throws InterruptedException
 */
	public void SetSortByFilter(String setSortBY) throws InterruptedException {
		Select selectSortByFilter = new Select(select_sort);
		selectSortByFilter.selectByVisibleText(setSortBY);
		Log.info("SetSortByFilter: Filter applied " + setSortBY);
	}


	
	/****
	 * Function returns total cars found after filtering
	 * @return total cars
	 */
	public int totalCarsFound() {
		String sTotalCars = label_totalCarsFound.getText();
		System.out.println("sTotalCars:" + sTotalCars);
		sTotalCars = sTotalCars.replaceAll("[^\\d.]", "").trim();
		System.out.println("sTotalCars:" + sTotalCars);
		int Totalcars = Integer.parseInt(sTotalCars);
		Log.info("totalCarsFound " + Totalcars);
		return Totalcars;
	}

	
	/*****
	 * Function to verify whether the cards are filtered by registraion year selected
	 * @param setMinimumRegYear
	 * @throws InterruptedException
	 */
	
	public void VerifyRegistrationYear(int setMinimumRegYear) throws InterruptedException {
		List<WebElement> list_Pagination = null;
		// int totalCars = totalCarsFound();
		// Log.info("Totalcars:" + totalCars);
		BaseTest.testResult = true;
		boolean stepResult = true;

		list_Pagination = driver.findElements(By.cssSelector("ul[class=\"pagination\"] li a"));
		int Pagination = list_Pagination.size();
		int totalPages = Integer.parseInt(list_Pagination.get(Pagination - 3).getText());
		Log.info("number of pages:" + totalPages);
		for (WebElement page : list_Pagination) {
			if (page.getText() == "1") {
				page.click();
			}

		}

		Thread.sleep(10000);

		driver.navigate().refresh();
//		list_Pagination.get(2).click();

		for (int currentPage = 0; currentPage < totalPages; currentPage++) {
			List<WebElement> List_YearRegistrations = null;
			Log.info("currentPage:" + (currentPage + 1));
			int carsOncurrentPage = totalCarsonCurrentPage.size();
			Log.info("Totalcars on curent page:" + carsOncurrentPage);
			List_YearRegistrations = driver.findElements(By.cssSelector("ul[class=\"specList___2i0rY\"] li:nth-of-type(1)"));

			for (int carIndex = 0; carIndex < carsOncurrentPage; carIndex++) {
				WebElement indCarYearReg = List_YearRegistrations.get(carIndex);
				UtilsFun.waitforStaleElement(indCarYearReg);
				String sYear = indCarYearReg.getText().trim();
				String[] sYearArray = sYear.split("/");
				String sYear1 = sYearArray[1];
				int registrationYear = Integer.parseInt(sYear1);
				Log.info("year:" + registrationYear + ", car:" + carIndex);

				if (registrationYear >= setMinimumRegYear) {

					if (carIndex != 1) {
						UtilsFun.PassResult("Page Number :" + (currentPage + 1) + " , Car Index : " + (carIndex + 1) + ", Registration Year " + registrationYear + " is greater than : " + setMinimumRegYear);
					}

				} else {
					stepResult = false;
					UtilsFun.FailResult("Verification failed : Page :" + (currentPage + 1) + ", Car Index : " + (carIndex + 1) + " , Registarion Year " + registrationYear + " is less than  : " + setMinimumRegYear);

				}

			}

			if (currentPage != totalPages - 1) {
				//click on next page button/arrow
				list_Pagination.get(Pagination - 2).click();
				Thread.sleep(2000);
			}

		}

		if (stepResult == false) {
			BaseTest.testResult = false;
		}
	}

/***
 * function to check whether the cards are sorted bt price descending
 * @throws InterruptedException
 */
	
	public void VerifyOPriceOrder() throws InterruptedException {
		List<WebElement> list_Pagination = null;

		int totalCars = totalCarsFound();
		Log.info("Totalcars:" + totalCars);
		boolean testResult = true;
		boolean stepResult = true;

		list_Pagination = driver.findElements(By.cssSelector("ul[class=\"pagination\"] li a"));
		int Pagination = list_Pagination.size();
		int totalPages = Integer.parseInt(list_Pagination.get(Pagination - 3).getText());

		Thread.sleep(1000);
		Log.info("number of pages:" + totalPages);

		List<WebElement> List_Price1 = driver.findElements(By.cssSelector("div[data-qa-selector=\"price\"]"));
		String shighestPrice = List_Price1.get(0).getText().trim();
		shighestPrice = shighestPrice.replaceAll("[^\\d.]", "").trim();
		Float highestPrice = Float.parseFloat(shighestPrice);

		for (WebElement page : list_Pagination) {

			if (page.getText() == "1") {
				page.click();
			}

		}

//list_Pagination.get(2).click();	

		Log.info("highestPrice:" + highestPrice);
		for (int currentPage = 0; currentPage < totalPages; currentPage++) {
			List<WebElement> List_Price = null;

			List_Price = driver.findElements(By.cssSelector("div[data-qa-selector=\"price\"]"));
			Log.info("currentPage:" + (currentPage + 1));

			int carsOncurrentPage = totalCarsonCurrentPage.size();
			Log.info("Totalcars on curent page:" + carsOncurrentPage);

			for (int carIndex = 0; carIndex < carsOncurrentPage; carIndex++) {
				// driver.navigate().refresh();

				WebElement indCarPrice = List_Price.get(carIndex);
				// UtilsFun.waitforStaleElement(indCarPrice);

				String sPrice = indCarPrice.getText().trim();
				sPrice = sPrice.replaceAll("[^\\d.]", "").trim();
				Float carPrice = Float.parseFloat(sPrice);
				// Log.info("Price:" + carPrice + ", car:" + carIndex);

				if (highestPrice >= carPrice) {

					if (carIndex != 0 | carIndex != carsOncurrentPage) {
						UtilsFun.PassResult("Page Number :" + (currentPage + 1) + " , Car  " + (carIndex + 1) + "Price - " + highestPrice + " is greater than : " + " car " + (carIndex + 2) + "price " + carPrice);
					}
					highestPrice = carPrice;

				} else {
					stepResult = false;
					UtilsFun.PassResult("Page Number :" + (currentPage + 1) + " , Car  " + (carIndex + 1) + "Price - " + highestPrice + " is less than : " + " car " + (carIndex + 2) + "price " + carPrice);
				}
			}

			if (currentPage != totalPages - 1) {
				//click on next page button/arrow
				list_Pagination.get(Pagination - 2).click();
				Thread.sleep(2000);
			}

		}

		if (stepResult == false) {
			testResult = false;
		}
	}

}
