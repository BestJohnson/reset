package com.kaisheng.servlet.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.entity.Account;
import com.kaisheng.service.AccountService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
import com.kaisheng.util.Page;

@WebServlet("/account/list.json")
public class AccountListJsonServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	
	AccountService service = new AccountService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String deptId = req.getParameter("deptId");
		String p = req.getParameter("p");
		
		
		
		int pageNo = 1;
		if(StringUtils.isNumeric(p)) {
			pageNo = Integer.parseInt(p);
		}
		
		Page<Account> pages = service.findAccountByPage(deptId,pageNo);
		AjaxResult result = AjaxResult.success(pages);
		sendJson(result, resp);
	}
}
