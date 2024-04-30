package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.ExcelFileUtil;
import commonFunctions.FunctionLibrary;

public class DriverScript 
{
	public static WebDriver driver;
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TestCases = "MasterTestCases";

	public void startTest() throws Throwable
	{
		String Module_Status = "";
		//Create Object Class for ExcelFileUtil Class

		ExcelFileUtil xl = new ExcelFileUtil(inputpath);

		//iterate all rows in MasterTestCases

		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			//Which Ever Test cases Flag to Y Execute them

			if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
			{
				//Store Each test Cases in to TCModule

				String TCModule = xl.getCellData(TestCases, i, 1);
				report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger = report.startTest(TCModule);

				//iterate All rows in each sheet TCModule

				for(int j = 1;j<=xl.rowCount(TCModule);j++)
				{
					//Call Cells from TCModule

					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);

					try {
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureStockNumber"))
						{
							FunctionLibrary.captureStockNumber(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureSupplierNum"))
						{
							FunctionLibrary.captureSupplierNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equals("captureCustomerNum"))
						{
							FunctionLibrary.captureCustomerNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capturePurchaseNum"))
						{
							FunctionLibrary.capturePurchaseNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						
						
						if(Object_Type.equalsIgnoreCase("PurchasesTable"))
						{
							FunctionLibrary.PurchasesTable();
							logger.log(LogStatus.INFO, Description);
						}

						//Write as PASS in to Status Cell in TCModule Sheet

						xl.SetCellData(TCModule, j, 5, "PASS", outputpath);
						logger.log(LogStatus.INFO, Description);
						Module_Status = "True";

					} catch (Exception e) 
					{
						System.out.println(e.getMessage());

						//Write as Fail in to Status Cell in TCModule Sheet

						xl.SetCellData(TCModule, j, 5, "FAIL", outputpath);
						logger.log(LogStatus.INFO, Description);
						Module_Status = "False";
					}
					if(Module_Status.equalsIgnoreCase("True"))
					{
						xl.SetCellData(TestCases, i, 3, "PASS", outputpath);
					}
					else
					{
						xl.SetCellData(TestCases, i, 3, "FAIL", outputpath);
					}
					report.endTest(logger);
					report.flush();
				}				
			}
			else
			{
				//Which Ever test Cases Flag to N Write as BLocked in to the Status Cell in MasterTestCases

				xl.SetCellData(TestCases, i, 3, "BLOCKED", outputpath);
			}
		}
	}
}




























