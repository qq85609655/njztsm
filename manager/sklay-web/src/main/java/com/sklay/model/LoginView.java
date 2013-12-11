package com.sklay.model;

import java.io.Serializable;

public class LoginView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String login = "登录";
	private String logout = "登出";
	private String manager = "会员管理";
	private String menus = "菜单";

	public String getLogin() {
		return login;
	}

	public String getLogout() {
		return logout;
	}

	public String getManager() {
		return manager;
	}

	public String getMenus() {
		return menus;
	}

}
