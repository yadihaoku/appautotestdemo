package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import com.hxj.lear.utils.Finder;
import com.hxj.lear.utils.ScreenShot;

import io.appium.java_client.AppiumDriver;

public class CaptureAction extends AbstractAction {

	public CaptureAction(AppiumDriver driver) {
		super(driver);
		setFinder(Finder.EMPTY);
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		ScreenShot.takeScreenShot(driver);
		return RESULT_PASS;
	}

}
