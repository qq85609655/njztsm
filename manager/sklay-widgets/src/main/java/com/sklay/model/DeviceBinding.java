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

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;

@Entity
@Table(name = "sklay_device_binding")
public class DeviceBinding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6024411221945234052L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "serial_number")
	private String serialNumber;

	@ManyToOne
	@JoinColumn(name = "tuid")
	private User targetUser;

	private AuditStatus status;

	@Column(name = "\"level\"")
	private Level level;

	@Column(name = "mold")
	private BindingMold mold;

	@Column(name = "mold_status")
	private AuditStatus moldStatus = AuditStatus.WAIT;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = true)
	private User creator;

	private Long updator;

	private Long belong;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Long getUpdator() {
		return updator;
	}

	public void setUpdator(Long updator) {
		this.updator = updator;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public AuditStatus getMoldStatus() {
		return moldStatus;
	}

	public void setMoldStatus(AuditStatus moldStatus) {
		this.moldStatus = moldStatus;
	}

	public BindingMold getMold() {
		return mold;
	}

	public void setMold(BindingMold mold) {
		this.mold = mold;
	}

	public Long getBelong() {
		return belong;
	}

	public void setBelong(Long belong) {
		this.belong = belong;
	}

	public DeviceBinding(String serialNumber, User targetUser,
			AuditStatus status, Level level, Date createTime, Date updateTime,
			User creator, Long updator, BindingMold mold) {
		super();
		this.serialNumber = serialNumber;
		this.targetUser = targetUser;
		this.status = status;
		this.level = level;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.creator = creator;
		this.updator = updator;
		this.mold = mold;
	}

	public DeviceBinding() {
		super();
	}

	@Override
	public String toString() {
		return "DeviceBinding [id=" + id + ", serialNumber=" + serialNumber
				+ ", targetUser=" + targetUser + ", status=" + status
				+ ", level=" + level + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", creator=" + creator
				+ ", updator=" + updator + "]";
	}

}
