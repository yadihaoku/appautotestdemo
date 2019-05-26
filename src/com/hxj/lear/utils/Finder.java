package com.hxj.lear.utils;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public interface Finder {
//内部类
	Finder EMPTY = new Finder() {
		public WebElement getByDriver(AppiumDriver driver) {
			return null;
		}
	};
	WebElement getByDriver(AppiumDriver driver);
}
