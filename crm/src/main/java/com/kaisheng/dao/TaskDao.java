package com.kaisheng.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kaisheng.entity.Task;
import com.kaisheng.util.DbHelp;

public class TaskDao {

	public List<Task> findListByAccountId(int accountId) {
		String sql = "select * from t_task where account_id = ?";
		return DbHelp.query(sql, new BeanListHandler<>(Task.class, new BasicRowProcessor(new GenerousBeanProcessor())), accountId);
	}

	public void save(Task task) {
		String sql = "insert into t_task (title, finish_time, status, account_id) value (?,?,?,?)";
		DbHelp.executeUpdate(sql, task.getTitle(),task.getFinishTime(),task.getStatus(),task.getAccountId());
	}

	public Task findById(String taskId) {
		String sql = "select * from t_task where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Task.class, new BasicRowProcessor(new GenerousBeanProcessor())), taskId);
	}

	public void update(Task task) {
		String sql = "update t_task set title = ?, finish_time = ?, status = ? where id = ?";
		DbHelp.executeUpdate(sql, task.getTitle(), task.getFinishTime(), task.getStatus(), task.getId());
	}

	public List<Task> findListByAccountIdAndStatus(int accountId, int status) {
		String sql = "select * from t_task where account_id = ? and status = ?";
		return DbHelp.query(sql, new BeanListHandler<>(Task.class, new BasicRowProcessor(new GenerousBeanProcessor())), accountId,status);
	}


}
