package com.booking.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.booking.tools.Framework;

public class DestinationFinderPage 
{
	Framework framework = new Framework();
	public static Boolean searchResult;
	
	public WebDriver driver;
	
	public DestinationFinderPage(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy (id = "dsf_search_input") WebElement destinationPageSearchField;
	
	public DestinationFinderPage searchRegionOnDestinationPage(String searchData) throws InterruptedException 
	{
		destinationPageSearchField.sendKeys(searchData);
		Thread.sleep(2000);
		return this;
	}
	
	public DestinationFinderPage selectRegionDataFromDropDown(String data) throws InterruptedException 
	{
		String searchPath=".//*[@id='ac_destinations']/div/div[@data-type='region'][contains(.,'"+data+"')]//span";
		driver.findElement(By.xpath(searchPath)).click();
		return this;
	}

	@FindBy (id = "dsf_button") WebElement destinationPageSearchButton;
	
	public DestinationFinderPage clickSearchButtonOnDestinationPage() throws InterruptedException 
	{
		destinationPageSearchButton.click();
		return this;
	}

	@FindBy (xpath = ".//*[@id='dsf_citycontainer']/div[3]/div/div") WebElement destinationPageSearchResult;
	
	public DestinationFinderPage checkIfRegionSearchResultIsPresent() throws InterruptedException 
	{
		framework.waitForWebElement(destinationPageSearchResult);
		searchResult = destinationPageSearchResult.isDisplayed();
		return this;
	}

}
