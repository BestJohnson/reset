package com.kaisheng.servlet.charts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/charts/level/count")
public class ChartsLevelCountServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	CustomerService service =  new CustomerService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, Object>> mapList = service.customerLevelCount();
		AjaxResult result = AjaxResult.success(mapList);
		sendJson(result, resp);
	}
	
		
		
}
