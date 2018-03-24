package com.kaisheng.servlet.disk;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/disk/add")
public class DiskAddFolderServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		String name = req.getParameter("name");
		
		int pId = 0;
		if(StringUtils.isNumeric(pid)) {
			pId = Integer.parseInt(pid);
		}
		
		int accountId = getCurrAccount(req).getId();
		service.saveFolder(name, accountId, pId);
		
		sendJson(AjaxResult.success(), resp);
		
	}
}
