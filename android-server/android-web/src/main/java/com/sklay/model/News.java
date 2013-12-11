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

import com.sklay.enums.DelStatus;

/**
 * 新闻实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@Entity
@Table(name = "android_news")
public class News implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "body")
	@Lob
	private String body;

	@Column(name = "ver", columnDefinition = " int default 0")
	private int ver;

	@Column(name = "img_count", columnDefinition = " int default 0")
	private int imgCount = 0;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = true)
	private User creator;

	@Column(name = "comment_count", columnDefinition = "int default 0")
	private int commentCount;

	@Column(name = "favorite", columnDefinition = "int default 0")
	private int favorite;

	@Column(name = "pub_date")
	private Date pubDate;

	@Column(name = "software_link")
	private String softwareLink;

	@Column(name = "software_name")
	private String softwareName;

	@Column(columnDefinition = "int default 0")
	private DelStatus delStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getSoftwareLink() {
		return softwareLink;
	}

	public void setSoftwareLink(String softwareLink) {
		this.softwareLink = softwareLink;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}

	public DelStatus getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(DelStatus delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", body=" + body
				+ ", ver=" + ver + ", creator=" + creator + ", commentCount="
				+ commentCount + ", favorite=" + favorite + ", pubDate="
				+ pubDate + ", softwareLink=" + softwareLink
				+ ", softwareName=" + softwareName + "]";
	}

}
