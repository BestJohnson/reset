package com.kaisheng.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.dao.DeptDao;
import com.kaisheng.entity.Dept;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;

public class DeptService {

	DeptDao dao = new DeptDao();
	
	/**
	 * 添加新部门
	 * @param deptName 部门名称
	 */
	public void addDept(String deptName) {
		
		if(StringUtils.isEmpty(deptName)) {
			throw new ServiceException("部门名称不能为空");
		}
		Dept dept = dao.findByName(deptName);
		
		if(dept != null ){
			throw new ServiceException("部门名称已存在");
		}
		
		dept = new Dept();
		dept.setDeptName(deptName);
		dept.setpId(Config.COMPANY_ID);
		dao.save(dept);
	}
	

	/**
	 * 获取部门列表
	 * @return
	 */
	public List<Dept> findAllDept() {
		return dao.findAll();
	}

}
