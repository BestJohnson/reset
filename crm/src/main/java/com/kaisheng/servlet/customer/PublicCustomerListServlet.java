package com.kaisheng.servlet.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.entity.customer.Customer;
import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.Config;
import com.kaisheng.util.Page;
@WebServlet("/customer/public/list")
public class PublicCustomerListServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	CustomerService service = new CustomerService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String p = req.getParameter("p");
		
		int pageNo = 1;
		if(StringUtils.isNumeric(p)) {
			pageNo = Integer.parseInt(p);
		}
		
		Page<Customer> page = service.findCustomerByPage(Config.PUBLIC_ID,pageNo);
		
		req.setAttribute("page", page);
		
		forward("customer/detaillist", req, resp);
		
	}
	
}
