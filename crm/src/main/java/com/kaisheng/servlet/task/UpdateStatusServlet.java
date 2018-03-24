package com.kaisheng.servlet.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.service.TaskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/task/updatestatus")
public class UpdateStatusServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	TaskService service = new TaskService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String taskId = req.getParameter("taskId");
		String status = req.getParameter("status");
		service.updateTaskById(taskId, status);
		
		AjaxResult result = AjaxResult.success();
		sendJson(result, resp);
	}
		
}
