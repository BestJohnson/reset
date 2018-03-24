package test.dao;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.kaisheng.dao.AccountDao;
import com.kaisheng.entity.Account;

import junit.framework.Assert;

public class AccountTestCase {

	AccountDao dao = new AccountDao();
	
	@Before
	public void first() {
		System.out.println("先出来这句话吗？");
	}
	
	
	@Test
	public void findById() {
		Account acc = dao.findById(2);
		Assert.assertNotNull(acc);
	}
	
	@Test
	public void saveTest() {
		Account acc = new Account();
		acc.setUsername("达令");
		acc.setPassword("123123");
		acc.setTel("110");
		acc.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		dao.save(acc);
	}
	
	
	
}
