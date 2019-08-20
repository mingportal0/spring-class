package com.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class NUserDao implements ConnectionMaker {
	final static Logger LOG = Logger.getLogger(NUserDao.class);
	
	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url    = "jdbc:oracle:thin:@211.238.142.124:1521:orcl";
		String userId = "HRSPRING";
		String passwd = "HRSPRING1026";		
		
		Connection conn = DriverManager.getConnection(url,userId,passwd);
		LOG.debug("---------------------");
		LOG.debug("01. conn : "+conn);
		LOG.debug("---------------------");
		
		return conn;
	}

}
