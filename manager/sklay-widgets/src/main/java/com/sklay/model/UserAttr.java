package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sklay_user_attr")
public class UserAttr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false, name = "\"key\"")
	@NotBlank
	private String key;

	@Column(name = "\"value\"")
	@Lob
	private String value;

	@Column(name = "remark")
	@Lob
	private String remark;

	@Column(name = "uid", nullable = false)
	private Long userId;

	@Column(name = "insert_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserAttr() {
	}

	public UserAttr(String key, String value, Long userId) {
		super();
		this.key = key;
		this.value = value;
		this.userId = userId;
		this.insertTime = new Date();
	}

	public UserAttr(String key, String value, String remark, Long userId,
			Date insertTime) {
		super();
		this.key = key;
		this.value = value;
		this.remark = remark;
		this.userId = userId;
		this.insertTime = insertTime;
	}

	public UserAttr(String key, String value, Long userId, Date data) {
		super();
		this.key = key;
		this.value = value;
		this.userId = userId;
		this.insertTime = data;
	}

	@Override
	public String toString() {
		return "UserAttr [id=" + id + ", key=" + key + ", value=" + value
				+ ", userId=" + userId + ", insertTime=" + insertTime + "]";
	}

}
