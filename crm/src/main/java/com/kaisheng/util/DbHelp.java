package com.kaisheng.util;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kaisheng.exception.DataAccessException;

public class DbHelp {

	private static QueryRunner runner = new QueryRunner(ConnectionManager.getDataSource());

	
	/**
	 * 执行insert，用于需要获得新数据的id值使用该方法，如果不需要使用executeUpdate()方法 
	 */
	public static int executeInsert(String sql, Object... params) {
		try {
			return runner.insert(sql, new ScalarHandler<Long>(),params).intValue(); 
		} catch (SQLException e) {
			throw new DataAccessException("执行" + sql + "异常", e);
		} 
	}
	
	
	
	
	
	/**
	 * 执行insert、delete、update
	 */
	public static void executeUpdate(String sql, Object... params) {
//		Connection conn = ConnectionManager.getConnection();
		try {
//			runner.update(conn, sql, params);
			runner.update(sql, params);
			
		} catch (SQLException e) {
			throw new DataAccessException("执行" + sql + "异常", e);
		} 
		
	}

	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
//		Connection conn = ConnectionManager.getConnection();
		try {
//			return runner.query(conn, sql,rsh ,params);
			return runner.query(sql, rsh, params);
		} catch (SQLException e) {
			throw new DataAccessException("执行" + sql + "异常", e);
		} 
		
	}

	/*private static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	
}
