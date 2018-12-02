package com.hxj.lear.utils;

import java.util.HashMap;
import java.util.List;
/**
 * 获取 excel 中 每列的数字，与 Action 对应的索引
 * @author YanYadi
 *
 */
public class ActionWithFileMapping {
	
	public static final String HEAD_LOCATION = "定位方式";
	public static final String HEAD_SELECTOR = "控件元素";
	public static final String HEAD_ACTION = "操作方法";
	public static final String HEAD_DATA ="测试数据";
	public static final String HEAD_VALIDATE_DATA= "验证数据";
	public static final String HEAD_WAIT_TIME = "延迟时间";
	public static final String HEAD_TEST_CASE_NUMBER = "测试用例编号";
	public static final String HEAD_RESULT = "测试结果";
	

	
	HashMap<String, Integer> mapping;
	private ActionWithFileMapping(HashMap<String, Integer> mapping){
		this.mapping = mapping;
	}
	
	
	public int locationIndex(){
		if(mapping.containsKey(HEAD_LOCATION))
			return mapping.get(HEAD_LOCATION);
		else
			return -1;
	}
	
	public int actionIndex(){
		if(mapping.containsKey(HEAD_ACTION))
			return mapping.get(HEAD_ACTION);
		else
			return -1;
	}
	public int dataIndex(){
		if(mapping.containsKey(HEAD_DATA))
			return mapping.get(HEAD_DATA);
		else
			return -1;
	}
	public int sleepIndex(){
		if(mapping.containsKey(HEAD_WAIT_TIME))
			return mapping.get(HEAD_WAIT_TIME);
		else
			return -1;
	}
	public int selectorIndex(){
		if(mapping.containsKey(HEAD_SELECTOR))
			return mapping.get(HEAD_SELECTOR);
		else
			return -1;
	}
	public int testCaseNoIndex(){
		if(mapping.containsKey(HEAD_TEST_CASE_NUMBER))
			return mapping.get(HEAD_TEST_CASE_NUMBER);
		else
			return -1;
	}
	public int testResultIndex(){
		if(mapping.containsKey(HEAD_RESULT))
			return mapping.get(HEAD_RESULT);
		else
			return -1;
	}
	/**
	 * 解析第 1 行数据，获取各列的数据索引
	 * @param row
	 * @return
	 */
	public static ActionWithFileMapping parseHeadRow(List<String> row){
		HashMap<String, Integer> mapping = new HashMap<>();
		
		for(int i = 0 ; i < row.size(); i++){
			String head = row.get(i).trim();
			mapping.put(head, i);
		}
		System.out.println(mapping);
		return new ActionWithFileMapping(mapping);
	}
}
