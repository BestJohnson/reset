package com.kaisheng.servlet.dept;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Dept;
import com.kaisheng.service.DeptService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/dept/list")
public class DeptListServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	DeptService service = new DeptService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Dept> list = service.findAllDept();
		sendJson(list, resp);
	
	}
	
}
