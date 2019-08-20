package com.ehr;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {
	final static Logger LOG = Logger.getLogger(UserDaoTest.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		//j01_ip : j01_125
		User user01 = new User("j01_125","노명진","1234");
		try {
			int flag = dao.add(user01);
			LOG.debug("=============================");
			LOG.debug("01. add flag="+flag);
			LOG.debug("=============================");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
