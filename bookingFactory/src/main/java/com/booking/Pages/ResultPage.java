package com.booking.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.booking.tools.Framework;

public class ResultPage 
{
	Framework framework = new Framework();
	public static String hotel = "Splendid Conference & Spa Resort";
	public static List<String> listOfPopularHotels = new ArrayList<String>();
	
	public WebDriver driver;
	
	public ResultPage(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy (xpath = ".//*[contains(text(),'Popular')]//..//a[contains(@href,'/hotel/me/')]") List<WebElement> popularHotelsWebList;
	public ResultPage saveAllPopularHotelsToList() 
	{
		List<WebElement> b = popularHotelsWebList;
		listOfPopularHotels.clear();
		for(WebElement x : b)
		  {
		    listOfPopularHotels.add(x.getText());
		  }
		return this;
	}
	
	@FindBy (xpath = ".//a[contains(text(),'Splendid Conference & Spa Resort')]") WebElement splendidResortLink;
	public SplendidResortPage clickHotelInPopularHotels(String hotel2) throws InterruptedException 
	{
		framework.waitForWebElement(splendidResortLink);
		splendidResortLink.click();
		for (String winHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(winHandle);
		}		
		return PageFactory.initElements(driver, SplendidResortPage.class);
	}

}
