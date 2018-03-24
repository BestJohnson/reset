package com.kaisheng.servlet.disk;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.entity.Disk;
import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/disk/home")
public class DiskServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		int pId = 0;
		if(StringUtils.isNumeric(pid)) {
			pId = Integer.parseInt(pid);
		}
		 List<Disk> diskList = service.findFileListByPId(pId);
		 req.setAttribute("diskList", diskList);
		
		 if(pId != 0) {
			 Disk disk = service.findDiskById(pId);
			 req.setAttribute("disk", disk);
		 }
		forward("disk/home", req, resp);
	}
}
