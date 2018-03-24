package test.service;

import org.junit.Test;

import com.kaisheng.dao.AccountDao;
import com.kaisheng.entity.Account;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class AccountServiceTestCase {

	AccountDao dao = new AccountDao();
	
	@Test
	public void findByIdTest() {
		CacheManager cacheManager = new CacheManager();
		Ehcache cache = cacheManager.getEhcache("account");
		Account acc = dao.findById(1);
		Element ele = new Element(acc.getId(),acc);
		
		cache.put(ele);
	}
	
	
}
