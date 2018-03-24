package com.kaisheng.dao;

import java.util.ArrayList;
import java.util.List;

import com.kaisheng.entity.AccountDept;
import com.kaisheng.util.DbHelp;

public class AccountDeptDao {

	public void save(List<AccountDept> list) {
		String sql = "insert into t_account_dept (account_id,dept_id) values";
		List<Object> arrays = new ArrayList<>();
		
		for(AccountDept accountDept : list) {
			sql += "(?,?),"; // values (?,?),(?,?),
			
			arrays.add(accountDept.getAccountId());
			arrays.add(accountDept.getDeptId());
		}
		
		sql = sql.substring(0, sql.length() - 1);
		DbHelp.executeUpdate(sql, arrays.toArray());
		
	}

}
