package com.kaisheng.servlet.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaisheng.entity.Task;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.service.TaskService;
import com.kaisheng.servlet.BaseServlet;
import com.kaisheng.util.AjaxResult;
@WebServlet("/task/edit")
public class TaskEditServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	TaskService service = new TaskService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String taskId = req.getParameter("id");
		Task task = service.findTaskById(taskId);
		req.setAttribute("task", task);
		forward("task/edit", req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String taskId = req.getParameter("taskId");
		String taskName = req.getParameter("newName");
		String finishTime = req.getParameter("newTime");
		
		try {
			service.updateTask(taskId,taskName, finishTime);
			AjaxResult result = AjaxResult.success();
			sendJson(result, resp);
		} catch (ServiceException e) {
			AjaxResult result = AjaxResult.error(e.getMessage());
			sendJson(result, resp);
		}
	}
		
}
