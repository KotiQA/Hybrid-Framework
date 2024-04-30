package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelFileUtil 
{
	Workbook wb;	
	//Constructor for reading Excel Path
	public ExcelFileUtil(String Excelpath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
	}
	//Method for Counting no of rows in a sheet
	public int rowCount(String sheetName)
	{
		return wb.getSheet(sheetName).getLastRowNum();

	}
	//Method for reading cell data	
	public String getCellData(String sheetname,int row,int column)
	{
		String data ="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
		}
		else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;	
	}
	//Method for Writing cell data
	public void SetCellData(String sheetName,int row,int column,String status,String writeExcel) throws Throwable
	{
		//get sheet from wb
		Sheet ws = wb.getSheet(sheetName);
		//get row from sheet
		Row rowNum = ws.getRow(row);
		//Create Cell
		Cell cell = rowNum.createCell(column);
		//Write Status
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
						
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.getBold();
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(writeExcel);
		wb.write(fo);
	}
			
}





















