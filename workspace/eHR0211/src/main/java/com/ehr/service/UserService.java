package com.ehr.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ehr.Level;
import com.ehr.User;
import com.ehr.UserDao;

public class UserService {
	public static final int MIN_LOGIN_COUNT_FOR_SILVER = 0;
	public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 0;
	
	final Logger LOG = Logger.getLogger(UserService.class);
	private MailSender mailSender;
	private PlatformTransactionManager transactionManager;
	private DataSource dataSource;
	private UserDao userDao;
	
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//회원가입
	//u_id : 유저 입력
	//name : 유저 입력
	//passwd : 유저 입력
	//h_level : BASIC
	//login : 0
	//recommend : 0
	//email : 유저 입력
	//reg_dt : NULL
	public void add(User user) {
		if(user.gethLevel()==null) {
			user.sethLevel(Level.BASIC);			
		}
		userDao.add(user);
	}
	//level upgrade
	public void upgradeLevels() throws SQLException {
		int upCnt = 0;
		LOG.debug("inital transactionManager="+transactionManager.toString());
		//트랜잭션 매니저 호출
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		LOG.debug("inital status="+status.toString());
		//1. 전체 사용자를 조회
		List<User> users = userDao.getAll();
		try {
			for(User user : users) {
				if(canUpgradeLevel(user)==true) {
					upgradeLevel(user);
					upCnt++;
				}
			}//--for
			//3. 성공 시 : Connection.commit();
			transactionManager.commit(status);
		}catch(Exception e) {
			//3. 실패 시 : Connection.rollback();
			transactionManager.rollback(status);
			LOG.debug("---------------------------------------------------------------");
			LOG.debug("rollback transactionManager="+transactionManager.toString());
			LOG.debug("rollback status="+status.toString());
			LOG.debug("---------------------------------------------------------------");
			throw e;
		}
		//자원 반납은 TransactionManager가 자동으로 찾아준다.
		LOG.debug("---------------------------------------------------------------");
		LOG.debug("upCnt = "+upCnt);
		LOG.debug("---------------------------------------------------------------");
	}
	protected void upgradeLevel(User user) {
//		if(user.gethLevel() == Level.BASIC) {
//			user.sethLevel(Level.SILVER);
//		}else if(user.gethLevel() == Level.SILVER) {
//			user.sethLevel(Level.GOLD);
//		}
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeMail(user);
	}
	
	/**
	 * 등업 사용자에게 메일 보내기
	 * @param user
	 */
	private void sendUpgradeMail(User user) {
		LOG.debug("---------------------------------------------------------------");
		LOG.debug("sendUpgradeMail");
		LOG.debug("---------------------------------------------------------------");
		try {
			//POP 서버명 : pop.naver.com
			//SMTP 서버명 : smtp.naver.com
			//POP 포트 : 995, 보안연결(SSL) 필요
			//SMTP 포트 : 465, 보안 연결(SSL) 필요
			//아이디 : jamesol
			//비밀번호 : 네이버 로그인 비밀번호
			//보내는 사람
			String host = "smtp.naver.com";
			String userName = "onnr0421";
			String password = "fl6sn7dhs819/>";
			int port = 465;
			
			//받는사람
			String recipient = user.getEmail();
			//제목
			String title   = user.getName()+"님 등업(https://cafe.naver.com/kndjang)";
			//내용
			String contents = user.getU_id()+"님의 등급이\n"+user.gethLevel().name()+"로 업되었습니다.";
			//SMTP서버 설정
			Properties  props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host);
			
			//인증
			Session  session = Session.getInstance(props, new Authenticator() {
				String uName  = 	userName;
				String passwd = 	password;
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication(uName, passwd);
				}				
			} );
			
			session.setDebug(true);
			//
//			Message mimeMessage=new MimeMessage(session);
//			//보내는 사람
//			mimeMessage.setFrom(new InternetAddress("nmjgo7321@naver.com"));
//			//받는사람
//			mimeMessage.setRecipient(Message.RecipientType.TO
//					                         , new InternetAddress(recipient));
//			//제목
//			mimeMessage.setSubject(title);
//			//내용
//			mimeMessage.setText(contents);
//			
//			//전송
//			Transport.send(mimeMessage);
			SimpleMailMessage mimeMessage = new SimpleMailMessage();
			//보내는 사람
			mimeMessage.setFrom("nmjgo7321@naver.com");
			//받는사람
			mimeMessage.setTo(recipient);
			//제목
			mimeMessage.setSubject(title);
			//내용
			mimeMessage.setText(contents);
			
			//전송
			this.mailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		LOG.debug("==============================");
		LOG.debug("=mail send=");
		LOG.debug("==============================");
	}
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.gethLevel();
		switch(currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
			case GOLD : return false;
			default: throw new IllegalArgumentException("Unknown Level"+user.gethLevel());
		}
	}
	
//	//level upgrade
//		public void upgradeLevels() {
//			//1. 전체 사용자를 조회
//			List<User> users = userDao.getAll();
//			int upCnt = 0;
//			for(User user : users) {
//				//2. 대상자를 선별
//				Boolean changed = null;
//				//2.1. Basic사용자, 로그인 cnt 50 이상 이면 : Basic -> Silver
//				if(user.gethLevel() == Level.BASIC && user.getLogin() >= 50) {
//					user.sethLevel(Level.SILVER);
//					changed = true;
//				}
//				//2.2. Silver사용자, 추천 cnt 30 이상 이면 : Silver -> Gold
//				else if(user.gethLevel() == Level.SILVER && user.getRecommend() >= 30) {
//					user.sethLevel(Level.GOLD);
//					changed = true;
//				}
//				//2.3. Gold는 대상 아님
//				else if(user.gethLevel() == Level.GOLD) {
//					changed = false;
//				}
//				//Others
//				else {
//					changed = false;
//				}
//				//3. 대상자 업그레이드 레벨 선정 및 upgrade
//				if(changed == true) {
//					userDao.update(user);
//					upCnt++;
//				}
//			}//--for
//			LOG.debug("---------------------------------------------------------------");
//			LOG.debug("upCnt = "+upCnt);
//			LOG.debug("---------------------------------------------------------------");
//		}

}
