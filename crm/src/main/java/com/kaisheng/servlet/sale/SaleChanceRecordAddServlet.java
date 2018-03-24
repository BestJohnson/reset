package com.kaisheng.servlet.sale;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.SaleChanceRecord;
import com.kaisheng.service.SaleService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/sale/add/record")
public class SaleChanceRecordAddServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	SaleService service = new SaleService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saleId = req.getParameter("saleId");
		String content = req.getParameter("content");
		
		int accountId = getCurrAccount(req).getId();
		
		SaleChanceRecord record = new SaleChanceRecord();
		record.setContent(content);
		record.setSaleId(Integer.parseInt(saleId));
		
		service.addRecord(record,accountId);
		AjaxResult result = AjaxResult.success();
		sendJson(result, resp);
	
	
	}
}
