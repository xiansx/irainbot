package com.irainbot.entity;

import java.io.Serializable;

/**
 * 用户信息
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId; // 用户id
	private String email; // 邮箱
	private String password; // 密码
	private String name; // 昵称

	private String session; // 用户session
	private String tempUnit; // 温度单位

	public UserInfo() {
	}

	public UserInfo(String userId, String email, String password, String name,
			String session, String tempUnit) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.session = session;
		this.tempUnit = tempUnit;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTempUnit() {
		return tempUnit;
	}

	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}

}
