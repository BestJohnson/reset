package com.kaisheng.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.kaisheng.dao.DiskDao;
import com.kaisheng.entity.Disk;
import com.kaisheng.exception.ServiceException;
import com.kaisheng.util.Config;

public class DiskService {

	DiskDao dao = new DiskDao();
	/**
	 * 根据pid查找该层级下的的文件集合
	 * @param pId
	 * @return
	 */
	public List<Disk> findFileListByPId(int pId) {
		return dao.findListByPId(pId);
	}
	/**
	 * 根据id查对应的文件夹对象
	 * @param id
	 * @return
	 */
	public Disk findDiskById(int id) {
		return dao.findById(id);
	}
	/**
	 * 新增文件夹
	 * @param name
	 * @param accountId
	 * @param pId
	 */
	public void saveFolder(String name, int accountId, int pId) {
		Disk disk = new Disk();
		disk.setName(name);
		disk.setpId(pId);
		disk.setAccountId(accountId);
		disk.setType(Disk.DISK_TYPE_FOLDER);
		
		dao.saveDir(disk);
	}
	/**
	 * 保存记录到数据库
	 * @param in 输入流
	 * @param name
	 * @param fileSize
	 * @param pid
	 * @param accountId
	 */
	public void saveFile(String saveName, String name, long fileSize, String pid, int accountId,String md5) {
		Disk disk = new Disk();
		disk.setName(name);
		disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));
		disk.setpId(Integer.parseInt(pid));
		disk.setAccountId(accountId);
		disk.setType(Disk.DISK_TYPE_FILE);
		disk.setMd5(md5);
		disk.setSaveName(saveName);
		
		dao.saveDir(disk);
		
	}
	
	
	/**
	 * 保存文件
	 * @param in
	 * @param saveName
	 */
	public void upload(InputStream in, String saveName) {
		try {
			String path = Config.get("file.upload.path");
			OutputStream out = new FileOutputStream(new File(path,saveName));
			IOUtils.copy(in, out);
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			throw new ServiceException("上传失败");
		}
		
	}
	
	
	/**
	 * 重命名
	 * @param rename
	 * @param diskId
	 */
	public void updateDisk(String rename, String diskId) {
		Disk disk = dao.findById(Integer.parseInt(diskId));
		if(disk == null){
			throw new ServiceException("参数异常");
		}
		disk.setName(rename);
		dao.update(disk);
	}
	/**
	 * 删除文件夹
	 * @param disk
	 */
	public void del(Disk disk) {
		deleteDisk(disk);
	}
	
	
	/**
	 * 有多层文件夹时的递归删除
	 * @param disk
	 */
	private void deleteDisk(Disk disk) {
		List<Disk> list = new ArrayList<>();
		list = dao.findListByPId(disk.getId());
		
		for(Disk disk1 : list) {
			deleteDisk(disk1);
		}
		
		//删除数据库
		dao.deleteDiskById(disk.getId());
		//删除文件
		if("file".equals(disk.getType())) {
			File file = new File(Config.get("file.upload.path"),disk.getSaveName());
			if(file.exists()) {
				file.delete();
			}
		}
	}
	/**
	 * 根据md5查找文件
	 * @param md5
	 * @return
	 */
	public Disk findDiskByMd5(String md5) {
		return dao.findByMd5(md5);
	}
	
	
	/**
	 * 
	 * @param disk
	 */
	public void saveDisk(Disk disk) {
		dao.saveDir(disk);
	}

}
