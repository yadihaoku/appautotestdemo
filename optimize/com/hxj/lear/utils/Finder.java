package com.hxj.lear.utils;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public interface Finder {

	WebElement getByDriver(AppiumDriver driver);
}
