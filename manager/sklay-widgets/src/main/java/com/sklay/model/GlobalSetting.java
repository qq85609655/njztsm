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

import org.springframework.format.annotation.DateTimeFormat;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.SwitchStatus;

@Entity
@Table(name = "sklay_global_setting")
public class GlobalSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	/** 关联发送短信 */
	@Column(name = "sms_fetch", columnDefinition = " int default 0")
	private SwitchStatus smsFetch;

	/** 每天可以访问几次 */
	@Column(name = "visit_count", columnDefinition = " int default 0")
	private Integer visitCount;

	/** 设备绑定的默认状态 */
	@Column(name = "device_binding_status", columnDefinition = " int default 0")
	private AuditStatus deviceBindingStatus;

	/** 体检短信开关 */
	@Column(name = "send_physical", columnDefinition = "int default 0")
	private SwitchStatus physicalSMS;

	/** 定位短信开关 */
	@Column(name = "send_sos", columnDefinition = "int default 0")
	private SwitchStatus sosSMS;

	/** 体检短信发送每天开始时间 */
	@Column(name = "send_start_time")
	private String sendStartTime;

	/** 体检短信发送每天结束时间 */
	@Column(name = "send_end_time")
	private String sendEndTime;

	/** 用户默认审核状态 */
	@Column(name = "user_audite", columnDefinition = " int default 0")
	private AuditStatus userAudite;

	/** 创建代理商事是否建组开关 */
	@Column(name = "auto_create_group", columnDefinition = " int default 0")
	private SwitchStatus autoCreateGroup;

	/** 分组审核状态 */
	@Column(name = "group_audite", columnDefinition = " int default 0")
	private AuditStatus groupAudite;

	/** 定时短信开关 */
	@Column(name = "send_job_switch", columnDefinition = "int default 0")
	private SwitchStatus sendSMSJob;

	/** 定时短信时间 */
	@Column(name = "send_job_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendSMSTime;

	/** 默认设备允许绑定个数 */
	@Column(name = "binding_count", columnDefinition = "int default 2")
	private Integer bindingCount;

	/** 开启api日志跟踪 0025100001586307 */
	@Column(name = "api_log_switch", columnDefinition = "int default 0")
	private SwitchStatus apiLogSwitch;

	@Column(name = "web_site")
	private String webSite;

	@Column(name = "copy_right")
	private String copyRight;

	@Lob
	@Column(name = "web_description")
	private String webDescription;

	@Lob
	@Column(name = "web_service")
	private String webService;

	@Lob
	@Column(name = "web_meta")
	private String webMeta;

	@Lob
	@Column(name = "web_author")
	private String webAuthor;

	public GlobalSetting() {
	}

	public SwitchStatus getApiLogSwitch() {
		return apiLogSwitch;
	}

	public void setApiLogSwitch(SwitchStatus apiLogSwitch) {
		this.apiLogSwitch = apiLogSwitch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SwitchStatus getSmsFetch() {
		return smsFetch;
	}

	public void setSmsFetch(SwitchStatus smsFetch) {
		this.smsFetch = smsFetch;
	}

	public AuditStatus getUserAudite() {
		return userAudite;
	}

	public void setUserAudite(AuditStatus userAudite) {
		this.userAudite = userAudite;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Date getSendSMSTime() {
		return sendSMSTime;
	}

	public void setSendSMSTime(Date sendSMSTime) {
		this.sendSMSTime = sendSMSTime;
	}

	public AuditStatus getGroupAudite() {
		return groupAudite;
	}

	public void setGroupAudite(AuditStatus groupAudite) {
		this.groupAudite = groupAudite;
	}

	public SwitchStatus getAutoCreateGroup() {
		return autoCreateGroup;
	}

	public void setAutoCreateGroup(SwitchStatus autoCreateGroup) {
		this.autoCreateGroup = autoCreateGroup;
	}

	public AuditStatus getDeviceBindingStatus() {
		return deviceBindingStatus;
	}

	public void setDeviceBindingStatus(AuditStatus deviceBindingStatus) {
		this.deviceBindingStatus = deviceBindingStatus;
	}

	public SwitchStatus getPhysicalSMS() {
		return physicalSMS;
	}

	public void setPhysicalSMS(SwitchStatus physicalSMS) {
		this.physicalSMS = physicalSMS;
	}

	public String getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public String getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public SwitchStatus getSendSMSJob() {
		return sendSMSJob;
	}

	public void setSendSMSJob(SwitchStatus sendSMSJob) {
		this.sendSMSJob = sendSMSJob;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getWebDescription() {
		return webDescription;
	}

	public void setWebDescription(String webDescription) {
		this.webDescription = webDescription;
	}

	public String getWebService() {
		return webService;
	}

	public void setWebService(String webService) {
		this.webService = webService;
	}

	public String getWebAuthor() {
		return webAuthor;
	}

	public void setWebAuthor(String webAuthor) {
		this.webAuthor = webAuthor;
	}

	public String getWebMeta() {
		return webMeta;
	}

	public void setWebMeta(String webMeta) {
		this.webMeta = webMeta;
	}

	public Integer getBindingCount() {
		return bindingCount;
	}

	public void setBindingCount(Integer bindingCount) {
		this.bindingCount = bindingCount;
	}

	public SwitchStatus getSosSMS() {
		return sosSMS;
	}

	public void setSosSMS(SwitchStatus sosSMS) {
		this.sosSMS = sosSMS;
	}

	@Override
	public String toString() {
		return "GlobalSetting [id=" + id + ", smsFetch=" + smsFetch
				+ ", visitCount=" + visitCount + ", deviceBindingStatus="
				+ deviceBindingStatus + ", physicalSMS=" + physicalSMS
				+ ", sendStartTime=" + sendStartTime + ", sendEndTime="
				+ sendEndTime + ", userAudite=" + userAudite
				+ ", autoCreateGroup=" + autoCreateGroup + ", groupAudite="
				+ groupAudite + ", sendSMSJob=" + sendSMSJob + ", sendSMSTime="
				+ sendSMSTime + ", webSite=" + webSite + ", copyRight="
				+ copyRight + ", webDescription=" + webDescription
				+ ", webService=" + webService + ", webMeta=" + webMeta
				+ ", webAuthor=" + webAuthor + "]";
	}

}