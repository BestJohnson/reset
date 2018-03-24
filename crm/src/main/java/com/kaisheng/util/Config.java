package com.kaisheng.util;

import java.io.IOException;
import java.util.Properties;

public class Config {

	public static final int PUBLIC_ID = 0;
	public static final int COMPANY_ID = 1;
	public static final int TASK_STATUS_UNDONE = 0;
	public static final int TASK_STATUS_DONE = 1;
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(Config.class.getClassLoader().getResourceAsStream("Config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return prop.getProperty(key);
	}
	
}
