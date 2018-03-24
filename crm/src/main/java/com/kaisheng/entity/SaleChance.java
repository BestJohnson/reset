package com.kaisheng.entity;

import java.sql.Timestamp;

import com.kaisheng.entity.customer.Customer;

public class SaleChance {

	private int id;
	private String name;
	private int custId;
	private float worth;
	private String process;
	private String content;
	private Timestamp createTime;
	private Timestamp lastTime;
	private int accountId;
	
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SaleChance(){};
	
	public SaleChance(String name, int custId, float worth, String process, String content,
			int accountId) {
		this.name = name;
		this.custId = custId;
		this.worth = worth;
		this.process = process;
		this.content = content;
		this.accountId = accountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public float getWorth() {
		return worth;
	}

	public void setWorth(float worth) {
		this.worth = worth;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	
}
