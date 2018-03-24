package com.kaisheng.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.kaisheng.dao.AccountDao;
import com.kaisheng.dao.AccountDeptDao;
import com.kaisheng.entity.Account;
import com.kaisheng.entity.AccountDept;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;
import com.kaisheng.util.EhcacheUtil;
import com.kaisheng.util.Page;

public class AccountService {

	AccountDao dao = new AccountDao();
	AccountDeptDao accountDeptDao = new AccountDeptDao();
	EhcacheUtil cacheUtil = new EhcacheUtil("account");
	
	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public Account login(String username, String password) {
		Account acc = dao.findByName(username);
		String md5Passwrod = DigestUtils.md5Hex(password + Config.get("user.password.salt"));
		
		if(acc != null && md5Passwrod.equals(acc.getPassword())) {
			return acc;
		} else {
			throw new ServiceException("账号或密码错误");
		}
	}
	
	
	/**
	 * 保存新建员工信息
	 * @param userName
	 * @param password 明文
	 * @param mobile 
	 * @param deptId 部门id数组
	 */
	public void saveAccount(String userName, String password, String mobile, String[] deptIds) throws ServiceException{
		
		Account account = dao.findByMobile(mobile);
		if(account != null) {
			throw new ServiceException("电话号码已存在");
		}
		
		//新增account数据
		account = new Account();
		account.setUsername(userName);
		account.setTel(mobile);
		String md5Password = DigestUtils.md5Hex(password + Config.get("user.password.salt"));
		account.setPassword(md5Password);
		account.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		// 调用save方法并获得自动生成的id
		int accountId = dao.save(account);

		// 新增员工和部门的对应关系
		List<AccountDept> list = new ArrayList<>();
		
		for(String deptId : deptIds) {
			AccountDept accountDept = new AccountDept();
			accountDept.setAccountId(accountId);
			accountDept.setDeptId(Integer.parseInt(deptId));
			list.add(accountDept); 
		}
		
		accountDeptDao.save(list);
		
	}


	/**
	 * 通过部门id和页码获取对应的page对象
	 * @param deptId
	 * @param pageNo
	 * @return
	 */
	public Page<Account> findAccountByPage(String deptId, int pageNo) {
		int count = dao.count(deptId);
		Page<Account> page = new Page<>(count,pageNo);
 		List<Account> accountList = dao.findByPage(deptId,page.getStart(),page.getPageSize());
 		page.setItems(accountList);
		return page;
	}


	/**
	 * 查找所有员工，用于转交他人事使用
	 * @return
	 */
	public List<Account> findAllAccount() {
		@SuppressWarnings("unchecked")
		List<Account> accList = (ArrayList<Account>) cacheUtil.getCacheValue("accList");
		if(accList == null) {
			dao.findAll();
			cacheUtil.setCache("accList", accList);
		}
		return accList;
	}

	/**
	 * 根据id查account对象（先去缓存里找，没有再去数据库查）
	 * @param accountId
	 * @return
	 */
	public Account findById(int accountId) {
		Account acc = (Account) cacheUtil.getCacheValue(accountId);
		if(acc == null) {
			dao.findById(accountId);
			cacheUtil.setCache(accountId, acc);
		}
		return acc;
	}
	
	/**
	 * 根据id删除account
	 * @param accountId
	 */
	public void delById(int accountId) {
		dao.delById(accountId);
		cacheUtil.removeByKey(accountId);
	}
	
	
}
