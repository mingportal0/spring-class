/**
 * @Class Name : UserDao.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019-08-19           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2019-08-19 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by H.R. KIM All right reserved.
 */
package com.ehr;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UserDao {
	final static Logger LOG = Logger.getLogger(UserDao.class);
	private ConnectionMaker connectionMaker;
	public UserDao() {
		//connectionMaker = new DUserDao();
	}
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}


	public int add(User user)throws ClassNotFoundException, SQLException{
		int flag = 0;
		
		Connection conn = connectionMaker.makeConnection();
		
		LOG.debug("========================================");
		LOG.debug(conn+" 연결 완료");
		LOG.debug("========================================");
		
		LOG.debug("========================================");
		LOG.debug(user+" 추가 완료");
		LOG.debug("========================================");
		
		flag = 1;
		return flag;
	}
	
}
