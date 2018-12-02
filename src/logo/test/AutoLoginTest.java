/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hxj.lear.AutoTestCaseFromFile;

import io.appium.java_client.AppiumDriver;
import jxl.read.biff.BiffException;
import logo.file.AutoTestExcelFile;
import logo.module.InitSetup;

public class AutoLoginTest
{
	AppiumDriver driver;                                          
	AutoTestExcelFile fesm = new AutoTestExcelFile();             
	InitSetup is = new InitSetup();                               
	//AutoTestCaseID tcId = new AutoTestCaseID();
	AutoTestCaseFromFile acf = new AutoTestCaseFromFile();

	@BeforeClass
	public void beforeClass() throws BiffException, IOException   
	{
		fesm.SetContentInit((short) 10);                         
	}

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException, InterruptedException 
	{
		driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), is.InitSetupCFG(new DesiredCapabilities()));
	}

	@Test
	public void My_Login_001() throws InterruptedException, BiffException, IOException 
	{
		//tcId.TestcaseId(driver, "My_Login_001");
		acf.testByModuleId(driver, "My_Login_001");
	}

	@AfterMethod
	public void afterMethod()                                     
	{
		driver.quit();                                            
	}
}
