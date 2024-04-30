package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class FunctionLibrary
{
	public static WebDriver driver;

	//Method for Launching Browser

	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else
		{
			Reporter.log("Browser Value is Not Matching",true);
		}
		return driver;
	}

	//Method for Launch Url

	public static void openUrl() throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}

	//Method for Wait for Element

	public static void waitForElement(String LocatorType,String LocatorValue,String TestData) throws Throwable
	{
		//WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));

		WebDriverWait mywait = new WebDriverWait(driver,Integer.parseInt(TestData));

		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}

		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}

		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}

	}

	//Method for Text Boxes

	public static void typeAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}

	//Method for Buttons,CheckBoxes,Radio Buttons,Links And Images

	public static void clickAction(String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}

	//Method for page Title Validation

	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}

	//Method for close Browser

	public static void closeBrowser()
	{
		driver.quit();
	}

	//Method for Generate Date

	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD  hh_mm_ss");
		return df.format(date);
	}

	//Method for List Boxes

	public static void dropDownAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);			
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);			
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);			
		}		
	}

	//Method For Stock Number Capture In to NotePad

	public static void captureStockNumber(String LocatorType,String LocatorValue) throws Throwable
	{
		String StockNum = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			StockNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			StockNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			StockNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNum);
		bw.flush();
		bw.close();
	}

	//Method for Stock Table
	@SuppressWarnings("resource")
	public static void stockTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try 
			{
				Assert.assertEquals(Exp_Data, Act_Data,"Stock Number is Not Matching");	
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}

		}
	}
	
	//Method for Capture Supplier Number
	
	public static void captureSupplierNum(String LocatorType,String LocatorValue) throws Throwable
	{
		String SupplierNum = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			SupplierNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			SupplierNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			SupplierNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();
	}
	
	// Method For Supplier Table
	@SuppressWarnings("resource")
	public static void supplierTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try 
			{
				Assert.assertEquals(Exp_Data, Act_Data,"Supplier Number is Not Matching");	
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}		
	}
	
	// Method For Capture Customer Number
	
	public static void captureCustomerNum(String LocatorType,String LocatorValue) throws Throwable
	{
		String CustomerNum = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			CustomerNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			CustomerNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			CustomerNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}
	
	// Method For Customer table
	@SuppressWarnings("resource")
	public static void customerTable() throws Throwable
	{

		FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try 
			{
				Assert.assertEquals(Exp_Data, Act_Data,"Customer Number is Not Matching");	
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}		
	
	}
	
	// Method for Capture Purchase Number
	
	public static void capturePurchaseNum(String LocatorType,String LocatorValue) throws Throwable
	{
		String PurchaseNum = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			PurchaseNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			PurchaseNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			PurchaseNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/PurchaseNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(PurchaseNum);
		bw.flush();
		bw.close();
	}
		
	// Method For Purchase Table
	@SuppressWarnings("resource")
	public static void PurchasesTable()throws Throwable
	{


		FileReader fr = new FileReader("./CaptureData/PurchaseNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).isDisplayed())
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_TextBox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_Button"))).click();
			Thread.sleep(2000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']//tbody/tr[1]/td[5]/div/span/span")).getText();
			Reporter.log(Exp_Data+"     "+Act_Data,true);
			try 
			{
				Assert.assertEquals(Exp_Data, Act_Data,"Purchase Number is Not Matching");	
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
		}		
	
	
		
	}

}




















