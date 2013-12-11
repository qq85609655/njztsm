package com.sklay.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class NewsCategoryPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8356126302908008211L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "newsId", nullable = false)
	private News news;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId", nullable = false)
	private NewsType newsType;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(NewsType newsType) {
		this.newsType = newsType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((news == null) ? 0 : news.hashCode());
		result = prime * result
				+ ((newsType == null) ? 0 : newsType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsCategoryPK other = (NewsCategoryPK) obj;
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		if (newsType == null) {
			if (other.newsType != null)
				return false;
		} else if (!newsType.equals(other.newsType))
			return false;
		return true;
	}

}
