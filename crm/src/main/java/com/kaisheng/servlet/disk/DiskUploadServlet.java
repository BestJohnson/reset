package com.kaisheng.servlet.disk;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.kaisheng.service.DiskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;

@WebServlet("/disk/upload")
@MultipartConfig
public class DiskUploadServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	DiskService service = new DiskService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Part part = req.getPart("file");//此处file跟home.jsp中fileVal处的的file对应
		InputStream in = part.getInputStream();
		
		String name = req.getParameter("name");//part.getSubmittedFileName不可行
		
		long fileSize = part.getSize();
		//从formData中获得pid
		String pid = req.getParameter("pid");
		String md5 = req.getParameter("fileMd5");
		
		try {
			
			int accountId = getCurrAccount(req).getId();
			String saveName = UUID.randomUUID() + name.substring(name.lastIndexOf("."));
			//保存记录到数据库
			service.saveFile(saveName,name,fileSize,pid,accountId,md5);
			//保存文件
			service.upload(in,saveName);
			sendJson(AjaxResult.success(), resp);
		} catch(NullPointerException e) {
			sendJson(AjaxResult.error("登录信息已过期，请重新登录"), resp);
		}
		
	}
}
