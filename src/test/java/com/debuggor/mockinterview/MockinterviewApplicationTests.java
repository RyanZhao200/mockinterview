package com.debuggor.mockinterview;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.dao.AdminDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockinterviewApplicationTests {

	@Autowired
	AdminDao adminDao;

	@Test
	public void testAdmin(){
		Admin admin = adminDao.getAdminByUserName("admin");
		System.out.println(admin.getPassword());
	}

	@Test
	public void contextLoads() {
	}

}
