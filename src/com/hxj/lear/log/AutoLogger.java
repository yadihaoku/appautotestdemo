
package com.hxj.lear.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

public class AutoLogger
{
	private Logger logger;
	
	private static boolean SINIT;

	public static AutoLogger getLogger(Class<?> clazz)
	{
		if (! SINIT) {
			Properties props = new Properties();//????
			try
			{
				
				String path = AutoLogger.class.getClass().getResource("/log4j.properties").getPath();
				InputStream is = new FileInputStream(path);
				props.load(is);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			PropertyConfigurator.configure(props); 
			SINIT = true;
		}
		
		AutoLogger logg = new AutoLogger();
		logg.logger = Logger.getLogger(clazz);
		return logg;
	}

	public void log(String msg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		logger.info(msg);
		Reporter.log("Reporter:" + sdf.format(ca.getTime()) + "===>" + msg);
	}

	public void debug(String msg)
	{
		logger.debug(msg);
	}

	public void warn(String msg)
	{
		logger.warn(msg);
		Reporter.log("Reporter:" + msg);
	}

	public void error(String msg)
	{
		logger.error(msg);
		Reporter.log("Reporter:" + msg);
	}
}
