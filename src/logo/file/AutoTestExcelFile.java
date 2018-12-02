/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import logo.module.Config;
import logo.module.ElementExist;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class AutoTestExcelFile
{
	private POIFSFileSystem filesystem;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;
	private HSSFCell cell;
	ElementExist el = new ElementExist();

	public String[] readExcelTitleContent(InputStream is)//获取excel中标题内容                 
	{
		try
		{
			filesystem = new POIFSFileSystem(is); //???                            
			workbook = new HSSFWorkbook(filesystem);//???
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = workbook.getSheet(Config.getInstance().getCfg("My"));          
		row = sheet.getRow(0);  //获得第一行数据                                               
		int colNum = row.getPhysicalNumberOfCells(); //第一行数据的个数存储在colNum变量中                        
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++)
		{
			title[i] = getStringCellValue(row.getCell((short) i));             
		}
		return title;                          
	}

	public Map<Integer, String> readExcelContent(InputStream is) //获取excel内容              
	{
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		try
		{
			filesystem = new POIFSFileSystem(is);
			workbook = new HSSFWorkbook(filesystem);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = workbook.getSheet(Config.getInstance().getCfg("My"));           
		int rowNum = sheet.getLastRowNum();                                     
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		for (int i = 1; i <= rowNum; i++)                                       
		{
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum)
			{                                                                  
				str += getStringCellValue(row.getCell((short) j)).trim() + "-"; 
				j++;
			}
			content.put(i, str);
			str = "";
		}
		return content;
	}

	private String getStringCellValue(HSSFCell cell)                           
	{
		String strCell = "";
		if (cell == null)
		{
			return "";
		} else
			switch (cell.getCellType())
			{
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				String strCellNumber = String.valueOf(cell.getNumericCellValue());
				DecimalFormat df = new DecimalFormat("0");
				strCell = df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
			}
		if (strCell.equals("") || strCell == null)
		{
			return "";
		}

		return strCell;
	}

	private String getDateCellValue(HSSFCell cell)                               
	{
		String result = "";
		try
		{
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				result = "";
			}
		} catch (Exception e)
		{
			System.out.println("日期格式有误!");
			e.printStackTrace();
		}
		return result;
	}

	public List<String> ReadTitle() throws FileNotFoundException              
	{
		List<String> list = new ArrayList<String>();//比list还好用的类型
		InputStream is = new FileInputStream(Config.getInstance().getCfg("filePath"));//
		//文件输入流
		AutoTestExcelFile excelReader = new AutoTestExcelFile();
		String[] title = excelReader.readExcelTitleContent(is);
		for (String s : title)
		{
			list.add(s);
		}
		return list;
	}

	public List<String> ReadContent() throws FileNotFoundException                
	{
		List<String> list = new ArrayList<String>();
		AutoTestExcelFile excelReader = new AutoTestExcelFile();
		InputStream is2 = new FileInputStream(Config.getInstance().getCfg("filePath"));
		//filePath为android_config.properties或ios_config.properties中定义的filePath，指向的是与其对应的excel文件
		Map<Integer, String> map = excelReader.readExcelContent(is2);
		for (int i = 1; i <= map.size(); i++)
		{
			list.add(map.get(i));
		}
		return list;
	}

	public String ReadTitleContent(int i, int j) throws BiffException, IOException
	{
		InputStream is = new FileInputStream(Config.getInstance().getCfg("filePath"));
		try
		{
			filesystem = new POIFSFileSystem(is);
			workbook = new HSSFWorkbook(filesystem);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = workbook.getSheet(Config.getInstance().getCfg("My"));
		row = sheet.getRow(i);
		String content = getStringCellValue(row.getCell((short) j));
		return content;
	}

	private void saveWorkBook(HSSFWorkbook wb)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(Config.getInstance().getCfg("filePath"));
			wb.write(fileOut);
		} catch (FileNotFoundException ex)
		{
			System.out.println(ex.getMessage());
		} catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	private HSSFCell getCell(HSSFSheet sheet, int rowIndex, short columnIndex)
	{
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null)
		{
			row = sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(columnIndex);
		if (cell == null)
		{
			cell = row.createCell((short) columnIndex);
		}
		return cell;
	}

	public void WriteTitleContent(int i, short j) throws BiffException, IOException   
	{
		try
		{
			InputStream is = new FileInputStream(Config.getInstance().getCfg("filePath"));
			filesystem = new POIFSFileSystem(is);
			workbook = new HSSFWorkbook(filesystem);
			is.close();
		} catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}

		sheet = workbook.getSheet(Config.getInstance().getCfg("My"));
		row = sheet.getRow(i);
		HSSFCell cell = getCell(sheet, i, j);
		HSSFRichTextString hts = new HSSFRichTextString(el.result);
		cell.setCellValue(hts);
		saveWorkBook(workbook);
	}

	public void SetContentInit(short j) throws BiffException, IOException   
	{
		try
		{
			InputStream is = new FileInputStream(Config.getInstance().getCfg("filePath"));
			Map<Integer, String> map = readExcelContent(is);

			for (int k = 1; k <= map.size(); k++)
			{
				sheet = workbook.getSheet(Config.getInstance().getCfg("My"));
				row = sheet.getRow(k);
				HSSFCell cell = getCell(sheet, k, j);
				HSSFRichTextString hts = new HSSFRichTextString("");
				cell.setCellValue(hts);
			}
			saveWorkBook(workbook);

		} catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

}
