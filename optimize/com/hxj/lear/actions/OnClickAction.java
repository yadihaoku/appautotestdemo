package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

class OnClickAction extends AbstractAction{

	public OnClickAction(AppiumDriver driver) {
		super(driver);
	}
	@Override
	public String run(AppiumDriver driver, WebElement element) {
		element.click();
		return RESULT_PASS;
	}

}
