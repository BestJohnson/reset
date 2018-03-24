package com.kaisheng.servlet.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
@WebServlet("/customer/my/trans")
public class MyCustomerTransServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	CustomerService service = new CustomerService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String custId = req.getParameter("custId");
		String toAccountId = req.getParameter("toAccountId");
		int accountId = getCurrAccount(req).getId();
		try {
			service.transCustomer(custId,accountId,toAccountId);
			resp.sendRedirect("/customer/my/list");
		} catch(ForbiddenException e) {
			resp.sendError(403,e.getMessage());
		}
	}
}
