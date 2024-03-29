package com.ehr;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContextAroundPerform.xml")
public class AspectJPerformTest {
	private final Logger LOG = Logger.getLogger(AspectJPerformTest.class);
	
	@Autowired
	ApplicationContext context;
	
	@Before
	public void setUp() {
		LOG.debug("context:"+context);
	}
	
	@Test
	public void apectTest() {
		CommonDao dao = (CommonDao) context.getBean("memberDaoImpl");
		LOG.debug("dao:"+dao);
		
		dao.do_save();
		dao.selectOne();
		dao.do_retrieve(10);
	}
}
