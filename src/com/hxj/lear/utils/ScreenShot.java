package com.hxj.lear.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.hxj.lear.config.Config;
import com.hxj.lear.test.Driver;

public class ScreenShot {
	public static void takeScreenShot(WebDriver driver) {
		File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShotFile,
					new File("screenshot/" + Config.INIT_TIME + "/" + getCurrentDateTime() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void takeFailureScreenShot(String fail) {
		
		if(Driver.SDriver == null) {	
			return;
		}
		
		File screenShotFile = Driver.SDriver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShotFile,
					new File("screenshot/" + Config.INIT_TIME + "/failure_(" + fail +")" + getCurrentDateTime() + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return df.format(new Date());
	}

	public static File getDatePath() {
		Date date = new Date();
		String path = new SimpleDateFormat("yyyyMMdd").format(date);
		File f = new File(path);
		return f;
	}
}
