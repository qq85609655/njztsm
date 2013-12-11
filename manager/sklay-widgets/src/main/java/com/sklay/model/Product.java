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
import com.sklay.core.enums.DelStatus;

@Entity
@Table(name = "sklay_product")
public class Product implements Serializable {

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

	private String title;

	private double price;

	@Lob
	private String description;

	@Lob
	private String remark;

	private DelStatus delete = DelStatus.SAVE;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private User creator;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@ManyToOne
	@JoinColumn(name = "updator", nullable = false)
	private User updator;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUpdator() {
		return updator;
	}

	public void setUpdator(User updator) {
		this.updator = updator;
	}

	public Date getUpdatorTime() {
		return updatorTime;
	}

	public void setUpdatorTime(Date updatorTime) {
		this.updatorTime = updatorTime;
	}

	public DelStatus getDelete() {
		return delete;
	}

	public void setDelete(DelStatus delete) {
		this.delete = delete;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}

	public Product() {
		super();
	}

	public Product(Long id, AppType appType, String title, double price,
			String description, String remark, User creator, Date createTime,
			User updator, Date updatorTime) {
		super();
		this.id = id;
		this.appType = appType;
		this.title = title;
		this.price = price;
		this.description = description;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.updator = updator;
		this.updatorTime = updatorTime;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", appType=" + appType + ", title="
				+ title + ", price=" + price + ", description=" + description
				+ ", remark=" + remark + ", creator=" + creator
				+ ", createTime=" + createTime + ", updator=" + updator
				+ ", updatorTime=" + updatorTime + "]";
	}

}
