package com.ehr;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UserDaoTest {
	final static Logger LOG = Logger.getLogger(UserDaoTest.class);
	
	public static void main(String[] args) {
		LOG.debug("---------------------");
		LOG.debug("Main");
		LOG.debug("---------------------");
		
		
		
		UserDao dao = new DaoFactory().userDao();
		
		//j01_ip : j01_125
		User user01 = new User("j01_125","노명진","1234");
		try {
			LOG.debug("=============================");
			LOG.debug("단건 등록");
			LOG.debug("=============================");
			
			int flag = dao.add(user01);
			LOG.debug("=============================");
			LOG.debug("01. add flag="+flag);
			LOG.debug("=============================");
			
			if(flag==1) {
				LOG.debug("=============================");
				LOG.debug("등록 성공"+user01.getU_id());
				LOG.debug("=============================");
			}
			LOG.debug("=============================");
			LOG.debug("단건 조회");
			LOG.debug("=============================");
			
			User output = dao.get(user01.getU_id());
			if(user01.getU_id().equals(output.getU_id())
			&& user01.getName().equals(output.getName())
			&& user01.getPasswd().equals(output.getPasswd())					
					) {
				LOG.debug("=============================");
				LOG.debug("조회 성공"+output);
				LOG.debug("=============================");
			}else {
				LOG.debug("=============================");
				LOG.debug("조회 실패"+output);
				LOG.debug("=============================");
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
