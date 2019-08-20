package com.ehr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao() {
		//1.UserDao가 사용할 ConnectionMaker로 결정
		//ConnectionMaker connectionMaker = new DUserDao();
		
		//2.UserDao 생성
		//2.1.사용할 ConnectionMaker 타입의 Object를 제공
		//2.2.UserDao와 ConnectionMaker의 의존 관계 결정
		UserDao dao = new UserDao(connectionMaker());
		
		return dao;		
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		//1.UserDao가 사용할 ConnectionMaker로 결정
		return new DUserDao();
	}
}
