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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;

@Entity
@Table(name = "sklay_app")
public class Application implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false, name = "app")
	private AppType appType;

	private AuditStatus status;

	private Integer cost;

	@Lob
	private String remark;

	@ManyToOne
	@JoinColumn(name = "pid", nullable = true)
	private Product product;

	private int count;

	private int used;

	@Column(name = "owner", nullable = false)
	private Long owner;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "updator", nullable = false)
	private Long updator;

	@Column(name = "updator_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatorTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppType getAppType() {
		return appType;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdator() {
		return updator;
	}

	public void setUpdator(Long updator) {
		this.updator = updator;
	}

	public Date getUpdatorTime() {
		return updatorTime;
	}

	public void setUpdatorTime(Date updatorTime) {
		this.updatorTime = updatorTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public Application() {
		super();
	}

	public Application(Long id, AppType appType, AuditStatus status,
			Integer cost, String remark, Long owner, Date createTime,
			Long updator, Date updatorTime) {
		super();
		this.id = id;
		this.appType = appType;
		this.status = status;
		this.cost = cost;
		this.remark = remark;
		this.owner = owner;
		this.createTime = createTime;
		this.updator = updator;
		this.updatorTime = updatorTime;
	}

	public Application(AppType appType, AuditStatus status, Integer cost,
			String remark, Long owner, Date createTime, Long updator,
			Date updatorTime) {
		super();
		this.appType = appType;
		this.status = status;
		this.cost = cost;
		this.remark = remark;
		this.owner = owner;
		this.createTime = createTime;
		this.updator = updator;
		this.updatorTime = updatorTime;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", appType=" + appType + ", status="
				+ status + ", cost=" + cost + ", remark=" + remark + ", owner="
				+ owner + ", createTime=" + createTime + ", updator=" + updator
				+ ", updatorTime=" + updatorTime + "]";
	}

}
