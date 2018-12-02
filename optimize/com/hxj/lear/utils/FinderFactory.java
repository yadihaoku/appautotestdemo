package com.hxj.lear.utils;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

/**
 * 查找器工厂
 * @author YanYadi
 *
 */
public class FinderFactory {
	
	static HashMap<String ,Class< ? extends Finder>> sFinders;
	
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
	static{
		sFinders = new HashMap<>();
		sFinders.put("By.id", ByNameFinder.class);
		sFinders.put("By.name",ByIdFinder.class);
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
			return driver.findElement(By.name(getSelector()));
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
			return driver.findElement(By.id(getSelector()));
		}
	}
 

}
