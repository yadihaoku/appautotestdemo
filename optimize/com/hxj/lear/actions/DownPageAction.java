package com.hxj.lear.actions;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class DownPageAction extends AbstractAction {

	public DownPageAction(AppiumDriver driver) {
		super(driver);
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		
		sleep(1_000);
		
		Point point = element.getLocation();
		Dimension size = element.getSize();
		int endy = point.getY();
		int endx = point.getX() + size.getHeight() / 2;
		int starty = point.getY() + size.getWidth() - 5;
		int startx = endx;
		driver.swipe(startx, starty, endx, endy, 700);
		
		sleep(3_000);
		return null;
	}

}
