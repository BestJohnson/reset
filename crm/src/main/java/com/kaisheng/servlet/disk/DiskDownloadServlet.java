package com.kaisheng.servlet.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.kaisheng.entity.Disk;
import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.Config;

@WebServlet("/disk/download")
public class DiskDownloadServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diskId = req.getParameter("id");
		String name = req.getParameter("fileName");
		
		if(StringUtils.isNumeric(diskId)) {
			Disk disk = service.findDiskById(Integer.parseInt(diskId));
			
			if(disk == null) {
				resp.sendError(404,"参数异常");
			} else {
				String path = Config.get("file.upload.path");
				InputStream in = new FileInputStream(new File(path,disk.getSaveName()));
				OutputStream out = resp.getOutputStream();
				
				if(StringUtils.isNotEmpty(name)) {    //下载
					//get乱码问题
					name = new String(name.getBytes("ISO8859-1"),"UTF-8");
					//
					name = new String(name.getBytes("UTF-8"),"ISO8859-1");
					resp.setContentType("application/octet-stream");
					//设置文件名
					/*resp.addHeader("Content-Disposition", "attachment; fileName=" + name);*/
					resp.addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
				}
				
				IOUtils.copy(in, out);
				out.flush();
				out.close();
				in.close();
			}
			
		} else {
			resp.sendError(404,"参数异常");
		}
	}
}
