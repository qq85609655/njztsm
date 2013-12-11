package com.sklay.view;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 新闻列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class NewsList extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int catalog;

	private int pageSize;

	private int newsCount;

	private List<News> newslist = Lists.newArrayList();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getNewsCount() {
		return newsCount;
	}

	public List<News> getNewslist() {
		return newslist;
	}

	public void setCatalog(int catalog) {
		this.catalog = catalog;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}

	public void setNewslist(List<News> newslist) {
		this.newslist = newslist;
	}

	public NewsList() {
		super();
	}

	public NewsList(int catalog, int pageSize, int newsCount,
			List<News> newslist) {
		super();
		this.catalog = catalog;
		this.pageSize = pageSize;
		this.newsCount = newsCount;
		this.newslist = newslist;
	}

	@Override
	public String toString() {
		return "NewsList [catalog=" + catalog + ", pageSize=" + pageSize
				+ ", newsCount=" + newsCount + ", newslist=" + newslist + "]";
	}

}
