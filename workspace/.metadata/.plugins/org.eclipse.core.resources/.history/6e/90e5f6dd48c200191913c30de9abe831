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

import org.apache.log4j.Logger;

/**
 * @author sist
 *
 */
public class UserDao {
	final static Logger LOG = Logger.getLogger(UserDao.class);
	
	/**
	 * 
	 * @Method Name  : add
	 * @작성일   : 2019. 8. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 : 단건 등록
	 * @param user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int add(User user)throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url    = "jdbc:oracle:thin:@211.238.142.124:1521:orcl";
		String userId = "HRSPRING";
		String passwd = "HRSPRING1026";		
		
		Connection conn = DriverManager.getConnection(url,userId,passwd);
		LOG.debug("---------------------");
		LOG.debug("conn : "+conn);
		LOG.debug("---------------------");
		
		//----------------------------------------
		//02.SQL
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
		PreparedStatement ps = conn.prepareStatement(sb.toString());
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
		LOG.debug("03. Run flag="+flag);
		LOG.debug("=============================");			
		
		//----------------------------------------
		//05.자원 반납
		//----------------------------------------	
		ps.close();
		conn.close();
		
		return flag;
	}
	
	/**
	 * 
	 * @Method Name  : get
	 * @작성일   : 2019. 8. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 : 단건 조회
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User get(String id)throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url    = "jdbc:oracle:thin:@211.238.142.124:1521:orcl";
		String userId = "HRSPRING";
		String passwd = "HRSPRING1026";		
		User output = null;
		Connection conn = DriverManager.getConnection(url,userId,passwd);
		LOG.debug("---------------------");
		LOG.debug("conn : "+conn);
		LOG.debug("---------------------");
		
		//----------------------------------------
		//02.SQL
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT         \n");
		sb.append("     u_id,      \n");
		sb.append("     name,      \n");
		sb.append("     passwd     \n");
		sb.append(" FROM users     \n");
		sb.append(" WHERE u_id = ? \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, id);
		LOG.debug("=============================");
		LOG.debug("03. param="+id);
		LOG.debug("=============================");			
		
		//----------------------------------------
		//04.Run sql
		//----------------------------------------	
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			output = new User();
			output.setU_id(rs.getString("u_id"));
			output.setName(rs.getString("name"));
			output.setPasswd(rs.getString("passwd"));
		}
		LOG.debug("=============================");
		LOG.debug("04. output="+output);
		LOG.debug("=============================");	
		
		//----------------------------------------
		//05.자원 반납
		//----------------------------------------	
		rs.close();
		ps.close();
		conn.close();
		
		return output;
	}
	
	/**
	 * @Method Name  : main
	 * @작성일   : 2019. 8. 19.
	 * @작성자   : sist
	 * @변경이력  : 최초작성
	 * @Method 설명 :
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.debug("---------------------");
		LOG.debug("Main");
		LOG.debug("---------------------");
		//j01_ip : j01_125
		UserDao dao = new UserDao();
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
