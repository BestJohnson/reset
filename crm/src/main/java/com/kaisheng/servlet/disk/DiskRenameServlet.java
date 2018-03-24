package com.kaisheng.servlet.disk;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/disk/rename")
public class DiskRenameServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rename = req.getParameter("rename");
		String diskId = req.getParameter("diskId");
		service.updateDisk(rename,diskId);
		
		sendJson(AjaxResult.success(), resp);
	}
}
