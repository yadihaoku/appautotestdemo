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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.read.biff.BiffException;
import logo.module.Config;
import logo.module.ElementExist;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class AutoTestExcelFile
{
	private POIFSFileSystem filesystem;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;
	private HSSFCell cell;
	ElementExist el = new ElementExist();

	/**
	 * 读取excel ，解析为 Map<Integer, List<String>> 的格式。
	 * @param is
	 * @param sheetName  excel sheet名称
	 * @return
	 */
	public HashMap<Integer, List<String>> readExcelToMap(InputStream is, String sheetName) //获取excel内容              
	{
		
		HashMap<Integer, List<String>> content = new HashMap<Integer, List<String> >();
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new POIFSFileSystem(is));
		} catch (IOException e) {
			e.printStackTrace();
			//异常时，返回，后续代码，不再执行。
			return content;
		}
		//获取 sheet 页
		HSSFSheet sheet = workbook.getSheet(sheetName);
		
		//获取 逻辑最后一个行数
		int rowNum = sheet.getLastRowNum();
		
		//如果一行数据，也没有。立即返回 。
		if(rowNum < 1)return content;
		
		//以第一行为准，获取 第一行的最大 列数
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
		
		for(int r = 0; r <= rowNum; r++){
			HSSFRow	row = sheet.getRow(r);
			
			ArrayList<String> list = new ArrayList<>(); 
			content.put(r, list);
			//读取 一行的数据
			for(int colIndex= 0; colIndex < columnCount; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				if(cell == null) {
					list.add("");
				}else{
					list.add( getStringCellValue (row.getCell(colIndex)));
				}
			}
		}
		
		return content;
	}

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
				DecimalFormat df = new DecimalFormat("##");
				strCell = df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				System.out.println(cell.getCellType());
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
	
	public static void main(String[] args) throws Exception{
		
		final String filePath = "android_testcase3_3.xls";
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook  = new HSSFWorkbook(new POIFSFileSystem(fis));
		HSSFSheet sheet = workbook.getSheet(Config.getInstance().getCfg("My"));    
		int rowNum = sheet.getLastRowNum();
		
		System.out.println("rowNum=" + rowNum);
		System.out.println("phyRowNum=" + sheet.getPhysicalNumberOfRows());
		
		System.out.println("rowCotent=" + sheet.getRow(0).getCell(0).getStringCellValue());
		System.out.println("rowCotent=" + sheet.getRow(1).getCell(0).getStringCellValue());
		
		System.out.println("row1 physical columns=" + sheet.getRow(0).getPhysicalNumberOfCells());
		System.out.println("row1 last columns=" + sheet.getRow(0).getLastCellNum());
		
		AutoTestExcelFile atef = new AutoTestExcelFile();
		view( atef.readExcelToMap(new FileInputStream(filePath), "我") );
	}
	/**
	 * 查看读取到的 excel 数据。
	 * @param map
	 */
	static void view(HashMap<Integer, List<String>> map){
		Set<Integer> keyset = map.keySet();
		
		for(Integer key : keyset){
			System.out.println(key + " -> :"  + map.get(key));
		}
		
	}

}
