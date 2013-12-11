package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sklay_login_record")
public class LoginRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4105525615216753596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "last_login_date")
	private Date lastLoginDate;

	@Column(name = "login_address")
	private String loginAddress;

	@Column(name = "last_login_address")
	private String LastLoginAddress;

	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getLastLoginAddress() {
		return LastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		LastLoginAddress = lastLoginAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginRecord [id=" + id + ", loginDate=" + loginDate
				+ ", lastLoginDate=" + lastLoginDate + ", loginAddress="
				+ loginAddress + ", LastLoginAddress=" + LastLoginAddress
				+ ", user=" + user + "]";
	}

}
