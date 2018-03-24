package com.kaisheng.servlet.sale;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.entity.SaleChance;
import com.kaisheng.service.SaleService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.Page;
@WebServlet("/sale/my/list")
public class MySaleChanceListServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	SaleService service = new SaleService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String p = req.getParameter("p");
		int pageNo = 1;
		
		if(StringUtils.isNumeric(p)) {
			pageNo = Integer.parseInt(p);
		}
		int accountId = getCurrAccount(req).getId();
		
		Page<SaleChance> page = service.findSaleListByPage(pageNo,accountId);
		req.setAttribute("page", page);
		
		forward("sale/mylist", req, resp);
	}
}
