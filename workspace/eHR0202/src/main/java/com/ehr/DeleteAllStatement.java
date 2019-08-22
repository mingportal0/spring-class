package com.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {
	private User user;
	
	public DeleteAllStatement() {}
	
	public DeleteAllStatement(User user) {
		this.user = user;
	}


	@Override
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		//----------------------------------------
		//02.SQL작성
		//----------------------------------------		
		StringBuilder sb = new StringBuilder();
		sb.append(" DELETE FROM users   \n");
		sb.append(" WHERE u_id = ?      \n");
		
		PreparedStatement ps = c.prepareStatement(sb.toString());		
		ps.setString(1, user.getU_id());
		return ps;
	}

}
