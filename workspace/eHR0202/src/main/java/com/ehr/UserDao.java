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

import org.springframework.dao.EmptyResultDataAccessException;
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
	 * @Method Name  : count
	 * @param user
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int count(String uId)throws ClassNotFoundException,SQLException{
		int cnt = 0;
		//----------------------------------------
		//01.DB 연결
		//----------------------------------------		
		Connection  c = dataSource.getConnection();
		LOG.info("=============================");
		LOG.info("01. Connection="+c);
		LOG.info("=============================");		
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT COUNT(*) cnt      \n");
		sb.append(" FROM users            \n");
		sb.append(" WHERE u_id like ? \n");
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ps.setString(1, "%"+uId+"%");
		LOG.info("=============================");
		LOG.info("03. id="+uId);
		LOG.info("=============================");			
		
		//----------------------------------------
		//04.Run sql
		//----------------------------------------	
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		LOG.info("=============================");
		LOG.info("04. Run cnt="+cnt);
		LOG.info("=============================");		
		
		//----------------------------------------
		//05.자원반납
		//----------------------------------------
		rs.close();
		ps.close();
		c.close();
		
		return cnt;
	}
	public PreparedStatement makeStatement(Connection c) throws SQLException{
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		PreparedStatement ps = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM users   \n");
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		ps = c.prepareStatement(sb.toString());		
		
		return ps;
	}
	public int jdbcContextWithStatementStrategy(StatementStrategy st) throws SQLException{
		//----------------------------------------
		//01.DB 연결
		//----------------------------------------	
		Connection  c = null;
		PreparedStatement ps = null;
		int flag = 0;
		
		try {
			c = dataSource.getConnection();
			LOG.info("=============================");
			LOG.info("01. Connection="+c);
			LOG.info("=============================");	
			
			//========================================
			//02.SQL작성=변하는 부분 메소드로 추출                    =
			//========================================	
			ps = st.makePreparedStatement(c);
			
			//----------------------------------------
			//04.Run sql
			//----------------------------------------	
			flag = ps.executeUpdate();
			LOG.info("=============================");
			LOG.info("04. Run flag="+flag);
			LOG.info("=============================");		
		}catch(SQLException e) {
			LOG.info("=============================");
			LOG.info("05. SQLException="+e.getMessage());
			LOG.info("=============================");	
		}finally {
		//----------------------------------------
		//05.자원반납
		//----------------------------------------	
			if(null != ps) {
				try {
					ps.close();
				}catch(SQLException e) {
					LOG.info("=============================");
					LOG.info("06. SQLException="+e.getMessage());
					LOG.info("=============================");	
				}
			}
			if(null != c) {
				try {
					c.close();
				}catch(SQLException e) {
					LOG.info("=============================");
					LOG.info("06. SQLException="+e.getMessage());
					LOG.info("=============================");	
				}
			}
		}
		return flag;
		
	}

	
	/**
	 * @Method Name  : deleteUser
	 * @param user
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int deleteUser(User user) throws SQLException{
		int flag = 0;
		flag = jdbcContextWithStatementStrategy(new DeleteAllStatement(user));
		return flag;
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
		StatementStrategy st = new AddStatement(user);
		int flag = 0;
		flag = jdbcContextWithStatementStrategy(st);
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
		LOG.info("=============================");
		LOG.info("01. Connection="+c);
		LOG.info("=============================");		
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
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ps.setString(1, id);
		LOG.info("=============================");
		LOG.info("03. id="+id);
		LOG.info("=============================");			
		
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
		LOG.info("=============================");
		LOG.info("04. Run outVO="+outVO);
		LOG.info("=============================");		
		
		//----------------------------------------
		//05.자원반납
		//----------------------------------------
		rs.close();
		ps.close();
		c.close();
		
		//----------------------------------------
		//06.예외 발생
		//----------------------------------------
		if(null== outVO) {
			throw new EmptyResultDataAccessException(1);
		}
		return outVO;
	}	
	


}
