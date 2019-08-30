package com.ehr.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.ehr.Level;
import com.ehr.User;
import com.ehr.UserDao;

public class UserService {
	final Logger LOG = Logger.getLogger(UserService.class);

	private UserDao userDao;

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
	public void upgradeLevels() {
		int upCnt = 0;
		//1. 전체 사용자를 조회
		List<User> users = userDao.getAll();
		for(User user : users) {
			if(canUpgradeLevel(user)==true) {
				upgradeLevel(user);
				upCnt++;
			}
		}//--for
		LOG.debug("---------------------------------------------------------------");
		LOG.debug("upCnt = "+upCnt);
		LOG.debug("---------------------------------------------------------------");
	}
	private void upgradeLevel(User user) {
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
			case BASIC: return (user.getLogin() >= 50);
			case SILVER: return (user.getRecommend() >= 30);
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
