package com.kaisheng.dao.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kaisheng.entity.customer.Customer;
import com.kaisheng.util.DbHelp;

public class CustomerDao {

	public int count(int accountId) {
		String sql = "select count(*) from t_customer where account_id = ?";
		return DbHelp.query(sql, new ScalarHandler<Long>(), accountId).intValue();
	}

	public List<Customer> findCustmerListByPage(int accountId, int start, int pageSize) {
		String sql = "select * from t_customer where account_id = ? limit ?,?";
		return DbHelp.query(sql, new BeanListHandler<>(Customer.class, new BasicRowProcessor(new GenerousBeanProcessor())), accountId, start, pageSize);
	}

	public void add(Customer customer) {
		String sql = "insert into t_customer (cust_name,sex,job_title,mobile,address,trade,source,level,mark,account_id,reminder) values (?,?,?,?,?,?,?,?,?,?,?)";
		DbHelp.executeUpdate(sql, customer.getCustName(),customer.getSex(),customer.getJobTitle(),customer.getMobile(),customer.getAddress(),customer.getTrade(),customer.getSource(),customer.getLevel(),customer.getMark(),customer.getAccountId(),customer.getReminder());
	}

	public Customer findById(int custId) {
		String sql = "select * from t_customer where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Customer.class, new BasicRowProcessor(new GenerousBeanProcessor())), custId);
	}

	public void del(String custId) {
		String sql = "delete from t_customer where id = ?";
		DbHelp.executeUpdate(sql, custId);
	}

	public void update(Customer customer) {
		String sql = "update t_customer set cust_name = ?, sex = ?, job_title=?, mobile = ?, address=?, trade=?, source=?,level=?,mark=?, account_id=?, last_concat_time =?, update_time=?, reminder=? where id=?";
		DbHelp.executeUpdate(sql, customer.getCustName(),customer.getSex(),customer.getJobTitle(),customer.getMobile(),customer.getAddress(),customer.getTrade(),customer.getSource(),customer.getLevel(),customer.getMark(),customer.getAccountId(),customer.getLastConcatTime(),customer.getUpdateTime(),customer.getReminder(),customer.getId());
	}

	public List<Customer> findByAccountId(int accountId) {
		String sql = "select * from t_customer where account_id = ?";
		return DbHelp.query(sql, new BeanListHandler<>(Customer.class, new BasicRowProcessor(new GenerousBeanProcessor())), accountId);
	}

	public List<Map<String, Object>> levelCount() {
		String sql = "select count(*) as count ,level from t_customer group by level";
		return DbHelp.query(sql, new ResultSetHandler<List<Map<String, Object>>>(){

			@Override
			public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
				List<Map<String, Object>> mapList = new ArrayList<>();
				while(rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("count", rs.getInt("count"));
					map.put("level", rs.getString("level"));
					mapList.add(map);
				}
				return mapList;
			}
			
		});
	}


	
}
