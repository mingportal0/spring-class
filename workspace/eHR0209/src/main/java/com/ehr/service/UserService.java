package com.ehr.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ehr.Level;
import com.ehr.User;
import com.ehr.UserDao;

public class UserService {
	public static final int MIN_LOGIN_COUNT_FOR_SILVER = 0;
	public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 0;

	final Logger LOG = Logger.getLogger(UserService.class);

	private DataSource dataSource;
	private UserDao userDao;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//회원가입
	//u_id : 유저 입력
	//name : 유저 입력
	//passwd : 유저 입력
	//h_level : BASIC
	//login : 0
	//recommend : 0
	//email : 유저 입력
	//reg_dt : NULL
	public void add(User user) {
		if(user.gethLevel()==null) {
			user.sethLevel(Level.BASIC);			
		}
		userDao.add(user);
	}
	//level upgrade
	public void upgradeLevels() throws SQLException {
		//1. 트랜잭션 동기화 관리자를 초기화
		
		//2. 트랜잭션 : Connection.setAutoCommit(false)
		
		//3. 성공 시 : Connection.commit();
		
		//3. 실패 시 : Connection.rollback();
		
		//4. 자원반납
		
		int upCnt = 0;
		//트랜잭션 동기화 : 초기화
		TransactionSynchronizationManager.initSynchronization();
		Connection c = DataSourceUtils.getConnection(dataSource);
		c.setAutoCommit(false);
		//1. 전체 사용자를 조회
		List<User> users = userDao.getAll();
		try {
			for(User user : users) {
				if(canUpgradeLevel(user)==true) {
					upgradeLevel(user);
					upCnt++;
				}
			}//--for
			c.commit();
		}catch(Exception e) {
			c.rollback();
			LOG.debug("---------------------------------------------------------------");
			LOG.debug("rollback c="+c);
			LOG.debug("---------------------------------------------------------------");
			throw e;
		}finally {
			//자원반납
			DataSourceUtils.releaseConnection(c, dataSource);
			//동기화 종료
			TransactionSynchronizationManager.unbindResource(dataSource);
			TransactionSynchronizationManager.clearSynchronization();
		}
		LOG.debug("---------------------------------------------------------------");
		LOG.debug("upCnt = "+upCnt);
		LOG.debug("---------------------------------------------------------------");
	}
	protected void upgradeLevel(User user) {
//		if(user.gethLevel() == Level.BASIC) {
//			user.sethLevel(Level.SILVER);
//		}else if(user.gethLevel() == Level.SILVER) {
//			user.sethLevel(Level.GOLD);
//		}
		user.upgradeLevel();
		userDao.update(user);
	}
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.gethLevel();
		switch(currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
			case GOLD : return false;
			default: throw new IllegalArgumentException("Unknown Level"+user.gethLevel());
		}
	}
	
//	//level upgrade
//		public void upgradeLevels() {
//			//1. 전체 사용자를 조회
//			List<User> users = userDao.getAll();
//			int upCnt = 0;
//			for(User user : users) {
//				//2. 대상자를 선별
//				Boolean changed = null;
//				//2.1. Basic사용자, 로그인 cnt 50 이상 이면 : Basic -> Silver
//				if(user.gethLevel() == Level.BASIC && user.getLogin() >= 50) {
//					user.sethLevel(Level.SILVER);
//					changed = true;
//				}
//				//2.2. Silver사용자, 추천 cnt 30 이상 이면 : Silver -> Gold
//				else if(user.gethLevel() == Level.SILVER && user.getRecommend() >= 30) {
//					user.sethLevel(Level.GOLD);
//					changed = true;
//				}
//				//2.3. Gold는 대상 아님
//				else if(user.gethLevel() == Level.GOLD) {
//					changed = false;
//				}
//				//Others
//				else {
//					changed = false;
//				}
//				//3. 대상자 업그레이드 레벨 선정 및 upgrade
//				if(changed == true) {
//					userDao.update(user);
//					upCnt++;
//				}
//			}//--for
//			LOG.debug("---------------------------------------------------------------");
//			LOG.debug("upCnt = "+upCnt);
//			LOG.debug("---------------------------------------------------------------");
//		}

}
