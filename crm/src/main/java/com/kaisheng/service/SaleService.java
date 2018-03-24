package com.kaisheng.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.dao.SaleDao;
import com.kaisheng.dao.SaleRecordDao;
import com.kaisheng.dao.customer.CustomerDao;
import com.kaisheng.entity.SaleChance;
import com.kaisheng.entity.SaleChanceRecord;
import com.kaisheng.entity.customer.Customer;
import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;
import com.kaisheng.util.Page;

public class SaleService {
	
	CustomerDao custDao = new CustomerDao();
	SaleDao saleDao = new SaleDao();
	SaleRecordDao saleRecordDao = new SaleRecordDao();
	
	/**
	 * 获取销售机会进度列表
	 * @return
	 */
	public List<String> findAllProcess() {
		String processString = Config.get("sales.process");
		String[] processArray = processString.split(",");
		return Arrays.asList(processArray);
	}

	/**
	 * 获取当前员工的所有客户列表
	 * @param accountId
	 * @return
	 */
	public List<Customer> findAllCustomersByAccountId(int accountId) {
		return custDao.findByAccountId(accountId);
	}

	/**
	 * 保存销售机会
	 * @param saleChance
	 */
	public void saveSaleChance(SaleChance saleChance) {
		Customer cust = custDao.findById(saleChance.getCustId());
		cust.setLastConcatTime(new Timestamp(System.currentTimeMillis()));;
		custDao.update(cust);
		
		saleChance.setLastTime(new Timestamp(System.currentTimeMillis()));
		int saveId = saleDao.save(saleChance);
		
		//在sale_chance_record表中添加记录
		if(StringUtils.isNotEmpty(saleChance.getContent())) {
			SaleChanceRecord record = new SaleChanceRecord();
			record.setContent(saleChance.getContent());
			record.setSaleId(saveId);
			saleRecordDao.save(record);
		}
	}

	/**
	 * 根据accountId获得对应的销售机会列表的page对象
	 * @param pageNo
	 * @param accountId
	 * @return
	 */
	public Page<SaleChance> findSaleListByPage(int pageNo, int accountId) {
		int count = saleDao.countByAccountId(accountId);
		Page<SaleChance> page = new Page<>(count,pageNo);
		List<SaleChance> saleChanceList = saleDao.findListByAccountIdAndPage(accountId,page.getStart(),page.getPageSize());
		page.setItems(saleChanceList);
		
		return page;
	}

	/**
	 * 根据saleId查找saleChance对象
	 * @param saleId
	 * @param accountId
	 * @return
	 */
	public SaleChance findSaleChanceById(String saleId, int accountId) {
		SaleChance saleChance = checkSaleChance(saleId,accountId);
		return saleChance;
	}

	/**
	 * 1.判断saleId是否为纯数字
	 * 2.判断seleId是否合法，是否存在对应的saleChance对象
	 * 3.当前登录员工是否有查看该销售机会详情的权限
	 * @param saleId
	 * @param accountId
	 * @return
	 */
	private SaleChance checkSaleChance(String saleId, int accountId) {
		if(!StringUtils.isNumeric(saleId)) {
			throw new ServiceException("参数异常");
		}
		
		SaleChance saleChance = saleDao.findById(Integer.parseInt(saleId));
		
		if(saleChance == null) {
			throw new ServiceException("参数异常");
		}
		
		if(saleChance.getAccountId() == accountId) {
			return saleChance;
		} else {
			throw new ForbiddenException("拒绝访问");
		}
	}

	/**
	 * 根据saleId查找跟进记录列表
	 * @param saleId
	 * @return
	 */
	public List<SaleChanceRecord> findRecordListBySaleId(String saleId) {
		return saleRecordDao.findListBySaleId(saleId);
		
	}

	/**
	 * 增加跟进记录
	 * @param record
	 * @param accountId
	 */
	public void addRecord(SaleChanceRecord record, int accountId) {
		saleRecordDao.save(record);
		
		SaleChance chance = saleDao.findById(record.getSaleId());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		chance.setLastTime(now);
		
		saleDao.update(chance);
		
		Customer cust = chance.getCustomer(); 
		cust.setLastConcatTime(now);
		cust.setUpdateTime(now);
		custDao.update(cust);
	}

	/**
	 * 根据saleId删除销售机会
	 * @param saleId
	 * @param accountId
	 */
	public void delBySaleId(String saleId, int accountId) {
		SaleChance chance = checkSaleChance(saleId, accountId);
		saleRecordDao.delBySaleId(chance.getId());
		saleDao.delById(chance.getId());
	}

	/**
	 * 更新销售机会的进度
	 * @param saleId
	 * @param process
	 * @param accountId
	 */
	public void updateSaleChanceProcess(String saleId, String process, int accountId) {
		SaleChance chance = saleDao.findById(Integer.parseInt(saleId));
		chance.setProcess(process);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		chance.setLastTime(now);
		saleDao.update(chance);
		
		SaleChanceRecord record = new SaleChanceRecord();
		record.setSaleId(Integer.parseInt(saleId));
		record.setContent("修改当前进度为[ " + process + " ]");
		saleRecordDao.save(record);
		
		Customer cust = custDao.findById(chance.getCustId());
		cust.setLastConcatTime(now);
		custDao.update(cust);
	}

	/**
	 * 查询custId对应的销售机会
	 * @param id
	 * @return
	 */
	public List<SaleChance> findSaleChanceListByCustId(int custId) {
		return saleDao.findListByCustId(custId);
	}

	/**
	 * 统计客户成交比
	 * @return
	 */
	public List<Map<String, Object>> customerDealCount() {
		return saleDao.dealCount();
	}
	
	
}
