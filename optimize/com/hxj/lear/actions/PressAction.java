package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class PressAction extends AbstractAction {

	public PressAction(AppiumDriver driver) {
		super(driver);
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		TouchAction action = new TouchAction(driver);
		action.press(element).waitAction(5000);
		action.perform();
		return RESULT_PASS;
	}

}
