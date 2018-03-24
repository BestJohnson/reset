package com.kaisheng.servlet.sale;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.SaleChance;
import com.kaisheng.entity.SaleChanceRecord;
import com.kaisheng.exception.ForbiddenException;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.SaleService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/sale/detail")
public class SaleChanceDetailServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	SaleService saleService = new SaleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saleId = req.getParameter("saleId");
		int accountId = getCurrAccount(req).getId();
		try {
			SaleChance saleChance = saleService.findSaleChanceById(saleId,accountId);
			List<SaleChanceRecord> recordList = saleService.findRecordListBySaleId(saleId);
			List<String> processList = saleService.findAllProcess();
			
			req.setAttribute("saleChance", saleChance);
			req.setAttribute("recordList", recordList);
			req.setAttribute("processList", processList);
			forward("sale/detail", req, resp);
		} catch(ServiceException e) {
			resp.sendError(404,e.getMessage());
		} catch(ForbiddenException e) {
			resp.sendError(403,e.getMessage());
		}
		
	}
	
	
}
