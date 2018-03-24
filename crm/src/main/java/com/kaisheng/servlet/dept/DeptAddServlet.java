package com.kaisheng.servlet.dept;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.DeptService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/dept/add")
public class DeptAddServlet extends BaseServlet{

	
	private static final long serialVersionUID = 1L;

	DeptService service = new DeptService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String deptName = req.getParameter("deptName");
		
		try{
			service.addDept(deptName);
			//此处做了一个封装
			AjaxResult result = AjaxResult.success();
			sendJson(result, resp);
		} catch(ServiceException e) {
			AjaxResult result = AjaxResult.error(e.getMessage());
			sendJson(result, resp);
		}
	}
	
}
