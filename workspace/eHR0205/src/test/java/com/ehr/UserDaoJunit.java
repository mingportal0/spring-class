package com.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoJunit {
	private Logger LOG=Logger.getLogger(UserDaoJunit.class);
	@Autowired
	ApplicationContext context;
	
	private UserDao dao;
	private User user01;
	private User user02;
	private User user03;
	
	private SearchVO search01;
	private SearchVO search02;
	private SearchVO search03;
	@Before
	public void setUp() {
		LOG.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		LOG.info("0 setUp()=");
		LOG.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		user01=new User("j01_125","노명진01","1234");
		user02=new User("j02_125","노명진02","1234");
		user03=new User("j03_125","노명진03","1234");
		
		search01 = new SearchVO(10,1,"10","12");
		search02 = new SearchVO(10,1,"20","명진");
		search03 = new SearchVO(10,1,"","");
		
		dao = context.getBean("userDao", UserDao.class);
		LOG.info("==============================");
		LOG.info("=context="+context);
		LOG.info("=dao="+dao);
		LOG.info("==============================");
	}
	
	@After
	public void tearDown() {
		LOG.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		LOG.info("99 tearDown()=");
		LOG.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
	@Test
	public void pageAll() throws ClassNotFoundException, SQLException {
		List<User> list = dao.pageAll(search01);
		for(User user:list) {
			LOG.info(user);
		}
		
	}
	
	@Test
	@Ignore
	public void getAll() throws ClassNotFoundException, SQLException  {
		List<User> list = dao.getAll();
		for(User user:list) {
			LOG.info(user);
		}
		
	}
	@Ignore
	@Test(expected = EmptyResultDataAccessException.class)
	public void getFailure() throws ClassNotFoundException, SQLException {
		LOG.info("-------------------------------------------");
		LOG.info("=01 getFailure()=");
		LOG.info("-------------------------------------------");
		dao.deleteUser(user01);
		dao.deleteUser(user02);
		dao.deleteUser(user03);
		
		assertThat(dao.count("_125"), is(0));
		
		dao.get("unknownUserId");
	}
	
	@Test
	@Ignore
	public void count() throws ClassNotFoundException, SQLException {
		LOG.info("-------------------------------------------");
		LOG.info("=01 count()=");
		LOG.info("-------------------------------------------");
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
	
	@Test(timeout = 5000) //1.JUnit에게 테스트용 메소드임을 알려줌
	@Ignore
	public void addAndGet() {//2. public
		LOG.info("-------------------------------------------");
		LOG.info("=01 addAndGet()=");
		LOG.info("-------------------------------------------");
		
		LOG.info("==============================");
		LOG.info("=01 삭제=");
		LOG.info("==============================");
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
