package com.hxj.lear.actions;


import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * 
 * @author YanYadi
 *
 */
public class ActionWrapper{
	
	private Action action;
	private int rowIndex;
	private int resultCellIndex;
	public ActionWrapper(Action action, int rIndex,int resultCellIndex){
		this.action = action;
		this.rowIndex = rIndex;
		this.resultCellIndex = resultCellIndex;
	}

	public String executeAndWriteResult(HSSFSheet sheet) {
		String result = action.execute();
		//写入 excel
		HSSFRichTextString hts = new HSSFRichTextString(result);
		sheet.getRow(rowIndex).getCell(resultCellIndex).setCellValue(hts);
		
		
		return result;
	}

}
