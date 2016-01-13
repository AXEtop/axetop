package com.booking.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.booking.Pages.HomePage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;


public class Framework 
{
	 public static String date= getDate();
	 public static String userfilepath=System.getProperty("user.dir")+"/Tools/Users.txt";
	 
	 protected WebDriver driver;
	 protected AppiumDriver<WebElement> x;
	 public WebDriver startDriver() 
	 {
		 
		 AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		 service.start();
		  driver = new FirefoxDriver();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
		  driver.get("http://www.booking.com");
		  return driver;
	 }

	 private static String getDate() 
	 {
		   DateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		   Date today = Calendar.getInstance().getTime();
		   String reportDate = df.format(today);
		   
		   System.out.println(reportDate);
		  
		  return reportDate;
	 }

	 public void clean()
	 {
		 driver.manage().deleteAllCookies();	  
	 }

	 public static String readFileAsString(String filePath) throws IOException 
	 {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1)
        {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	 
	 public static String email() throws IOException
	 {
			String txtfile = readFileAsString(userfilepath);
			String[] splittxt = txtfile.split(",");
			String email = splittxt[1];
			return email;
	 }
	 public static String pass() throws IOException
	 {
			String txtfile = readFileAsString(userfilepath);
			String[] splittxt = txtfile.split(",");
			String pass = splittxt[2];
			return pass;
	 }
	 
	 public static void waitForWebElement(WebElement element) throws InterruptedException
	 {
		 for (int i=0;i<10;i++)
			{
				if (!element.isDisplayed())
				{			
				Thread.sleep(2000);
				}
				else
					break;
			}
	 }
	 
	 public static void saveExcelFileToWithListData(String path, List<String> list) 
	 {
		 	@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("SubjectData");
			int rownum = 0;
			for (String x : list)
			{
				Row row = sheet.createRow(rownum++);
				int cellIndex = 0;
				row.createCell(cellIndex++).setCellValue(x);
			}
			try
			{
			FileOutputStream fos=new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}			
	 }
	 
	 public static int checkExcelEntriesNumber(String path) throws IOException
	 {
		 FileInputStream fis= new FileInputStream(new File(path));
		 @SuppressWarnings("resource")
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
		 XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		 return mySheet.getPhysicalNumberOfRows();
	 }
	 
	public static void filldate()
	 {
		 Calendar cal1 = Calendar.getInstance();
		 cal1.set(Calendar.DAY_OF_MONTH, 22);
		 cal1.set(Calendar.MONTH, Calendar.MARCH);
		 cal1.set(Calendar.YEAR, 2016);
		 Calendar cal2 = Calendar.getInstance();
		 cal2.set(Calendar.DAY_OF_MONTH, 30);
		 cal2.set(Calendar.MONTH, Calendar.MARCH);
		 cal2.set(Calendar.YEAR, 2016);
		 HomePage.inDate = cal1.getTime();
		 HomePage.outDate = cal2.getTime();
		 
	 }
}