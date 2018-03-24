package com.kaisheng.servlet.sale;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.service.SaleService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/sale/process/update")
public class SaleChanceProcessUpdateServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	SaleService service = new SaleService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saleId = req.getParameter("saleId");
		String process = req.getParameter("process");
		
		int accountId = getCurrAccount(req).getId();
		service.updateSaleChanceProcess(saleId,process,accountId);
		
		resp.sendRedirect("/sale/detail?saleId=" + saleId);
	}
	
}
