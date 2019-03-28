/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.log;

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
	private static Logger logger = null;//???
	private static AutoLogger logg = null;//??

	public static AutoLogger getLogger(Class<?> T)
	{
		if (logger == null)
		{
			Properties props = new Properties();//????
			try
			{
				InputStream is = new FileInputStream("src//log4j.properties");//??log4j.properties作用？都是定义的什么？
				props.load(is);//????
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			PropertyConfigurator.configure(props);//???
			logger = Logger.getLogger(T);//????自己调用自己？getLogger(T)
			logg = new AutoLogger();
		}
		return logg;
	}

//	 重写logger方法???
	public void log(String msg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();//???
		logger.info(msg);//??
		Reporter.log("Reporter:" + sdf.format(ca.getTime()) + "===>" + msg);//????
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
