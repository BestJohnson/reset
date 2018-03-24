package com.kaisheng.servlet.disk;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Disk;
import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/disk/md5")
public class DiskMd5UploadServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String md5 = req.getParameter("md5");
		String pid = req.getParameter("pid");
		String name = req.getParameter("name");
		
		Disk disk = service.findDiskByMd5(md5);
		if(disk == null) {
			sendJson(AjaxResult.success(), resp);
		} else {
			name = new String(name.getBytes("ISO8859-1"),"UTF-8");
			disk.setName(name);
			disk.setAccountId(getCurrAccount(req).getId());
			disk.setpId(Integer.parseInt(pid));
			
			service.saveDisk(disk);
			sendJson(AjaxResult.error("文件已存在"), resp);
		}
	}
}
