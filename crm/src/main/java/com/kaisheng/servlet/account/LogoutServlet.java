package com.kaisheng.servlet.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.servlet.BaseServlet;
@WebServlet("/account/logout")
public class LogoutServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//强制过期
		req.getSession().invalidate();
		
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies) {
			if("admin".equals(cookie.getName())) {
				String[] str = cookie.getValue().split(",");
				req.setAttribute("username", str[0]);
				req.setAttribute("password", str[1]);
			}
		}
	
		forward("/account/login", req, resp);
		
	}
	
	
}
