package com.ehr;

public class DaoFactory {
	/*
	 * UserDao.java
	   BoardDao.java
	 */
	//Factory : 객체의 생성 방법을 결정하고 return
	public UserDao userDao() {
		//1.UserDao가 사용할 ConnectionMaker로 결정
		//ConnectionMaker connectionMaker = new DUserDao();
		
		//2.UserDao 생성
		//2.1.사용할 ConnectionMaker 타입의 Object를 제공
		//2.2.UserDao와 ConnectionMaker의 의존 관계 결정
		UserDao dao = new UserDao(connectionMaker());
		
		return dao;		
	}
	
	public ConnectionMaker connectionMaker() {
		//1.UserDao가 사용할 ConnectionMaker로 결정
		return new DUserDao();
	}
}
