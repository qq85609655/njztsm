package com.sklay.widgets.pushs;

import com.sklay.model.NewsGroup;
import com.sklay.model.Page;

public class TypeAndPage {

	private NewsGroup type;
	private Page page;
	private Page detailPage;
	public NewsGroup getType() {
		return type;
	}
	public void setType(NewsGroup type) {
		this.type = type;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public Page getDetailPage() {
		return detailPage;
	}
	public void setDetailPage(Page detailPage) {
		this.detailPage = detailPage;
	}
	@Override
	public String toString() {
		return "TypeAndPage [type=" + type + ", page=" + page + ", detailPage="
				+ detailPage + "]";
	}
}
