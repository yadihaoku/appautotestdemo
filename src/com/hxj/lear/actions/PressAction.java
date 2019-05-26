package com.hxj.lear.actions;

import java.time.Duration;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class PressAction extends AbstractAction {

	public PressAction(AppiumDriver driver) {
		super(driver);
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		TouchAction<?>  action = new TouchAction<>(driver);
		action.press(ElementOption.element(element)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)));
		action.perform();
		return RESULT_PASS;
	}

}
