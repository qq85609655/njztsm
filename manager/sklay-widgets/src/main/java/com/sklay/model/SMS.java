package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sklay.core.enums.SMSStatus;

/**
 * 
 */
@Entity
@Table(name = "sklay_app_sms")
public class SMS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	private Long creator;

	@ManyToOne
	@JoinColumn(name = "app", nullable = false)
	private Application app;

	@Column(nullable = false)
	@Lob
	private String content;

	@Column(name = "send_time", nullable = false)
	private Date sendTime;

	private Long receiver;

	@Column(columnDefinition = " int default 0")
	private SMSStatus status;

	private String result;

	private String mobile;

	@Lob
	private String remark;

	@Column(columnDefinition = " int default 1")
	private int count = 1;

	private Long belong;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public SMSStatus getStatus() {
		return status;
	}

	public void setStatus(SMSStatus status) {
		this.status = status;
	}

	public SMS() {
		super();
	}

	public SMS(Long creator, String content, Date sendTime, Long receiver,
			String mobile, SMSStatus status) {
		super();
		this.creator = creator;
		this.content = content;
		this.sendTime = sendTime;
		this.receiver = receiver;
		this.mobile = mobile;
		this.status = status;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getBelong() {
		return belong;
	}

	public void setBelong(Long belong) {
		this.belong = belong;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "SMS [id=" + id + ", creator=" + creator + ", app=" + app
				+ ", content=" + content + ", sendTime=" + sendTime
				+ ", receiver=" + receiver + ", status=" + status + ", result="
				+ result + ", remark=" + remark + ", count=" + count
				+ ", belong=" + belong + "]";
	}

}