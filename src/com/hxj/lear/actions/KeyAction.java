package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class KeyAction extends AbstractAction {
	
	String keys;

	public KeyAction(AppiumDriver driver, String keys) {
		super(driver);
		this.keys = keys;
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		element.sendKeys(this.keys);
		return RESULT_PASS;
	}

} 
