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
	 * @param finderName
	 * @return
	 */
	public final static Finder make(String finderName){
		
		if(finderName == null)return null;
		if(sFinders.containsKey(finderName)){
			try {
				return sFinders.get(finderName).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			throw new UnsupportedOperationException("不支持的查询方式-> " + finderName);
		}
		
		
	}
	static{
		sFinders = new HashMap<>();
		sFinders.put("By.id", ByNameFinder.class);
		sFinders.put("By.name",ByIdFinder.class);
	}
 
	/**
	 * 通过 byName 查询
	 * @author YanYadi
	 *
	 */
	static class ByNameFinder implements Finder{
		
		String name;
		ByNameFinder(String name){
			this.name = name;
		}

		@Override
		public WebElement getByDriver(AppiumDriver driver) {
			return driver.findElement(By.name(name));
		}
		
	}
	/**
	 * 通过 byId 查询
	 * @author YanYadi
	 *
	 */
	static class ByIdFinder implements Finder{

		String id;
		public ByIdFinder(String id) {
			this.id = id;
		}
		@Override
		public WebElement getByDriver(AppiumDriver driver) {
			return driver.findElement(By.id(id));
		}
	}
 

}
