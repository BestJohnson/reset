package com.kaisheng.servlet.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.customer.Customer;
import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/customer/my/edit")
public class MyCustomerEditServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	CustomerService service = new CustomerService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String custId = req.getParameter("id");
		int accountId = getCurrAccount(req).getId();
	
		try {
			Customer cust = service.getCustomerById(custId,accountId);
			List<String> sources = service.findAllSources();
			List<String> trades  = service.findAllTrades();
			
			req.setAttribute("customer", cust);
			req.setAttribute("sources", sources);
			req.setAttribute("trades", trades);
			forward("customer/edit", req, resp);
		} catch(ServiceException e) {
			resp.sendError(404,e.getMessage());
		} catch (ForbiddenException e) {
			resp.sendError(403, e.getMessage());
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String custId = req.getParameter("custId");
		String custname = req.getParameter("custname");
		String sex = req.getParameter("sex");
		String jobtitle = req.getParameter("jobtitle");
		String address = req.getParameter("address");
		String mobile = req.getParameter("mobile");
		String source = req.getParameter("source");
		String trade = req.getParameter("trade");
		String level = req.getParameter("level");
		String mark = req.getParameter("mark");
		
		service.edit(custId,custname,sex,jobtitle,address,mobile,source,trade,level,mark);
		
		AjaxResult result = AjaxResult.success();
		sendJson(result, resp);
	}
}
