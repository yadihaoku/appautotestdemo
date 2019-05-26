package com.hxj.lear.utils;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hxj.lear.log.AutoLogger;

import io.appium.java_client.AppiumDriver;

/**
 * 查找器工厂
 * @author YanYadi
 *
 */
public class FinderFactory {
	
	/**
	 *  查找元素时的 超时时间
	 */
	public static final int DEFAULT_FIND_TIMEOUT = 5;
	
	static HashMap<String ,Class< ? extends Finder>> sFinders;
	//静态代码块
	static{
		sFinders = new HashMap<>();
		sFinders.put("id", ByIdFinder.class);
		sFinders.put("name",ByNameFinder.class);
		sFinders.put("xpath", ByXpathFinder.class);
	}
	
	static AutoLogger log = AutoLogger.getLogger(FinderFactory.class);
	
	
	/**
	 * 依据 名称，返回查找器对象
	 * @param locationMode
	 * @return
	 */
	public final static Finder make(String locationMode, String selector){
		
		if(locationMode == null)return null;
		if(sFinders.containsKey(locationMode)){
			try {
				BaseFinder finder = (BaseFinder) sFinders.get(locationMode).newInstance();
				finder.setSelector(selector);
				return finder;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			throw new UnsupportedOperationException("不支持的查询方式-> " + locationMode);
		}
		
		
	}
 
	static abstract class BaseFinder implements Finder{
		String selector;
		BaseFinder(String selector){
			this.selector = selector;
		}
		BaseFinder(){
		}
		void setSelector(String selector){
			this.selector = selector;
		}
		protected String getSelector(){
			return this.selector;
		}
		
	}
	
	/**
	 * 通过 byName 查询
	 * @author YanYadi
	 *
	 */
	static class ByNameFinder extends BaseFinder{
		@Override
		public WebElement getByDriver(AppiumDriver driver) {
			log.log("byName" + getSelector());
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_FIND_TIMEOUT);
			return wait.until(ExpectedConditions.presenceOfElementLocated( By.name(getSelector()) ));
		}
		
	}
	/**
	 * 通过 byId 查询
	 * @author YanYadi
	 *
	 */
	static class ByIdFinder extends BaseFinder{
		@Override
		public WebElement getByDriver(AppiumDriver driver) {
			log.log("byId" + getSelector());
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_FIND_TIMEOUT);
			return	wait.until(ExpectedConditions.presenceOfElementLocated( By.id(getSelector()) ));
		}
	}
	/**
	 * 通过 xpath 查询
	 * @author YanYadi
	 *
	 */
	static class ByXpathFinder extends BaseFinder{
		@Override
		public WebElement getByDriver(AppiumDriver driver) {
			log.log("byXpath" + getSelector());
			
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_FIND_TIMEOUT);
		
			WebElement  ele = wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath(getSelector()) ));
			log.log("byXpath" + ele.getTagName());
			return ele;
		}
	}
 

}
