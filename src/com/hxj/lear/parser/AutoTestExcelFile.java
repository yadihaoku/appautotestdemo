
package com.hxj.lear.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.hxj.lear.config.Config;

/**
 * 读取excel ，解析为 Map<Integer, List<String>> 的格式。
 * @author YanYadi
 *
 */
public class AutoTestExcelFile {
	/**
	 *readExcelToMap(InputStream is, String sheetName) 与
	 *readExcelToMap(String filePath, String sheetName)这就是多态、是方法的重载。
	 *封装、继承、多态
	 *https://www.cnblogs.com/chenssy/p/3372798.html
	 */
	public HashMap<Integer, List<String>> readExcelToMap(String filePath, String sheetName) // 获取excel内容
	{
		File file = new File(filePath);
		FileInputStream fis = null;
		if (!file.exists())
			throw new RuntimeException(filePath + " 不存在!");
		try {
			// 返回readExcelToMap()类型
			// 下面的方法，返回解析后的 excel 内容，不会为null。 可能的值要么是空的map {} 或者读取到值的 map {"xxxx"} 
			return readExcelToMap(new FileInputStream(file) , sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(fis != null)
				IOUtils.closeQuietly(fis);
		}
		// 当excel文件读取失败时，返回 null
		return null;
	}

	//重载方法
	public HashMap<Integer, List<String>> readExcelToMap(InputStream is, String sheetName) // 获取excel内容
	{

		HashMap<Integer, List<String>> content = new HashMap<Integer, List<String>>();
		
		// 获取 sheet 页
		HSSFSheet sheet = getSheet(is, sheetName);
		// 获取 逻辑最后一个行数
		int rowNum = sheet.getLastRowNum();
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();// 获取最大 列数（选择第一行来获取）
		if (rowNum > 0) {
			for (int r = 0; r <= rowNum; r++) {// 获取excel表- sheetName页的所有行		
				ArrayList<String> list = new ArrayList<>();
				for (int colIndex = 0; colIndex < columnCount; colIndex++) {//所有列
					HSSFRow row = sheet.getRow(r);// 读取 一行的数据
					HSSFCell cell = row.getCell(colIndex);// 读取一行数据中每一个单元内容		
					if (cell != null) {
						list.add(getStringCellValue(cell));
					} else {
						list.add("");//Q getStringCellValue(cell)中cell已经被验证为空的情况，cell不判断为空能去掉吗？
					}
				}	
				content.put(r, list);
			}
			return content;		
		}
		else {
			throw new RuntimeException("  ");
			}
	}
	
	public HSSFSheet getSheet(InputStream is, String sheetName) {
		HSSFWorkbook workbook = null;
		try {
			// apache poi excel 解析 库，对 文件流的一种封装。
			workbook = new HSSFWorkbook(new POIFSFileSystem(is));
			// 获取 sheet 页
			HSSFSheet sheet = workbook.getSheet(sheetName);
			return sheet;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public HSSFWorkbook getWorkBook(String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			throw new RuntimeException(filePath + " 不存在!");

		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		} else
			switch (cell.getCellType()) {
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
		if (strCell.equals("") || strCell == null) {
			return "";
		}

		return strCell;
	}
	public void saveWorkBook(HSSFWorkbook wb) {
		try {
			FileOutputStream fileOut = new FileOutputStream(Config.getInstance().getCfg("filePath"));
			wb.write(fileOut);
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
