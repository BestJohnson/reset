package com.kaisheng.servlet.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Account;
import com.kaisheng.entity.SaleChance;
import com.kaisheng.entity.customer.Customer;
import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.AccountService;
import com.kaisheng.service.SaleService;
import com.kaisheng.service.customer.CustomerService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.Config;
@WebServlet("/customer/public/detail")
public class PublicCustomerDetailServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	CustomerService service = new CustomerService();
	AccountService accService = new AccountService();
	SaleService saleService = new SaleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String custId = req.getParameter("id");
		try {
			Customer cust = service.findCustById(custId,Config.PUBLIC_ID);
			List<Account> accountList = accService.findAllAccount();
			List<SaleChance> chanceList = saleService.findSaleChanceListByCustId(cust.getId());
			
			req.setAttribute("customer", cust);
			req.setAttribute("accountList", accountList);
			req.setAttribute("chanceList", chanceList);
			
			forward("customer/publicdetail", req, resp);
		} catch(ServiceException e) {
			resp.sendError(404,e.getMessage());
		} catch(ForbiddenException e) {
			resp.sendError(403,e.getMessage());
		}
	
		
	}
}
