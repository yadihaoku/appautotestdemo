package com.hxj.lear.actions;

import com.hxj.lear.utils.FinderFactory;

import io.appium.java_client.AppiumDriver;

public class ActionFactory {

	public static final String ACTION_CLICK = "click";
	public static final String ACTION_SENDKEYS = "sendkeys";
	public static final String ACTION_DOWNPAGE = "DownPage";
	public static final String ACTION_SLEEP = "sleep";
	
	/**
	 * sendkeys \ click \ DownPage 
	 * @param action
	 * @param driver
	 * @param locationMode
	 * @param selector
	 * @param data
	 * @param sleep
	 * @return
	 */
	public static Action build(String action, AppiumDriver driver, String locationMode, String selector,String data, String sleep){
		
		AbstractAction actionBean = null;
		if(ACTION_CLICK.equalsIgnoreCase(action)){
			actionBean = new OnClickAction(driver);
		}else if(ACTION_DOWNPAGE.equalsIgnoreCase(action)){
			actionBean = new DownPageAction(driver);
		}else if(ACTION_SENDKEYS.equalsIgnoreCase(action)){
			actionBean = new KeyAction(driver, data);
		}else{
			// nothing
			return null;
		}
		//设置 Finder
		actionBean.setFinder(FinderFactory.make(locationMode, selector));
		//如果有 sleep ，则 sleep 设置为一个 Action
		if(sleep != null){
			Action[] nextActions = new Action[]{
					new SleepAction(Long.parseLong(sleep))
			};
			actionBean.setNextActions(nextActions);
		}
		return actionBean;
	}
}
