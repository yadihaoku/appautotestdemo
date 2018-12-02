package com.hxj.lear;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.hxj.lear.actions.Action;
import com.hxj.lear.actions.ActionFactory;
import com.hxj.lear.actions.ActionWrapper;
import com.hxj.lear.utils.ActionWithFileMapping;

import io.appium.java_client.AppiumDriver;
import jxl.read.biff.BiffException;
import logo.file.AutoTestExcelFile;
import logo.log.AutoLogger;
import logo.module.Config;
/**
 * 
 * 优化后的自动化测试，测试场景 从 文件中读取
 * 
 * @author YanYadi
 *  
 */
public class AutoTestCaseFromFile {
	
	
	AutoTestExcelFile excelReader = new AutoTestExcelFile();
	AutoLogger log = AutoLogger.getLogger(AutoTestCaseFromFile.class);//AutoTestCaseFromFile.class打日志
	
	/**
	 * 按模块ID 执行测试用例
	 * @param driver  驱动
	 * @param moduleId 模块 id
	 * @throws InterruptedException
	 * @throws BiffException
	 * @throws IOException
	 */
	public void testByModuleId(AppiumDriver driver, String moduleId) throws InterruptedException, BiffException, IOException
	{ 

		final String excelFilePath = Config.getInstance().getCfg("filePath");
		final String sheetName =  Config.getInstance().getCfg("My");
		
		log.log("filePath = " + excelFilePath + "  sheetName = " + sheetName);
		
		HashMap<Integer, List<String>> map = excelReader.readExcelToMap(excelFilePath, sheetName);
		if(map != null && map.size() > 0){
			
			//默认第 0 行，为标题行
			final int titleRowIndex = 0;
			ActionWithFileMapping mapping = ActionWithFileMapping.parseHeadRow(map.get(titleRowIndex));
			//筛选编号为 moduleId 的行,并生成 Action
			List<ActionWrapper> actions = convertMap2ListActions(map, moduleId, mapping, driver);
			//执行动作
			runAllActions(actions, excelReader.getSheet(excelFilePath, sheetName));
		}
	}
	/**
	 * 按指定的 moudleId 进行筛选，并转换 Map 为 List
	 * @param map
	 * @param moduleId
	 * @param mapping
	 * @return
	 */
	List<ActionWrapper> convertMap2ListActions(HashMap<Integer, List<String>> map, String moduleId, ActionWithFileMapping mapping, AppiumDriver driver){
		
		List<ActionWrapper> actions = new ArrayList<>();
		
		final int endRows = map.size();
		
		final int testCaseNoIndex = mapping.testCaseNoIndex();
		if(testCaseNoIndex == -1){
			throw new RuntimeException("未找到 [测试用例编号] 列的索引！  ");
		}
		for(int i=0; i < endRows; i++){
			List<String> row = map.get(i);
			if(moduleId.equals(row.get(testCaseNoIndex).trim())){
				actions.add(new ActionWrapper(parse2Action(row, mapping, driver) , i, mapping.testResultIndex()));
			}
		}
		return actions;
	}
	
	/**
	 * 执行测试动作，并写入结果
	 * @param actions
	 * @param sheet
	 */
	void runAllActions(List<ActionWrapper> actions, HSSFSheet sheet){
		
		for(ActionWrapper act : actions){
			act.executeAndWriteResult(sheet);
		}
		
	}
	
	/**
	 * 把一行中数据，转化为一个 Action
	 * @param row
	 * @param mapping
	 * @return
	 */
	Action parse2Action(List<String> row, ActionWithFileMapping mapping, AppiumDriver driver){
		
		final String actionName = row.get(mapping.actionIndex());
		final String locationMode = row.get(mapping.locationIndex());
		final String data = row.get(mapping.dataIndex());
		final String sleep = row.get(mapping.sleepIndex());
		final String selector =row.get(mapping.selectorIndex());
		
		return ActionFactory.build(actionName, driver, locationMode,selector, data, sleep );
		
	}
}
