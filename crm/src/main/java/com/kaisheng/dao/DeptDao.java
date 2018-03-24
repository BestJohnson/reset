package com.kaisheng.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kaisheng.entity.Dept;
import com.kaisheng.util.DbHelp;

public class DeptDao {

	public Dept findByName(String deptName) {

		String sql = "select * from t_dept where deptname = ?";
		return DbHelp.query(sql, new BeanHandler<>(Dept.class), deptName);
	}

	public void save(Dept dept) {
		
		String sql = "insert into t_dept (deptname,pid) values (?,?)";
		DbHelp.executeUpdate(sql, dept.getDeptName(),dept.getpId());
	}

	public List<Dept> findAll() {
		String sql = "select * from t_dept";
		return DbHelp.query(sql, new BeanListHandler<>(Dept.class)) ;
	}

}
