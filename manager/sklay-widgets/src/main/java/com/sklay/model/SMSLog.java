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

import com.sklay.core.enums.OperatorType;
import com.sklay.core.enums.SMSStatus;

/**
 * 
 */
@Entity
@Table(name = "sklay_sms_log")
public class SMSLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "uid", nullable = false)
	private User user;

	@Column(nullable = false)
	@Lob
	private String content;

	@Column(name = "send_time", nullable = false)
	private Date sendTime;

	@ManyToOne(optional = false)
	@JoinColumn(name = "receiver", referencedColumnName = "phone")
	private User receiver;

	@Column(columnDefinition = " int default 0")
	private SMSStatus status;

	@Column(name = "report_time", nullable = false)
	private Long reportTime;

	@Column(name = "send_operator", nullable = false)
	private OperatorType operator;

	private String remark;

	public SMSLog() {
	}

	public SMSLog(User user, String content, Date sendTime, User receiver,
			Long reportTime, SMSStatus status) {
		super();
		this.user = user;
		this.content = content;
		this.sendTime = sendTime;
		this.receiver = receiver;
		this.reportTime = reportTime;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Long getReportTime() {
		return reportTime;
	}

	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}

	public SMSStatus getStatus() {
		return status;
	}

	public void setStatus(SMSStatus status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SMSLog [id=" + id + ", user=" + user + ", content=" + content
				+ ", sendTime=" + sendTime + ", receiver=" + receiver
				+ ", status=" + status + ", reportTime=" + reportTime
				+ ", operator=" + operator + ", remark=" + remark + "]";
	}

}