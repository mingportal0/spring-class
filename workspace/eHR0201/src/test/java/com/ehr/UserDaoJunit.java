package com.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDaoJunit {

	private Logger LOG=Logger.getLogger(UserDaoJunit.class);
	
	@Test(expected = EmptyResultDataAccessException.class)	
	public void getFailure() throws ClassNotFoundException, SQLException {
		//Spring IoC로 객체 생성
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user01=new User("j01_125","노명진01","1234");
		User user02=new User("j02_125","노명진02","1234");
		User user03=new User("j03_125","노명진03","1234");
		
		//----------------------------------------------------------------------------------------------
		//삭제
		//----------------------------------------------------------------------------------------------
		dao.deleteUser(user01);
		dao.deleteUser(user02);
		dao.deleteUser(user03);
		
		assertThat(dao.count("_125"), is(0));
		
		dao.get("unknownUserId");
	}
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
		//Spring IoC로 객체 생성
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user01=new User("j01_125","노명진01","1234");
		User user02=new User("j02_125","노명진02","1234");
		User user03=new User("j03_125","노명진03","1234");
		
		//----------------------------------------------------------------------------------------------
		//삭제
		//----------------------------------------------------------------------------------------------
		dao.deleteUser(user01);
		dao.deleteUser(user02);
		dao.deleteUser(user03);
		
		assertThat(dao.count("_125"), is(0));
		//----------------------------------------------------------------------------------------------
		//1건추가
		//----------------------------------------------------------------------------------------------
		dao.add(user01);
		//----------------------------------------------------------------------------------------------
		//count:1
		//----------------------------------------------------------------------------------------------
		assertThat(dao.count("_125"), is(1));
		//----------------------------------------------------------------------------------------------
		//1건추가
		//----------------------------------------------------------------------------------------------
		dao.add(user02);
		//----------------------------------------------------------------------------------------------
		//count:2
		//----------------------------------------------------------------------------------------------
		assertThat(dao.count("_125"), is(2));
		//----------------------------------------------------------------------------------------------
		//1건추가
		//----------------------------------------------------------------------------------------------
		dao.add(user03);
		//----------------------------------------------------------------------------------------------
		//count:3
		//----------------------------------------------------------------------------------------------
		assertThat(dao.count("_125"), is(3));
		
	}
	
	@Test(timeout = 1000) //1.JUnit에게 테스트용 메소드임을 알려줌
	public void addAndGet() {//2. public
		//Spring IoC로 객체 생성
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		LOG.info("==============================");
		LOG.info("=01 삭제=");
		LOG.info("==============================");
		//j01_ip: j01_125
		User user01=new User("j01_125","노명진01","1234");
		User user02=new User("j02_125","노명진02","1234");
		User user03=new User("j03_125","노명진03","1234");
		
		try {
			dao.deleteUser(user01);
			dao.deleteUser(user02);
			dao.deleteUser(user03);
			
			assertThat(dao.count("_125"), is(0));
			
			LOG.info("==============================");
			LOG.info("=02 단건등록=");
			LOG.info("==============================");			
			
			int flag = dao.add(user01);
			flag = dao.add(user02);
			flag = dao.add(user03);
			assertThat(dao.count("_125"), is(3));
			LOG.info("==============================");
			LOG.info("=02.01 add flag="+flag);
			LOG.info("==============================");
			
			assertThat(flag,is(1));
			
			LOG.info("==============================");
			LOG.info("=03 단건조회");
			LOG.info("==============================");			
			
			User  userOne = dao.get(user01.getU_id());
			
			assertThat(userOne.getU_id(), is(user01.getU_id()));
			assertThat(userOne.getName(), is(user01.getName()));
			assertThat(userOne.getPasswd(), is(user01.getPasswd()));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
