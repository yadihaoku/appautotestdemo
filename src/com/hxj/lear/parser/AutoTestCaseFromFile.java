package com.hxj.lear.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hxj.lear.actions.Action;
import com.hxj.lear.actions.ActionFactory;
import com.hxj.lear.actions.ActionWrapper;
import com.hxj.lear.config.Config;
import com.hxj.lear.log.AutoLogger;

import io.appium.java_client.AppiumDriver;

/**
 * 
 * 优化后的自动化测试，测试场景 从 文件中读取
 * 
 * @author YanYadi
 * 
 */
public class AutoTestCaseFromFile {
	AutoTestExcelFile excelReader = new AutoTestExcelFile();
	// AutoTestCaseFromFile.class打日志
	AutoLogger log = AutoLogger.getLogger(AutoTestCaseFromFile.class);

	/**
	 * 按模块ID 执行测试用例
	 * 
	 * @param driver   驱动
	 * @param moduleId 模块 id 用例编号
	 */
	public void testByModuleId(AppiumDriver driver, String moduleId) {
		final String excelFilePath = Config.getInstance().getCfg("filePath");
		final String sheetName = Config.getInstance().getCfg("My");

		log.log("filePath = " + excelFilePath + "  sheetName = " + sheetName);// 日志记录
		HashMap<Integer, List<String>> map = excelReader.readExcelToMap(excelFilePath, sheetName);
		log.log("total data ==>" + (map == null ? 0 : map.size()));// 日志记录
		if (map != null && map.size() > 0) {
			//获取类型为List的标题行，
			final int titleRowIndex = 0;
			ActionWithFileMapping mapping = new ActionWithFileMapping(map.get(titleRowIndex));
			// 筛选编号为 moduleId 的行,并生成 Action
			List<ActionWrapper> actions = convertMap2ListActions(map, moduleId, mapping, driver);
			log.log("total action ==> " + actions.size());
			// 执行动作，并保存执行结果
			// excel的实体
			HSSFWorkbook workBook = excelReader.getWorkBook(excelFilePath);
			try {
				runActions(actions);
			} catch (Exception e) {
				throw e;
			} finally {
				if (workBook != null) {
					fillResult(actions, workBook, sheetName);
				}
			}
		}
	}
	/**
	 * 执行测试动作，并写入结果
	 * 
	 * @param actions
	 * @param sheet
	 */
	
	void fillResult(List<ActionWrapper> actions, HSSFWorkbook workBook, String sheetName) {
		HSSFSheet sheet = workBook.getSheet(sheetName);
		for (ActionWrapper act : actions) {
			act.writeToSheet(sheet);
		}
		excelReader.saveWorkBook(workBook);
	}

	/**
	 * 按指定的 moudleId 进行筛选，并转换 Map 为 List
	 * 
	 * @param map
	 * @param moduleId
	 * @param mapping
	 */
	List<ActionWrapper> convertMap2ListActions(HashMap<Integer, List<String>> map, String moduleId,
			ActionWithFileMapping mapping, AppiumDriver driver) {

		List<ActionWrapper> actions = new ArrayList<>();

		final int endRows = map.size();
		final int testCaseNoIndex = mapping.testCaseNoIndex();
		if (testCaseNoIndex == -1) {
			throw new RuntimeException("未找到 [测试用例编号] 列的索引！  ");
		}
		// 测试结果 列的索引
		final int resultIndex = mapping.testResultIndex();

		for (int i = 0; i < endRows; i++) {
			List<String> row = map.get(i);
			if (moduleId.equals(row.get(testCaseNoIndex).trim())) {
				log.log("parse2Action" + row.toString());
				Action action = parse2Action(row, mapping, driver);
				ActionWrapper wraper = new ActionWrapper(action, i, resultIndex);
				actions.add(wraper);
			}
		}
		return actions;
	}
	
	void runActions(List<ActionWrapper> actions) {
		for (ActionWrapper act : actions) {
			act.execute();
		}
	}

	/**
	 * 把一行中数据，转化为一个 Action
	 * 
	 * @param row
	 * @param mapping
	 * @return
	 */
	Action parse2Action(List<String> row, ActionWithFileMapping mapping, AppiumDriver driver) {

		final String actionName = row.get(mapping.actionIndex());
		final String locationMode = row.get(mapping.locationIndex());
		final String data = row.get(mapping.dataIndex());
		final String sleep = row.get(mapping.sleepIndex());
		final String selector = row.get(mapping.selectorIndex());
		String capture = row.get(mapping.captureIndex());
		final boolean isCaptureScreen = capture == null ? true : !capture.trim().equals("0");

		log.log("locationMode = " + locationMode + " selector = " + selector);
		return ActionFactory.build(actionName, driver, locationMode, selector, data, sleep, isCaptureScreen);
	}
}
