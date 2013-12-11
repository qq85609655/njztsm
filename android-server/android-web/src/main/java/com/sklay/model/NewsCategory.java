package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sklay.enums.AuditStatus;

@Entity
@Table(name = "android_news_category")
public class NewsCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NewsCategoryPK newsCategory;

	private AuditStatus status;

	@Column(name = "createTime")
	private Date createTime;;

	public NewsCategoryPK getNewsCategory() {
		return newsCategory;
	}

	public void setNewsCategory(NewsCategoryPK newsCategory) {
		this.newsCategory = newsCategory;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "NewsCategory [newsCategory=" + newsCategory + ", status="
				+ status + ", createTime=" + createTime + "]";
	}

}
