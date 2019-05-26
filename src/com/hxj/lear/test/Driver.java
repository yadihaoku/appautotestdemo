package com.hxj.lear.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.hxj.lear.config.InitSetup;

import io.appium.java_client.AppiumDriver;

public class Driver {

	public static AppiumDriver SDriver;

	@Parameters("hubUrl")
	@BeforeSuite
	public static void beforeSuite(String hubUrl) throws MalformedURLException {
		SDriver = new AppiumDriver(new URL(hubUrl), InitSetup.InitSetupCFG(new DesiredCapabilities()));
		
		//报告正确显示截图
		System.setProperty("org.uncommons.reportng.escape-output", "false"); 
		
		assertThat(SDriver).isNotNull();
		
//		assertThat("").startsWith("hxj");
	}

	@AfterSuite
	public static void afterSuite() {
		if (SDriver != null) {
			SDriver.quit();
		}
	}

//	public static void main(String[] args) {
//
//		DecimalFormat df = new DecimalFormat("0.#");
//		double a = 100;
//		double b = 99.89;
//		double c = 99.35;
//
//		System.out.println(df.format(a));
//		System.out.println(df.format(b));
//		System.out.println(df.format(c));
//
//		System.out.println("asdfasd");
//	}

}
