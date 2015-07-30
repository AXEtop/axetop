package com.booking.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.booking.Pages.DestinationFinderPage;
import com.booking.Pages.HomePage;
import com.booking.Pages.ResultPage;
import com.booking.Pages.SplendidResortPage;


public class BookingFactory extends com.booking.tools.Framework
{
	public WebDriver driver;
	
	@BeforeMethod
	public void before()
	{
		driver=startDriver();
		driver.get("http://www.booking.com");		
	}
	
	@Test //1
	public void test1() throws Exception,IOException
	{
		PageFactory.initElements(driver, HomePage.class)
		.login()
		.saveFirstAndLastNameInfoInTopRightCorner();

		Assert.assertTrue(HomePage.UserFirstname.contains("Yura") && HomePage.UserLastname.contains("AXE"),"FirsName or LastName is incorrect");

	}
	
	@Test //2
	public void test2() throws Exception,IOException
	{
		PageFactory.initElements(driver, HomePage.class)
		.searchOnMainPageWithText(HomePage.searchText)
		.checkInDate(HomePage.inDate)
		.checkOutDate(HomePage.outDate)
		.clickSearchButtonOnHomePage()
		.saveAllPopularHotelsToList();
		
		Assert.assertTrue(ResultPage.listOfPopularHotels.contains(ResultPage.hotel),"No such hotel in list of popular hotels");
	}
	
	@Test //3
	public void test3() throws Exception,IOException
	{
		PageFactory.initElements(driver, HomePage.class)
		.searchOnMainPageWithText(HomePage.searchText)
		.checkInDate(HomePage.inDate)
		.checkOutDate(HomePage.outDate)
		.clickSearchButtonOnHomePage()
		.clickHotelInPopularHotels(ResultPage.hotel)
		.checkForWiFiAndParking();
		
		Assert.assertTrue(SplendidResortPage.wiFiVerification && SplendidResortPage.parkingVerification, "No wifi or parking");	
	}
	
	@Test //4
	public void test4() throws Exception,IOException
	{
		PageFactory.initElements(driver, HomePage.class)
		.login()
		.clickOnDiscoverNewDestinations()
		.searchRegionOnDestinationPage("Lviv")
		.selectRegionDataFromDropDown("Lviv Region, Ukraine")
		.clickSearchButtonOnDestinationPage()
		.checkIfRegionSearchResultIsPresent();
		
		Assert.assertTrue(DestinationFinderPage.searchResult);
	}
	
	@Test //5
	public void test5() throws Exception,IOException
	{
		PageFactory.initElements(driver, HomePage.class)
		.saveAllVisibleTownNamesOnDestinations()
		.saveExcelFileToWithListData(System.getProperty("user.dir")+"/tools/Test.xlsx",HomePage.listOfTownNames)
		.checkNumberOfEntriesInExcel(System.getProperty("user.dir")+"/tools/Test.xlsx");

		Assert.assertEquals(HomePage.numberOfRows, HomePage.listOfTownNames.size());
	}
	
	@AfterMethod
	public void after() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.quit();	
	}
}
