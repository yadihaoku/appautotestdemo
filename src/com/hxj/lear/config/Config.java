
package com.hxj.lear.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class Config {
	static final String CONFIG_FILE = "android_config.properties";
	public static final String INIT_TIME;
	static {
		// 首次执行时，初始化时间
		INIT_TIME = new SimpleDateFormat("yyyyMMdd-HH-mm").format(Calendar.getInstance().getTime());
	}
	private Map<String, String> configMap;
	private static Config testResManager;

	public static Config getInstance()// config实例化
	{
		// testResManager 是 static 修饰，标示是静态变量。
		// 此处的 if 判断，目的是保证 testResManager 只能被实例化一次。
		// 当第1 次，调用 Config.getInstance() 时， testResManager 为null，
		// if 条件为 true，将进行初始化。
		// 在第2次之后调用 Config.getInstance() 时，testResManager 已经被实例化，不为null。直接返回
		// testResManger 。
		if (testResManager == null) {
			testResManager = new Config();
		}
		return testResManager;
	}

	/**
	 * 构造函数私有（private 修饰）,对象实例化操作，只能在该当前 class 内。
	 * 对象时，要从文件中读取配置。类似的操作，放在构造函数中最合适。
	 */
	@SuppressWarnings("unchecked")
	private Config() {
		this.configMap = loadFile(Config.CONFIG_FILE);
	}

	public String getCfg(String key) {
		return configMap.get(key);
	}

	/**
	 * 此方法，会读取指定的 .properties 配置文件，并转换为 HashMap
	 * 
	 * @param fileName
	 * @return
	 */
	protected Map loadFile(String fileName) {
		Properties prop = null;//Properties是Hashtable，和HashMap类似；实例化分开写是为了避免无用的new操作
		if ((fileName != null) && (!fileName.trim().equals(""))) {
			InputStream is = Config.class.getClassLoader().getResourceAsStream(fileName);//把配置文件转变成输入流
			prop = new Properties();
			try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(is);
			}

		}
		return prop;
	}
}
