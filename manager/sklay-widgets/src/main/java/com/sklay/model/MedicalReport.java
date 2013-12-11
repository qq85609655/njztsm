package com.sklay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.TipType;

@Entity
@Table(name = "sklay_user_medical_report")
public class MedicalReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148659448349696609L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "uid", nullable = false)
	private User targetUser;

	@Column(name = "report_time", nullable = false)
	private Long reportTime;

	@Lob
	private String result;

	@Lob
	private String remark;

	@Column(name = "report_type")
	private SMSType reportType;

	@Column(name = "sms_content")
	@Lob
	private String smsContent;

	@Column(name = "original_data")
	@Lob
	private String originalData;

	@Column(name = "tip_type", columnDefinition = "int default 0")
	private TipType tipType;

	@Column(name = "readed", columnDefinition = "int default 0")
	private ReadType read;

	public Long getReportTime() {
		return reportTime;
	}

	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MedicalReport() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SMSType getReportType() {
		return reportType;
	}

	public void setReportType(SMSType reportType) {
		this.reportType = reportType;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public MedicalReport(User targetUser, Long reportTime, String result,
			String remark, SMSType reportType, String smsContent,
			String originalData) {
		super();
		this.setTargetUser(targetUser);
		this.reportTime = reportTime;
		this.result = result;
		this.remark = remark;
		this.reportType = reportType;
		this.smsContent = smsContent;
		this.originalData = originalData;
	}

	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}

	public TipType getTipType() {
		return tipType;
	}

	public void setTipType(TipType tipType) {
		this.tipType = tipType;
	}

	public ReadType getRead() {
		return read;
	}

	public void setRead(ReadType read) {
		this.read = read;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

}
