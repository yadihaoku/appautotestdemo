package com.hxj.lear.config;

import java.net.MalformedURLException;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 初始化Android/IOS系统，设备等信息，如app路径，系统版本，系统名称，设备名称，（包名，appActivity，noReset等）/udid
 * @author YanYadi
 *
 */
public class InitSetup 
{
	public static DesiredCapabilities InitSetupCFG(final DesiredCapabilities capabilities) throws MalformedURLException
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
			capabilities.setCapability("noReset", false);
			capabilities.setCapability("clearSystemFiles", true);
		} else if (Config.CONFIG_FILE.equals("ios_config.properties"))
		{
			capabilities.setCapability("platformVersion", Config.getInstance().getCfg("platformVersion"));        
			capabilities.setCapability("platformName", Config.getInstance().getCfg("platformName"));              
			capabilities.setCapability("deviceName", Config.getInstance().getCfg("deviceName"));                  
			capabilities.setCapability("udid", Config.getInstance().getCfg("udid"));                              
			capabilities.setCapability("app", Config.getInstance().getCfg("app"));  
		}
		return capabilities;
	}
}
