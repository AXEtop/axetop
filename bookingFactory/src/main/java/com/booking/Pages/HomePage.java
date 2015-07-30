package com.booking.Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.booking.tools.Framework;

public class HomePage 
{
	public static Date inDate = new Date();
	public static Date outDate = new Date();
	public static String searchText = "Montenegro";
	public static String UserFirstname;
	public static String UserLastname;
	public static int numberOfRows;
	public static List<String> listOfTownNames = new ArrayList<String>();
	
	public WebDriver driver;

	public HomePage(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy (id = "current_account") WebElement LoginButton;
	
	@FindBy (xpath = 
	"//div[@class='modal-wrapper user-access-menu-lightbox user-access-menu-lightbox--user-center']//div[3]/form/label[1]/input") 
	WebElement EmailField;
	
	@FindBy (xpath = 
	"//div[@class='modal-wrapper user-access-menu-lightbox user-access-menu-lightbox--user-center']//div[3]/form/label[2]//input") 
	WebElement PassField;
	
	@FindBy (xpath = 
	"//div[@class='modal-wrapper user-access-menu-lightbox user-access-menu-lightbox--user-center']//div[3]/form/input[@type='submit']") 
	WebElement ConfirmButton;
	
	public HomePage login() throws InterruptedException, IOException 
	{
		LoginButton.click();
		Thread.sleep(2000);
		EmailField.sendKeys(Framework.email());
		PassField.sendKeys(Framework.pass());
		ConfirmButton.click();
		Thread.sleep(5000);
		return this;
	}

	@FindBy (xpath = ".//li[@id='current_account']/a/span[2]") WebElement userFirstnameVerificationField;
	@FindBy (xpath = ".//li[@id='current_account']/a/span[3]") WebElement userLastnameVerificationField;
	
	public HomePage saveFirstAndLastNameInfoInTopRightCorner() throws InterruptedException 
	{
		Framework.waitForWebElement(userFirstnameVerificationField);
		UserFirstname = userFirstnameVerificationField.getText();
		UserLastname = userLastnameVerificationField.getText();
		return this;
	}
	
	@FindBy (xpath = ".//*[@id='destination']") WebElement searchField;
	
	public HomePage searchOnMainPageWithText(String searchtText) throws InterruptedException, AWTException
	{
		searchField.clear();
		searchField.sendKeys(searchtText);
		Thread.sleep(1000);
		Robot act = new Robot();
		act.keyPress(KeyEvent.VK_DOWN);
		act.keyRelease(KeyEvent.VK_DOWN);
		Thread.sleep(1000);
		act.keyPress(KeyEvent.VK_ENTER);
		act.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		Framework.filldate();
		return this;
	}

	@FindBy (name = "checkin_monthday") WebElement checkInMonthDay;
	@FindBy (name = "checkin_year_month") WebElement checkInYearMonth;
	
	public HomePage checkInDate(Date date) throws InterruptedException
	{	
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		Select InMonthDay = new Select(checkInMonthDay);
		InMonthDay.selectByValue(String.valueOf(day));
		Select InYearMonth = new Select(checkInYearMonth);
		String a = String.valueOf(year)+"-"+(String.valueOf(month+1));
		InYearMonth.selectByValue(a);
		return this;
	}
	
	@FindBy (name = "checkout_monthday") WebElement checkOutMonthDay;
	@FindBy (name = "checkout_year_month") WebElement checkOutYearMonth;
	
	public HomePage checkOutDate(Date date) throws InterruptedException 
	{	
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		Select OutMonthDay = new Select(checkOutMonthDay);
		OutMonthDay.selectByValue(String.valueOf(day));
		Select OutYearMonth = new Select(checkOutYearMonth);
		String a = String.valueOf(year)+"-"+(String.valueOf(month+1));
		OutYearMonth.selectByValue(a);
		return this;
	}

	@FindBy (xpath = ".//button/span[contains(text(),'Search')]") WebElement searchButton;
	
	public ResultPage clickSearchButtonOnHomePage() throws InterruptedException
	{
		searchButton.click();
		Thread.sleep(2000);
		return PageFactory.initElements(driver, ResultPage.class);
	}

	@FindBy (xpath = ".//*[@id='header_dsf_link']/a") WebElement getDestinationTipsFromWorldTravelersButton;
	@FindBy (xpath = 
			".//*[@id='header_dsf_link']//*[contains(text(),'Discover new destinations')]") WebElement discoverNewDestinationsButton;
	
	public DestinationFinderPage clickOnDiscoverNewDestinations() throws InterruptedException 
	{
		Framework.waitForWebElement(getDestinationTipsFromWorldTravelersButton);
		getDestinationTipsFromWorldTravelersButton.click();
		discoverNewDestinationsButton.click();
		return PageFactory.initElements(driver, DestinationFinderPage.class);
	}

	@FindBy (xpath = ".//div[@id='top-destinations-block']//h3/a") List<WebElement> townNamesInDestinations;
	
	public HomePage saveAllVisibleTownNamesOnDestinations() 
	{
		List<WebElement> b = townNamesInDestinations;
		listOfTownNames.clear();
		for(WebElement x : b)
		  {
		    listOfTownNames.add(x.getText());
		  }
		return this;
	}

	public HomePage saveExcelFileToWithListData(String path, List<String> list) 
	{
		Framework.saveExcelFileToWithListData(path, list);	
		return this;
	}

	public HomePage checkNumberOfEntriesInExcel(String path) throws IOException 
	{
		numberOfRows = Framework.checkExcelEntriesNumber(path);
		return this;
	}
}
