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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author sist
 *
 */
public class UserDao {

	static final Logger LOG= Logger.getLogger(UserDao.class);
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User tmp = new User();
			tmp.setNum(rs.getInt("num"));
			tmp.setU_id(rs.getString("u_id"));
			tmp.setName(rs.getString("name"));
			tmp.setPasswd(rs.getString("passwd"));
			tmp.setTotal(rs.getInt("total_cnt"));
			return tmp;
		}
	};
	private JdbcTemplate jdbcTemplate; 
	private DataSource dataSource;
	
	public UserDao() {
	}
	
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	
	public List<User> pageAll(SearchVO vo){
		List<User> outList = null;
		StringBuilder sbWhere = new StringBuilder();
		
		//search div
		if(null != vo.getSearchDiv()){
			if("10".equals(vo.getSearchDiv())){
				sbWhere.append("WHERE u_id like ? \n");				
			}else if("20".equals(vo.getSearchDiv())){
				sbWhere.append("WHERE name like ? \n");				
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT T1.*,T2.*                                              \n");
		sb.append(" FROM(                                                         \n");
		sb.append(" SELECT B.rnum as num                                          \n");
		sb.append("       ,B.u_id                                                 \n");
		sb.append("       ,B.name                                                 \n");
		sb.append("       ,B.passwd                                               \n");
		sb.append(" FROM(                                                         \n");
		sb.append("    SELECT ROWNUM AS rnum,A.*                                  \n");
		sb.append("    FROM(                                                      \n");
		sb.append("       SELECT *                                                \n");
		sb.append("       FROM users                                              \n");
		//=============================================================================
		//search div
		sb.append(sbWhere.toString());
		//=============================================================================
		sb.append("       ORDER BY u_id ASC                                       \n");
		sb.append("    )A                                                         \n");
		sb.append("    WHERE ROWNUM <=(?*(?-1)+?) )B   							  \n");
		sb.append("    WHERE B.rnum>= (?*(?-1)+1)          				          \n");
		sb.append(" )T1                                                           \n");
		sb.append(" CROSS JOIN                                                    \n");
		sb.append(" (                                                             \n");
		sb.append(" SELECT COUNT(*) total_cnt                                     \n");
		sb.append(" FROM users                                                    \n");
		//=============================================================================
		//search div
		sb.append(sbWhere.toString());
		//=============================================================================
		sb.append(" )T2                                                           \n");
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");	
		Object[] args = null;
		if(null == vo.getSearchDiv() || vo.getSearchDiv().equals("")) {
			args = new Object[] {
				vo.getPageSize()
			   ,vo.getPageNum()
			   ,vo.getPageSize()
			   ,vo.getPageSize()
			   ,vo.getPageNum()
			};
			LOG.info("=============================");
			LOG.info("03. param 5ea="+vo);
			LOG.info("=============================");	
		}else {
			args = new Object[] {
				"%"+vo.getSearchWord()+"%"
			   ,vo.getPageSize()
			   ,vo.getPageNum()
			   ,vo.getPageSize()
			   ,vo.getPageSize()
			   ,vo.getPageNum()
			   ,"%"+vo.getSearchWord()+"%"
			};
			LOG.info("=============================");
			LOG.info("03. param 7ea="+vo);
			LOG.info("=============================");	
		}
		outList = (List<User>) jdbcTemplate.query(sb.toString()
				, args
				, userMapper
		);
		
		return outList;
	}
	
	/**
	 * 전체 조회 u_id asc순으로
	 * @return
	 */
	public List<User> getAll(){
		List<User> outList = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT u_id,   \n");
		sb.append("        name,   \n");
		sb.append("        passwd  \n");
		sb.append(" FROM users     \n");
		sb.append(" ORDER BY u_id  \n");
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");	
		
		outList = (List<User>) jdbcTemplate.query(sb.toString()
				, userMapper
		);
		
		return outList;
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
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT COUNT(*) cnt      \n");
		sb.append(" FROM users            \n");
		sb.append(" WHERE u_id like ? \n");
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");	
		
		Object[] args = {"%"+uId+"%"};
		cnt = jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);
		LOG.info("=============================");
		LOG.info("04. Run cnt="+cnt);
		LOG.info("=============================");		
		
		return cnt;
	}
	
	/**
	 * @Method Name  : deleteUser
	 * @param user
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int deleteUser(User user) throws SQLException{
		String query = "DELETE FROM users WHERE u_id = ?";
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+query);
		LOG.info("=============================");	
		
		Object[] args = {user.getU_id()};
		int flag = jdbcTemplate.update(query, args);
		
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
		int flag = 0;
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
		
		String query = sb.toString();
		LOG.info("=============================");
		LOG.info("02. sql=\n"+query);
		LOG.info("=============================");
		
		Object[] args = {user.getU_id(), user.getName(), user.getPasswd()};
		flag = jdbcTemplate.update(query, args);
		
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
		User outVO = null;
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
		
		outVO = jdbcTemplate.queryForObject(sb.toString()
				, new Object[] {id}
				, userMapper
		);
		
		LOG.info("=============================");
		LOG.info("04. Run outVO="+outVO);
		LOG.info("=============================");		
		
		//----------------------------------------
		//06.예외 발생
		//----------------------------------------
		if(null== outVO) {
			throw new EmptyResultDataAccessException(1);
		}
		return outVO;
	}	
	


}
