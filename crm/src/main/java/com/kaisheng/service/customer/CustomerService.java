package com.kaisheng.service.customer;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.dao.AccountDao;
import com.kaisheng.dao.customer.CustomerDao;
import com.kaisheng.entity.Account;
import com.kaisheng.entity.customer.Customer;
import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;
import com.kaisheng.util.Page;

public class CustomerService {

	CustomerDao dao = new CustomerDao();
	AccountDao accDao = new AccountDao();
	/**
	 * 根据accountId 和 pageNo查找对应的customer集合
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<Customer> findCustomerByPage(int accountId, int pageNo) {
		int count = dao.count(accountId);
		Page<Customer> page = new Page<>(count,pageNo);
		List<Customer> custList = dao.findCustmerListByPage(accountId,page.getStart(),page.getPageSize());
		
		page.setItems(custList);
		return page;
	}

	/**
	 * 新增客户时获得客户来源列表
	 * @return
	 */
	public List<String> findAllSources() {
		String source = Config.get("customer.source");
		String[] sourceArray = source.split(",");
		return Arrays.asList(sourceArray);
	}

	/**
	 * 新增客户时获得客户行业列表
	 * @return
	 */
	public List<String> findAllTrades() {
		String trade = Config.get("customer.trade");
		String[] tradeArray = trade.split(",");
		return Arrays.asList(tradeArray);
	}

	/**
	 * 
	 * 新增当前登录员工的客户信息
	 * @param custname
	 * @param sex
	 * @param jobTitle
	 * @param address
	 * @param mobile
	 * @param source
	 * @param trade
	 * @param level
	 * @param mark
	 * @param accountId 当前登录员工的id
	 */
	public void addMyCustomer(String custname, String sex, String jobTitle, String address, String mobile,
			String source, String trade, String level, String mark, int accountId) {

		Customer customer = new Customer(custname, sex, jobTitle,address, mobile, trade, source, level, mark, accountId);
		customer.setReminder("员工添加");
		customer.setLastConcatTime(new Timestamp(System.currentTimeMillis()));
		customer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		dao.add(customer);
	}
	
	
	/**
	 * 根据custId获得对应的customer对象
	 * @param custId
	 * @return
	 */
	private Customer checkCustomer(String custId,int accountId) {
		//  1.校验custId是否合法     ---觉得没必要，就是在当前页面点击的，不可能不合法
		if(!StringUtils.isNumeric(custId)) {
			throw new ServiceException("参数异常");
		}
		
		//  2.根据custId查找对应的对象，如果该对象不存在，在抛出异常
		Customer cust = dao.findById(Integer.parseInt(custId));
		if(cust == null) {
			throw new ServiceException("参数异常");
		}
		//  3.校验该客户是否为当前登录员工的客户
		if(cust.getAccountId() == accountId) {
			return cust;
		} else {
			throw new ForbiddenException("拒绝访问");
		}	
		
	}
	
	
	/**
	 * 通过客户id删除
	 * @param custId
	 * @param accountId
	 */
	public void delByCustId(String custId, int accountId) {
		checkCustomer(custId,accountId);
		dao.del(custId);
	}

	/**
	 * 把custId对应的客户放入公海
	 * @param custId
	 * @param accountId
	 */
	public void toPublicByCustId(String custId, int accountId) {
		Customer customer = checkCustomer(custId,accountId);
		Account account = accDao.findById(accountId);
		
		customer.setAccountId(Config.PUBLIC_ID);
		customer.setReminder(customer.getReminder() + ";" + account.getUsername() +"放入公海");
		dao.update(customer);
	}

	/**
	 * 根据id找对应的客户
	 * @param custId
	 * @param accountId 不是当前登录员工，无权访问
	 * @return
	 */
	public Customer findCustById(String custId, int accountId) {
		/*Customer cust = dao.findById(custId);
		if(cust.getAccountId() != accountId) {
			throw new ForbiddenException("拒绝访问");
		};*/
		Customer cust = checkCustomer(custId, accountId);
		return cust;
	}

	/**
	 * 转交客户
	 * @param custId
	 * @param accountId
	 * @param toAccountId
	 */
	public void transCustomer(String custId, int accountId, String toAccountId) {
		Customer customer = dao.findById(Integer.parseInt(custId));
		Account account = accDao.findById(accountId);
		Account toAccount = accDao.findById(Integer.parseInt(toAccountId));
		
		customer.setAccountId(toAccount.getId());
		customer.setReminder(customer.getReminder() + "," + account.getUsername() + "转交给" + toAccount.getUsername());
		dao.update(customer);
	}

	/**
	 * 根据客户id找客户，修改客户信息时用
	 * @param custId
	 * @param accountId
	 * @return
	 */
	public Customer getCustomerById(String custId, int accountId) {
		Customer customer = checkCustomer(custId,accountId);
		return customer;
	}

	/**
	 * 修改客户信息
	 * @param custId
	 * @param custname
	 * @param sex
	 * @param jobtitle
	 * @param address
	 * @param mobile
	 * @param source
	 * @param trade
	 * @param level
	 * @param mark
	 */
	public void edit(String custId, String custName, String sex, String jobTitle, String address, String mobile,
			String source, String trade, String level, String mark) {
		
		Customer cust = dao.findById(Integer.parseInt(custId));
		cust.setCustName(custName);
		cust.setSex(sex);
		cust.setJobTitle(jobTitle);
		cust.setAddress(address);
		cust.setMobile(mobile);
		cust.setSource(source);
		cust.setTrade(trade);
		cust.setLevel(level);
		cust.setMark(mark);
		cust.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		dao.update(cust);
	}

	/**
	 * 统计各个级别客户的数量
	 * @return
	 */
	public List<Map<String, Object>> customerLevelCount() {
		return dao.levelCount();
	}

}
