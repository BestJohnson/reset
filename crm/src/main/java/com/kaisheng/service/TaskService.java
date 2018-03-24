package com.kaisheng.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kaisheng.dao.TaskDao;
import com.kaisheng.entity.Task;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;

public class TaskService {

	TaskDao dao = new TaskDao();
	/**
	 * 根据当前accountId获取对应的task列表
	 * @param accountId
	 * @return
	 */
	public List<Task> findTaskListByAccountId(int accountId, String show) {
		if(StringUtils.isNotEmpty(show)) {
			if(show.equals("undone"))  {
				int status = Config.TASK_STATUS_UNDONE;
				return dao.findListByAccountIdAndStatus(accountId,status);
			}
		}
		return dao.findListByAccountId(accountId);
	}
	
	/**
	 * 新增任务
	 * @param taskName
	 * @param finishTime
	 * @param accountId
	 */
	public void saveTask(String taskName, String finishTime, int accountId) {
		Task task = new Task();
		task.setTitle(taskName);
		task.setStatus(Config.TASK_STATUS_UNDONE);
		task.setAccountId(accountId);
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date finishDate = format.parse(finishTime);
			task.setFinishTime(finishDate);
			dao.save(task);
		} catch (ParseException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	/**
	 * 根据taskId修改对应task的status
	 * @param taskId
	 * @param status
	 */
	public void updateTaskById(String taskId, String status) {
		Task task = dao.findById(taskId);
		task.setStatus(Integer.parseInt(status));
		dao.update(task);
	}

	/**
	 * 根据id找task
	 * @param taskId
	 * @return
	 */
	public Task findTaskById(String taskId) {
		return dao.findById(taskId);
	}

	/**
	 * 修改task
	 * @param taskName
	 * @param finishTime
	 * @param accountId
	 */
	public void updateTask(String taskId,String taskName, String finishTime) {
		Task task = dao.findById(taskId);
		if(task == null) {
			throw new ServiceException("参数异常");
		} else {
			task.setTitle(taskName);
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date finishDate = format.parse(finishTime);
				task.setFinishTime(finishDate);
				dao.update(task);
			} catch (ParseException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}


}
