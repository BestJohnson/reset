package com.kaisheng.servlet.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.AccountService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/account/add")
public class AccountAddServlet extends BaseServlet{

	
	private static final long serialVersionUID = 1L;
	AccountService service = new AccountService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String mobile = req.getParameter("mobile");
		String[] deptId = req.getParameterValues("deptId");
		
		try{
			service.saveAccount(userName,password,mobile,deptId);
			AjaxResult result = AjaxResult.success();
			System.out.println(result);
			sendJson(result, resp);
		} catch(ServiceException e) {
			AjaxResult result = AjaxResult.error(e.getMessage());
			sendJson(result, resp);
		}
	}
}
