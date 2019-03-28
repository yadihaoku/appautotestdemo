/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.module;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class Config                                                   
{                                                                 
	static final String CONFIG_FILE = "android_config.properties";     
	private Map<String, String> configMap;
	private static Config testResManager;

	public static Config getInstance()//config实例化
	{	
		// testResManager 是 static 修饰，标示是静态变量。
		// 此处的 if 判断，目的是保证 testResManager 只能被实例化一次。
		// 当第1 次，调用 Config.getInstance() 时， testResManager 为null，
		//if 条件为 true，将进行初始化。
		// 在第2次之后调用 Config.getInstance() 时，testResManager 已经被实例化，不为null。直接返回 testResManger 。
		if (testResManager == null)
		{
			testResManager = new Config();
		}
		return testResManager;
	}
	/**
	 * 构造函数私有（private 修饰）,对象实例化操作，只能在该当前 class 内。
	 * Q 构造 Config 函数的意义？
	 * 创建 Config 对象时，要从文件中读取配置。类似的操作，放在构造函数中最合适。
	 */
	private Config()
	{
		configMap = loadFile(Config.CONFIG_FILE);
	}
	/**
	 * 此方法，会读取指定的  .properties 配置文件，并转换为 HashMap  
	 * @param fileName
	 * @return
	 */
	protected Map<String, String> loadFile(String fileName)
	{
		Map<String, String> map = null;
		if ((fileName != null) && (!fileName.trim().equals("")))
		{
			InputStream is = Config.class.getClassLoader().getResourceAsStream(fileName);
			if (is != null)
			{
				map = new HashMap<String, String>();
				//Q Map map = new HashMap<>?为什么new的不是Map，而是HashMap？
				//  Map 是接口，new 创建对象时，一般 new 接口的实现类。 
				Properties prop = new Properties();
				try
				{
					prop.load(is);
					//Q load()函数是加载？
					for (Entry<Object, Object> entry : prop.entrySet())
					{
						String key = (String) entry.getKey();
						String value = (String) entry.getValue();
						map.put(key, value);
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				/**
				 * properties 文件加载之后，需要关闭。
				 */
				finally{
					IOUtils.closeQuietly(is);
				}
			}
		}
		return map;
	}

	public String getCfg(String key)
	{
		return configMap.get(key);//有没有顺序要求
	}

}
