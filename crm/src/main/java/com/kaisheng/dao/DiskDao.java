package com.kaisheng.dao;

import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kaisheng.entity.Disk;
import com.kaisheng.util.DbHelp;

public class DiskDao {

	public List<Disk> findListByPId(int pId) {
		String sql = "select * from t_disk where pid = ? order by type asc";
		return DbHelp.query(sql, new BeanListHandler<>(Disk.class, new BasicRowProcessor(new GenerousBeanProcessor())), pId);
	}

	public Disk findById(int id) {
		String sql = "select * from t_disk where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Disk.class, new BasicRowProcessor(new GenerousBeanProcessor())), id);
	}

	public void saveDir(Disk disk) {
		String sql = "insert into t_disk (name, account_id, pid, type, save_name, file_size, md5) values (?,?,?,?,?,?,?)";
		DbHelp.executeUpdate(sql, disk.getName(),disk.getAccountId(),disk.getpId(),disk.getType(),disk.getSaveName(),disk.getFileSize(),disk.getMd5());
	}

	public void update(Disk disk) {
		String sql = "update t_disk set name = ? where id = ?";
		DbHelp.executeUpdate(sql, disk.getName(),disk.getId());
	}

	public void deleteDiskById(int id) {
		String sql = "delete from t_disk where id = ?";
		DbHelp.executeUpdate(sql, id);
	}

	public Disk findByMd5(String md5) {
		String sql = "select * from t_disk where md5 = ?";
		return DbHelp.query(sql, new BeanHandler<>(Disk.class, new BasicRowProcessor(new GenerousBeanProcessor())), md5);
	}


}
