package com.hxj.lear.actions;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

/**
 * 
 * @author YanYadi
 *
 */
public class ActionWrapper{
	
	private Action action;
	private int rowIndex;
	private int resultCellIndex;
	private String result;
	private boolean isExecuted;
	/**
	 * 鏋勯�犲嚱鏁�
	 * @param action
	 * @param rIndex
	 * @param resultCellIndex
	 */
	public ActionWrapper(Action action, int rIndex,int resultCellIndex){
		this.action = action;
		this.rowIndex = rIndex;
		this.resultCellIndex = resultCellIndex;
	}

	public String execute() {
		isExecuted = true;
		this.result = action.execute();
		return result;
	}
	
	public void writeToSheet(HSSFSheet sheet) {
		
		final HSSFRichTextString hssfResult;
		if(isExecuted) {
			hssfResult = new HSSFRichTextString(result);
		}else {
			hssfResult = new HSSFRichTextString("no execute");
		}
		//鍐欏叆 excel
		sheet.getRow(rowIndex).getCell(resultCellIndex, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(hssfResult);//鍐欏叆鍝竴鍒楋紝鎬庝箞纭畾鐨勶紵
	}
}
