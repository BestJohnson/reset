package com.kaisheng.servlet.disk;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Disk;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/disk/del")
public class DiskDeleteServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diskId = req.getParameter("id");
		Disk disk = service.findDiskById(Integer.parseInt(diskId));
		if(disk == null) {
			resp.sendError(404,"参数异常");
		} else {
			service.del(disk);
			try{
				resp.sendRedirect("/disk/home");
			} catch(ServiceException e) {
				resp.sendError(404,"删除失败");
			}
		}
		
	}
}
