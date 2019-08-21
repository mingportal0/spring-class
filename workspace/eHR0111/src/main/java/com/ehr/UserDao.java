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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import org.apache.log4j.Logger;

/**
 * @author sist
 *
 */
public class UserDao {

	static final Logger LOG= Logger.getLogger(UserDao.class);
	private DataSource dataSource;

	public UserDao() {
		
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	/**
	 * 
	 * @Method Name  : add
	 * @작성일   : 2019. 8. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 : 단건등록
	 * @param user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int add(User user)throws ClassNotFoundException,SQLException{
		
		//----------------------------------------
		//01.DB 연결
		//----------------------------------------		
		Connection  c = dataSource.getConnection();
		LOG.debug("=============================");
		LOG.debug("01. Connection="+c);
		LOG.debug("=============================");		
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" INSERT INTO users (  \n");
		sb.append("     u_id,            \n");
		sb.append("     name,            \n");
		sb.append("     passwd           \n");
		sb.append(" ) VALUES (           \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?                \n");
		sb.append(" )                    \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ps.setString(1, user.getU_id());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPasswd());
		LOG.debug("=============================");
		LOG.debug("03. param="+user);
		LOG.debug("=============================");			
		
		//----------------------------------------
		//04.Run sql
		//----------------------------------------	
		int flag = ps.executeUpdate();
		LOG.debug("=============================");
		LOG.debug("04. Run flag="+flag);
		LOG.debug("=============================");		
		
		//----------------------------------------
		//05.자원반납
		//----------------------------------------	
		ps.close();
		c.close();
		
		return flag;
		
	}
	
	/**
	 * 
	 * @Method Name  : get
	 * @작성일   : 2019. 8. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 :단건조회
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User get(String id)throws ClassNotFoundException,SQLException{
		User outVO=null;
		//----------------------------------------
		//01.DB 연결
		//----------------------------------------		
		Connection  c = dataSource.getConnection();
		LOG.debug("=============================");
		LOG.debug("01. Connection="+c);
		LOG.debug("=============================");		
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT          \n");
		sb.append("     u_id,       \n");
		sb.append("     name,       \n");
		sb.append("     passwd      \n");
		sb.append(" FROM users      \n");
		sb.append(" WHERE u_id = ?  \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ps.setString(1, id);
		LOG.debug("=============================");
		LOG.debug("03. id="+id);
		LOG.debug("=============================");			
		
		//----------------------------------------
		//04.Run sql
		//----------------------------------------	
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			outVO=new User();
			outVO.setU_id(rs.getString("u_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPasswd(rs.getString("passwd"));
		}
		LOG.debug("=============================");
		LOG.debug("04. Run outVO="+outVO);
		LOG.debug("=============================");		
		
		//----------------------------------------
		//05.자원반납
		//----------------------------------------
		rs.close();
		ps.close();
		c.close();
		
		return outVO;
	}	
	


}
