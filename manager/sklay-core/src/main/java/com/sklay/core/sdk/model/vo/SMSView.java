package com.sklay.core.sdk.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.sklay.core.enums.OperatorType;
import com.sklay.core.enums.SMSStatus;

/**
 * 
 */
public class SMSView implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private String content;

	private Date sendTime;

	private String receiver;

	private SMSStatus status;

	private Long reportTime;

	private OperatorType operator;

	private String remark;

	public SMSView() {
	}

	public SMSView(Long userId, String content, Date sendTime, String receiver,
			Long reportTime, SMSStatus status) {
		super();
		this.userId = userId;
		this.content = content;
		this.sendTime = sendTime;
		this.receiver = receiver;
		this.reportTime = reportTime;
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Long getReportTime() {
		return reportTime;
	}

	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
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

	public OperatorType getOperator() {
		return operator;
	}

	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "SMSView [id=" + id + ", userId=" + userId + ", content="
				+ content + ", sendTime=" + sendTime + ", receiver=" + receiver
				+ ", status=" + status + ", reportTime=" + reportTime
				+ ", operator=" + operator + ", remark=" + remark + "]";
	}

}