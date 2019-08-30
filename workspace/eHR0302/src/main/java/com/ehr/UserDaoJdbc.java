package com.ehr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoJdbc implements UserDao {
	static final Logger LOG= Logger.getLogger(UserDao.class);
	
	private RowMapper<User> userMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User tmp=new User();
			tmp.setU_id(rs.getString("u_id"));
			tmp.setName(rs.getString("name"));
			tmp.setPasswd(rs.getString("passwd"));
			tmp.sethLevel(Level.valueOf(rs.getInt("h_level")));
			tmp.setLogin(rs.getInt("login"));
			tmp.setRecommend(rs.getInt("recommend"));
			tmp.setEmail(rs.getString("email"));
			tmp.setRegDt(rs.getString("reg_dt"));
			tmp.setNum(rs.getInt("rnum"));
			tmp.setTotalCnt(rs.getInt("total_cnt"));
			return tmp;
		}
	};	
		
	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;
	
	public UserDaoJdbc() {}
	
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		this.dataSource = dataSource;
	}
	@Override
	public int update(User user) {
		int flag = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE users             \n");
		sb.append(" SET name      = ?,       \n");
		sb.append("     passwd    = ?,       \n");
		sb.append("     h_level   = ?,       \n");
		sb.append("     login     = ?,       \n");
		sb.append("     recommend = ?,       \n");
		sb.append("     email     = ?,       \n");
		sb.append("     reg_dt    = SYSDATE  \n");
		sb.append(" WHERE u_id  = ?          \n");
		
		List<Object> args = new ArrayList<Object>();
		//SET
		args.add(user.getName());
		args.add(user.getPasswd());
		args.add(user.gethLevel().intValue());
		args.add(user.getLogin());
		args.add(user.getRecommend());
		args.add(user.getEmail());
		//WHERE
		args.add(user.getU_id());
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");	
		
		LOG.debug("=============================");
		LOG.debug("02. param=\n"+user);
		LOG.debug("=============================");	
		flag = jdbcTemplate.update(sb.toString(), args.toArray());
		
		return flag;
	}
	@Override
	public List<User> retrieve(Search vo) {
		//u_id  :10
		//name  :20
		//email :30
		StringBuilder param = new StringBuilder();
		//검색 조건이 선택이 되었다면
		if(null != vo && !"".equals(vo.getSearchDiv())) {
			//id 검색
			if("10".equals(vo.getSearchDiv())){
				param.append("WHERE u_id LIKE '%'|| ? ||'%' \n");
			}
			//이름 검색
			else if("20".equals(vo.getSearchDiv())){
				param.append("WHERE name LIKE '%'|| ? ||'%' \n");
			}
			//이메일 검색
			else if("30".equals(vo.getSearchDiv())){
				param.append("WHERE email LIKE '%'|| ? ||'%' \n");
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT T1.*,T2.*                                   \n");
		sb.append(" FROM(                                              \n");
		sb.append("     SELECT  B.u_id,                                \n");
		sb.append("             B.name,                                \n");
		sb.append("             B.passwd,                              \n");
		sb.append("             B.h_level,                             \n");
		sb.append("             B.login,                               \n");
		sb.append("             B.recommend,                           \n");
		sb.append("             B.email,                               \n");
		sb.append("             TO_CHAR(B.reg_dt,'YYYY/MM/DD') reg_dt, \n");
		sb.append("             B.rnum                                 \n");
		sb.append("     FROM(                                          \n");
		sb.append("             SELECT ROWNUM AS rnum                  \n");
		sb.append("                   ,A.*                             \n");
		sb.append("             FROM(                                  \n");
		sb.append("                 SELECT *                           \n");
		sb.append("                 FROM users                         \n");
		//=SEARCH CONDITION=================================================
		sb.append(param.toString());
		//==================================================================
		sb.append("                 ORDER BY reg_dt                    \n");
		sb.append("             )A                                     \n");
		//sb.append(" WHERE ROWNUM <=(&page_size*(&page_num-1)+&page_size))B \n");
		//sb.append(" WHERE B.rnum>= (&page_size*(&page_num-1)+1)            \n");
		sb.append("             WHERE ROWNUM <=(?*(?-1)+?))B           \n");
		sb.append("             WHERE B.rnum>= (?*(?-1)+1)             \n");
		sb.append(" 		)T1                                        \n");
		sb.append(" CROSS JOIN                                         \n");
		sb.append(" (                                                  \n");
		sb.append(" 	SELECT COUNT(*) total_cnt                      \n");
		sb.append(" 	FROM users                                     \n");
		//=SEARCH CONDITION=================================================
		sb.append(param.toString());
		//==================================================================
		sb.append(" )T2                                                \n");
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");	
		
		//검색조건 분기
		List<Object> args = new ArrayList<Object>();
		//5개
		if(null != vo && !"".equals(vo.getSearchDiv())) {
			args.add(vo.getSearchWord());
			args.add(vo.getPageSize());
			args.add(vo.getPageNum());
			args.add(vo.getPageSize());
			args.add(vo.getPageSize());
			args.add(vo.getPageNum());
			args.add(vo.getSearchWord());
		}
		//7개
		else {
			args.add(vo.getPageSize());
			args.add(vo.getPageNum());
			args.add(vo.getPageSize());
			args.add(vo.getPageSize());
			args.add(vo.getPageNum());
		}
		LOG.debug("=============================");
		LOG.debug("03. param vo=\n"+vo);
		LOG.debug("03. param args=\n"+args);
		LOG.debug("=============================");	
		List<User> list =jdbcTemplate.query(sb.toString(), args.toArray(), userMapper);
		return list;
	}
	
	@Override
	public List<User> getAll() {
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT  u_id,                               \n");
		sb.append("         name,                               \n");
		sb.append("         passwd,                             \n");
		sb.append("         h_level,                            \n");
		sb.append("         login,                              \n");
		sb.append("         recommend,                          \n");
		sb.append("         email,                              \n");
		sb.append("         TO_CHAR(reg_dt,'YYYY/MM/DD') reg_dt, \n");
		sb.append("         '1' rnum, \n");
		sb.append("         '1' total_cnt \n");
		sb.append(" FROM users                                  \n");
		sb.append(" ORDER BY u_id  								\n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		List<User> list =jdbcTemplate.query(sb.toString(), userMapper);
		return list;
	}

	@Override
	public int count(String uId) {
		int cnt = 0;//조회 count
		
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT COUNT(*) cnt      \n");
		sb.append(" FROM users               \n");
		sb.append(" WHERE u_id like ?        \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		
		cnt = jdbcTemplate.queryForObject(sb.toString()
				, new Object[] { "%"+uId+"%"}
				, Integer.class);
		
		
		LOG.debug("=============================");
		LOG.debug("04. Run cnt=\n"+cnt);
		LOG.debug("=============================");		
		
		return cnt;
	}	

	@Override
	public int deleteAll() {
		String query =" DELETE FROM users";
		int flag = jdbcTemplate.update(query);
		
		return flag;
	}
	@Override
	public int deleteUser(User user) {
		String query =" DELETE FROM users WHERE u_id = ? ";
		Object[] args = {user.getU_id()};
		int flag = jdbcTemplate.update(query, args);
		
		return flag;
	}

	@Override
	public int add(User user) {
		int flag = 0;	
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" INSERT INTO users ( \n");
		sb.append("     u_id,           \n");
		sb.append("     name,           \n");
		sb.append("     passwd,         \n");
		sb.append("     h_level,        \n");
		sb.append("     login,          \n");
		sb.append("     recommend,      \n");
		sb.append("     email,          \n");
		sb.append("     reg_dt          \n");
		sb.append(" ) VALUES (          \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     ?,              \n");
		sb.append("     SYSDATE         \n");
		sb.append(" )                   \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		Object[] args = {user.getU_id()
				        ,user.getName()
				        ,user.getPasswd()
				        ,user.gethLevel().intValue()
				        ,user.getLogin()
				        ,user.getRecommend()
				        ,user.getEmail()
		};
		LOG.debug("=============================");
		LOG.debug("02. param=\n"+user);
		LOG.debug("=============================");
		flag = jdbcTemplate.update(sb.toString(), args);
		
		return flag;
	}

	@Override
	public User get(String id) {
		User outVO=null;
		
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb=new StringBuilder();
		sb.append(" SELECT  u_id,                               \n");
		sb.append("         name,                               \n");
		sb.append("         passwd,                             \n");
		sb.append("         h_level,                            \n");
		sb.append("         login,                              \n");
		sb.append("         recommend,                          \n");
		sb.append("         email,                              \n");
		sb.append("         TO_CHAR(reg_dt,'YYYY/MM/DD') reg_dt,\n");
		//rowmapper를 retrieve와 공유하기 때문에 아래 컬럼 추가
		sb.append("         '1' rnum,                           \n");
		sb.append("         '1' total_cnt                       \n");
		sb.append(" FROM users                                  \n");
		sb.append(" WHERE u_id = ?                              \n");
		
		LOG.debug("=============================");
		LOG.debug("02. sql=\n"+sb.toString());
		LOG.debug("=============================");		
		
		outVO = jdbcTemplate.queryForObject(sb.toString()
				, new Object[] {id}
				, userMapper);
		
		//----------------------------------------
		//06.outVO==null 예외발생
		//----------------------------------------
		if(null == outVO) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return outVO;
	}
}
