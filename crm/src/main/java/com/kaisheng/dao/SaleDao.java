package com.kaisheng.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kaisheng.entity.SaleChance;
import com.kaisheng.entity.customer.Customer;
import com.kaisheng.util.DbHelp;

public class SaleDao {

	public int save(SaleChance saleChance) {
		String sql = "insert into sale_chance (name, cust_id, worth,process,content,last_time,account_id) values (?,?,?,?,?,?,?)";;
		return DbHelp.executeInsert(sql, saleChance.getName(), saleChance.getCustId(),saleChance.getWorth(),saleChance.getProcess(),saleChance.getContent(),saleChance.getLastTime(),saleChance.getAccountId());
	}

	public int countByAccountId(int accountId) {
		String sql = "select count(*) from sale_chance where account_id = ?";
		return DbHelp.query(sql, new ScalarHandler<Long>(), accountId).intValue();
	}

	public List<SaleChance> findListByAccountIdAndPage(int accountId, int start, int pageSize) {
		String sql = "select * from sale_chance sc inner join t_customer c "
				+ "on sc.cust_id = c.id where sc.account_id = ? limit ?,? ";
		
		return DbHelp.query(sql, new ResultSetHandler<List<SaleChance>>(){

			@Override
			public List<SaleChance> handle(ResultSet rs) throws SQLException {
				List<SaleChance> chanceList = new ArrayList<>();
				
				while(rs.next()) {
					SaleChance chance = new SaleChance();
					chance.setId(rs.getInt("id"));
					chance.setName(rs.getString("name"));
					chance.setWorth(rs.getFloat("worth"));
					chance.setProcess(rs.getString("process"));
					chance.setContent(rs.getString("content"));
					chance.setCreateTime(rs.getTimestamp("create_time"));
					chance.setLastTime(rs.getTimestamp("last_time"));
					chance.setAccountId(rs.getInt("account_id"));
					
					Customer customer = new Customer();
					customer.setCustName(rs.getString("cust_name"));
					
					chance.setCustomer(customer);
					chanceList.add(chance);
				}
				return chanceList;
			}
		}, accountId, start , pageSize);
	}

	
	public SaleChance findById(int saleId) {
		String sql = "select * from sale_chance sc inner join t_customer c "
				+ "on sc.cust_id = c.id where sc.id = ?";
		return DbHelp.query(sql, new ResultSetHandler<SaleChance>(){

			@Override
			public SaleChance handle(ResultSet rs) throws SQLException {
				SaleChance chance = null;
				if(rs.next()) {
					chance = new SaleChance();
					chance.setId(rs.getInt("id"));
					chance.setCustId(rs.getInt("cust_id"));
					chance.setName(rs.getString("name"));
					chance.setWorth(rs.getFloat("worth"));
					chance.setProcess(rs.getString("process"));
					chance.setContent(rs.getString("content"));
					chance.setCreateTime(rs.getTimestamp("create_time"));
					chance.setLastTime(rs.getTimestamp("last_time"));
					chance.setAccountId(rs.getInt("account_id"));
					
					Customer customer = new Customer();
					customer.setId(rs.getInt("cust_id"));
					customer.setCustName(rs.getString("cust_name"));
					customer.setSex(rs.getString("sex"));
					customer.setAddress(rs.getString("address"));
					customer.setJobTitle(rs.getString("job_title"));
					customer.setMobile(rs.getString("mobile"));
					customer.setTrade(rs.getString("trade"));
					customer.setSource(rs.getString("source"));
					customer.setLevel(rs.getString("level"));
					customer.setMark(rs.getString("mark"));
					customer.setReminder(rs.getString("reminder"));
					
					chance.setCustomer(customer);
				}
				return chance;
			}
		}, saleId);
	}

	public void update(SaleChance chance) {
		String sql = "update sale_chance set worth = ? , process = ?, content = ?, last_time = ? where id = ?";
		DbHelp.executeUpdate(sql, chance.getWorth(),chance.getProcess(),chance.getContent(),chance.getLastTime(),chance.getId());
	}

	public void delById(int id) {
		String sql = "delete from sale_chance where id = ?";
		DbHelp.executeUpdate(sql, id);
	}	

	public List<SaleChance> findListByCustId(int custId) {
		String sql = "select * from sale_chance where cust_id = ?";
		return DbHelp.query(sql, new BeanListHandler<>(SaleChance.class, new BasicRowProcessor(new GenerousBeanProcessor())), custId);
	}

	public List<Map<String, Object>> dealCount() {
		String sql = "select count(*) as count , process from sale_chance group by process";
		return DbHelp.query(sql, new ResultSetHandler<List<Map<String, Object>>>(){

			@Override
			public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
				List<Map<String, Object>> mapList = new ArrayList<>();
				
				while(rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("count", rs.getInt("count"));
					map.put("process", rs.getString("process"));
					
					mapList.add(map);
				}
				return mapList;
			}
			
		});
	}
}
