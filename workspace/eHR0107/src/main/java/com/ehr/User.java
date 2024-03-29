package com.ehr;

public class User {
	/** 사용자 ID */
	private String u_id    ;
	/** 사용자 이름 */
	private String name    ;
	/** 사용자 비번 */
	private String passwd  ;
	
	public User() {}

	public User(String u_id, String name, String passwd) {
		super();
		this.u_id = u_id;
		this.name = name;
		this.passwd = passwd;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", name=" + name + ", passwd=" + passwd + "]";
	}
	
	
}
