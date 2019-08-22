package com.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * DI 역할
 * @author sist
 *
 */
public class JdbcContext {
	final Logger LOG = Logger.getLogger(JdbcContext.class);
	private DataSource dataSource;
	
	public JdbcContext() {}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int excuteSql(final String query, User user) throws SQLException{
		int flag = 0;
		flag = workWithStatementStrategy(
					new StatementStrategy() {
						@Override
						public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
							PreparedStatement ps = c.prepareStatement(query);		
							ps.setString(1, user.getU_id());
							return ps;
						}
					}
				);
		return flag;
	}
	public int workWithStatementStrategy(StatementStrategy st) throws SQLException{
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
}
