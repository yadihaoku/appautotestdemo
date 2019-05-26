package com.hxj.lear.actions;

import com.hxj.lear.utils.Finder;

public interface Action {
	
	String RESULT_FAILURE = "failure";
	String RESULT_PASS = "pass";
	/**
	 * 执行动作, 并返回结果
	 */
	String execute();
	/**
	 * 子动作
	 * @param action
	 */
	void addSubAction(Action action);
	
	void setFinder(Finder finder);
	 
}
