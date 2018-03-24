package com.kaisheng.servlet.sale;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.SaleChance;
import com.kaisheng.entity.customer.Customer;
import com.kaisheng.service.SaleService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/sale/add")
public class SaleChanceAddServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	SaleService service = new SaleService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int accountId = getCurrAccount(req).getId();
		
		List<String> process = service.findAllProcess();
		List<Customer> customers = service.findAllCustomersByAccountId(accountId);
		
		req.setAttribute("process", process);
		req.setAttribute("customerList", customers);
		
		forward("sale/add", req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saleName = req.getParameter("salename");
		String custId = req.getParameter("custId");
		String worth = req.getParameter("worth");
		String process = req.getParameter("process");
		String content = req.getParameter("content");
		
		int accountId = getCurrAccount(req).getId();
		
		SaleChance saleChance = new SaleChance(saleName,Integer.parseInt(custId),Float.parseFloat(worth),process,content,accountId);
		service.saveSaleChance(saleChance);
		AjaxResult result = AjaxResult.success();
		sendJson(result, resp);
	
	
	}
}
