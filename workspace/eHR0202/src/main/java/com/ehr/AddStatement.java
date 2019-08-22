package com.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy {
	private User user;
	
	public AddStatement() {}
	
	public AddStatement(User user) {
		this.user = user;
	}



	@Override
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
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
		
		LOG.info("=============================");
		LOG.info("02. sql=\n"+sb.toString());
		LOG.info("=============================");		
		
		//----------------------------------------
		//03.PreparedStatement
		//----------------------------------------			
		PreparedStatement ps = c.prepareStatement(sb.toString());
		ps.setString(1, user.getU_id());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPasswd());
		LOG.info("=============================");
		LOG.info("03. param="+user);
		LOG.info("=============================");			
		
		return ps;
	}

}
