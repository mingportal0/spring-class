package com.ehr;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoJunitContext {
	final Logger LOG = Logger.getLogger(UserDaoJunitContext.class);
	@Autowired
	ApplicationContext context;
	//----------------------------------
	//@Before setUp()
	//----------------------------------
	@Before
	public void setUp() {
		LOG.info("context : "+context);
		LOG.info("UserDaoJunitContext : "+this);
		LOG.info("01 setUp()");
	}
	//----------------------------------
	//@Test
	//----------------------------------
	@Test
	public void count() {
		LOG.info("02 count()");
	}
	
	//----------------------------------
	//@Test
	//----------------------------------
	@Test
	public void addAndGet() {
		LOG.info("02 addAndGet()");
	}
	//----------------------------------
	//@After dearDown()
	//----------------------------------
	@After
	public void tearDown() {
		LOG.info("03 tearDown()");
	}
}
