package com.sklay.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新闻实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class News extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String url;
	private String body;
	private String author;
	private int authorId;
	private int commentCount;
	private Date pubDate;
	private String softwareLink;
	private String softwareName;
	private int favorite;
	private NewsType newType;
	private List<Relative> relatives;

	public News() {
		this.newType = new NewsType();
		this.relatives = new ArrayList<Relative>();
	}

	public List<Relative> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}

	public NewsType getNewType() {
		return newType;
	}

	public void setNewType(NewsType newType) {
		this.newType = newType;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
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

	public Date getPubDate() {
		return this.pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", url=" + url + ", body=" + body
				+ ", author=" + author + ", authorId=" + authorId
				+ ", commentCount=" + commentCount + ", pubDate=" + pubDate
				+ ", softwareLink=" + softwareLink + ", softwareName="
				+ softwareName + ", favorite=" + favorite + ", newType="
				+ newType + ", relatives=" + relatives + "]";
	}

}
