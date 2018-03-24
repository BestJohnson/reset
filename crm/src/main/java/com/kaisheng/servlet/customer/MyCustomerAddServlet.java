package com.kaisheng.servlet.customer;


//和公海客户的新增公用（CustomerAdd），这个可以删除了
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Account;
import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/customer/my/add")
public class MyCustomerAddServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	CustomerService service = new CustomerService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> sources = service.findAllSources();
		List<String> trades = service.findAllTrades();
		req.setAttribute("sources", sources);
		req.setAttribute("trades", trades);
		forward("customer/add", req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String custname = req.getParameter("custname");
		String sex = req.getParameter("sex");
		String jobtitle = req.getParameter("jobtitle");
		String address = req.getParameter("address");
		String mobile = req.getParameter("mobile");
		String source = req.getParameter("source");
		String trade = req.getParameter("trade");
		String level = req.getParameter("level");
		String mark = req.getParameter("mark");
		
		//通过sessionID，获得当前登陆的用户
		Account currAccount = getCurrAccount(req);
		
		service.addMyCustomer(custname,sex,jobtitle,address,mobile,source,trade,level,mark,currAccount.getId());
		AjaxResult result = AjaxResult.success();
		sendJson(result, resp);
		
	}
}
