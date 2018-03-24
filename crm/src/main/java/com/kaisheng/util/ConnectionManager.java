package com.kaisheng.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.kaisheng.exception.DataAccessException;

public class ConnectionManager {

	private static final String DRIVER;
	private static final String URL;
	private static final String NAME;
	private static final String PASSWORD;

	private static BasicDataSource bds = new BasicDataSource();
	private static Properties pro = new Properties();
	
	static {
		
		//解决硬编码问题
		try {
			pro.load(ConnectionManager.class.getClassLoader().getResourceAsStream("Config.properties"));
			DRIVER = pro.getProperty("jdbc.driver");
			URL = pro.getProperty("jdbc.url");
			NAME = pro.getProperty("jdbc.name");
			PASSWORD = pro.getProperty("jdbc.password");
			
		} catch (IOException e) {
			throw new DataAccessException("数据库链接异常",e);
		}
		//解决硬编码问题
		
		bds.setDriverClassName(DRIVER);
		bds.setUrl(URL);
		bds.setUsername(NAME);
		bds.setPassword(PASSWORD);
		
		
		bds.setInitialSize(5);
		bds.setMaxIdle(10);
		bds.setMinIdle(5);
		bds.setMaxWaitMillis(5000);
		
		
	}
	
	
	public static DataSource getDataSource() {
		return bds;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = bds.getConnection();
			
		} catch (SQLException e) {
			throw new DataAccessException("数据库链接异常",e);
		}
		/*try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return conn;
	}
	
}
