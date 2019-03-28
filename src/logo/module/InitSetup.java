/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.module;

import java.net.MalformedURLException;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;

public class InitSetup 
{
	public DesiredCapabilities InitSetupCFG(final DesiredCapabilities capabilities) throws MalformedURLException
	{ 
		if (Config.CONFIG_FILE.equals("android_config.properties"))  
		{
			capabilities.setCapability("app", Config.getInstance().getCfg("app"));                               
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("platformVersion", Config.getInstance().getCfg("platformVersion"));        
			capabilities.setCapability("platformName", Config.getInstance().getCfg("platformName"));              
			capabilities.setCapability("deviceName", Config.getInstance().getCfg("deviceName"));                  
			capabilities.setCapability("appPackage", Config.getInstance().getCfg("appPackage"));                  
			capabilities.setCapability("appActivity", Config.getInstance().getCfg("appActivity"));                
			capabilities.setCapability("unicodeKeyboard", Boolean.parseBoolean( Config.getInstance().getCfg("unicodeKeyboard") ));        
			capabilities.setCapability("resetKeyboard", Boolean.parseBoolean( Config.getInstance().getCfg("resetKeyboard" )));            
			capabilities.setCapability("newCommandTimeout", Config.getInstance().getCfg("newCommandTimeout"));
			capabilities.setCapability("noReset", true);
		} else if (Config.CONFIG_FILE.equals("ios_config.properties"))
		{
			capabilities.setCapability("platformVersion", Config.getInstance().getCfg("platformVersion"));        
			capabilities.setCapability("platformName", Config.getInstance().getCfg("platformName"));              
			capabilities.setCapability("deviceName", Config.getInstance().getCfg("deviceName"));                  
			capabilities.setCapability("udid", Config.getInstance().getCfg("udid"));                              
			capabilities.setCapability("app", Config.getInstance().getCfg("app"));                                
			return capabilities;
		}
		return capabilities;
	}

	public void TearDownCFG(AppiumDriver driver) throws MalformedURLException
	{
		driver.quit();
	}
}
