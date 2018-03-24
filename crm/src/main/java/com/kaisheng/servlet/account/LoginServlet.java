package com.kaisheng.servlet.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaisheng.entity.Account;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.AccountService;
import com.kaisheng.servlet.BaseServlet;

@WebServlet("/login")
public class LoginServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	//使用log4j的写法
//	Logger logger = Logger.getLogger(LoginServlet.class);
	
	//使用logback时的写法
	Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies) {
			if("admin".equals(cookie.getName())) {
				String[] str = cookie.getValue().split(",");
				req.setAttribute("username", str[0]);
				req.setAttribute("password", str[1]);
			}
		}
		
		forward("account/login", req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
//		logger.info(username + "登录了系统");
		
		//logback的写法
		logger.debug("{}登陆了系统",username);
		
		Map<String,Object> map = new HashMap<>();
		AccountService service = new AccountService();
		try {
			Account acc = service.login(username,password);
			
			HttpSession session = req.getSession();
			session.setAttribute("account", acc);
			
			if(StringUtils.isNotEmpty(remember)) {
				Cookie cookie = new Cookie("admin",username + "," + password);
				cookie.setDomain("192.168.1.182");
				cookie.setPath("/");
				cookie.setMaxAge(60 * 60 * 24 * 30);
				cookie.setHttpOnly(true);
				
				resp.addCookie(cookie);
			} else {
				Cookie[] cookies = req.getCookies();
				for(Cookie cookie : cookies) {
					if("admin".equals(cookie.getName())) {
						cookie.setDomain("localhost");
						cookie.setPath("/");
						cookie.setMaxAge(0);
						
						resp.addCookie(cookie);
					}
				}
			}
			
			map.put("state", "success");
			sendJson(map, resp);
		} catch(ServiceException e) {
			map.put("state", "error");
			map.put("message",e.getMessage());
			sendJson(map, resp);
		}
	
	}
	
}
