package com.kaisheng.servlet.charts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.servlet.BaseServlet;
@WebServlet("/charts/level")
public class ChartsLevelServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward("charts/level", req, resp);
	}
	
		
		
}
