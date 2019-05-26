package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import com.hxj.lear.utils.SwipeUtils;

import io.appium.java_client.AppiumDriver;

public class DownPageAction extends AbstractAction {

	public DownPageAction(AppiumDriver driver) {
		super(driver);
	}

	@Override
	public String run(AppiumDriver driver, WebElement element) {
		
//		Point point = element.getLocation();
//		Dimension size = element.getSize();
//		
//		int halfWidth = size.getWidth() / 2;
		
		//上下滑动时， 开始时的x坐标与 结束时的x坐标相同。
//		int startX , endX = startX = point.getX() + halfWidth; 
//		
//		int startY = ((Double)(size.getHeight() *  0.9 )).intValue() + point.getY();
//		int endY = ((Double)(size.getHeight() * 0.5)).intValue() + point.getY();
//		driver.swipe(startX, startY, endX, endY, 700);
		// 5.0 之后移除了 driver.swipe
		SwipeUtils.swipeToDown(driver);
		return RESULT_PASS;
	}

}
