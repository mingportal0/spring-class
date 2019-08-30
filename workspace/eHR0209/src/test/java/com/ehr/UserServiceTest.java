package com.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static com.ehr.service.UserService.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.ehr.service.UserService.MIN_RECOMMEND_COUNT_FOR_GOLD;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ehr.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
	final Logger LOG = Logger.getLogger(UserServiceTest.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	DataSource dataSource;
	
	List<User> users;
	
	//대상 Data 생성
	@Before
	public void serUp() {
		users = Arrays.asList(
					new User("j01_125","노명진01","1234",Level.BASIC,MIN_LOGIN_COUNT_FOR_SILVER-1,0,"nmjgo7321@naver.com","2019/08/23"),
					new User("j02_125","노명진02","1234",Level.BASIC,MIN_LOGIN_COUNT_FOR_SILVER,0,"nmjgo7321@naver.com","2019/08/23"), //Basic -> Silver
					new User("j03_125","노명진03","1234",Level.SILVER,60,MIN_RECOMMEND_COUNT_FOR_GOLD-1,"nmjgo7321@naver.com","2019/08/23"),
					new User("j04_125","노명진04","1234",Level.SILVER,60,MIN_RECOMMEND_COUNT_FOR_GOLD,"nmjgo7321@naver.com","2019/08/23"), //Silver -> Gold
					new User("j05_125","노명진05","1234",Level.GOLD,99,99,"nmjgo7321@naver.com","2019/08/23")
				);
	}
	
	@Test
	@Ignore
	public void add() {
		//1. 전체 삭제
		userDao.deleteAll();
		//2. Level
		User userExistsLevel = users.get(4);
		//3. Level ==null 
		User userNoLevel = users.get(0);
		userNoLevel.sethLevel(null);
		//4. update
		this.userService.add(userExistsLevel);
		this.userService.add(userNoLevel);
		
		User userExistsLevelRead = userDao.get(userExistsLevel.getU_id());
		User userNoLevelRead = userDao.get(userNoLevel.getU_id());
		
		assertThat(userExistsLevelRead.gethLevel(), is(userExistsLevel.gethLevel()));
		assertThat(userNoLevelRead.gethLevel(), is(Level.BASIC));
	}
	
	@Test
	public void upgradeLevels() throws SQLException {
		//1. 전체 삭제
		userDao.deleteAll();
		//2. user 등록
		for(User user : users) {
			userDao.add(user);
		}
		//3. upgradeLevels call
		this.userService.upgradeLevels();
		//4. 2,4 upgrade 대상
		checkUser(users.get(0),Level.BASIC);
		checkUser(users.get(1),Level.SILVER);
		checkUser(users.get(2),Level.SILVER);
		checkUser(users.get(3),Level.GOLD);
		checkUser(users.get(4),Level.GOLD);
	}
	
	private void checkUser(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getU_id());
		
		assertThat(userUpdate.gethLevel(), is(expectedLevel));
	}
	
	@Test
	public void serviceBean() {
		//assertThat(this.userService, is(notNullValue()));
		//assertThat(this.userDao, is(notNullValue()));
		//assertThat(this.dataSource, is(notNullValue()));
		LOG.info("-------------------------------------------------------");
		LOG.info("userService : "+userService);
		LOG.info("userDao : "+userDao);
		LOG.info("dataSource : "+dataSource);
		LOG.info("-------------------------------------------------------");
	}
	
	@Test
	public void upgradeAllOrNothing() throws SQLException {
		TestUserService testUserService = new TestUserService(users.get(3).getU_id());
		testUserService.setUserDao(userDao); //userDao를 수동으로 DI
		testUserService.setDataSource(dataSource);
		//전체 삭제
		userDao.deleteAll();
		//users 입력
		for(User user : users) {
			userDao.add(user);
		}
		try{
			testUserService.upgradeLevels();
			
		}catch(TestUserServiceException e) {
			LOG.info("-------------------------------------------------------");
			LOG.info("TestUserServiceException");
			LOG.info("-------------------------------------------------------");
		}
		checkUser(users.get(1), Level.BASIC);
	}
}

class TestUserService extends UserService{
	final Logger LOG = Logger.getLogger(UserServiceTest.class);
	private String id;
	
	public TestUserService(String id) {
		this.id = id;
	}
	public void upgradeLevel(User user) {
		if(user.getU_id().equals(id)) {
			throw new TestUserServiceException();
		}
		super.upgradeLevel(user);
	}
}

class TestUserServiceException extends RuntimeException{
	final Logger LOG = Logger.getLogger(UserServiceTest.class);
	
	public TestUserServiceException() {
		LOG.info("-------------------------------------------------------");
		LOG.info("TestUserServiceException");
		LOG.info("-------------------------------------------------------");
	}
}