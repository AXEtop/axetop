package com.booking.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.booking.tools.Framework;

public class SplendidResortPage 
{
	Framework framework = new Framework();
	public static Boolean wiFiVerification;
	public static Boolean parkingVerification;
	
	public WebDriver driver;

	public SplendidResortPage(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	@FindBy (xpath = ".//span[@class='inner_content_filter_api']//span[contains(text(),'Free WiFi')]") WebElement freeWiFiVerification;
	@FindBy (xpath = ".//span[@class='inner_content_filter_api']//span[contains(text(),'Free parking')]") WebElement freeParkingVerification;
	public SplendidResortPage checkForWiFiAndParking() throws InterruptedException 
	{
		framework.waitForWebElement(freeWiFiVerification);
		framework.waitForWebElement(freeParkingVerification);
		wiFiVerification = freeWiFiVerification.isDisplayed();
		parkingVerification = freeParkingVerification.isDisplayed();
		return this;
	}

}
