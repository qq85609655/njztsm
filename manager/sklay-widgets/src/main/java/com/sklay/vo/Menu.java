package com.sklay.vo;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998510954138363786L;

	private String title;

	private String href;

	private String nav ;
	
	private List<Menu> child;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<Menu> getChild() {
		return child;
	}

	public void setChild(List<Menu> child) {
		this.child = child;
	}

	public String getNav() {
		return nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}


	@Override
	public String toString() {
		return "Menu [title=" + title + ", href=" + href + ", nav=" + nav
				+ ", child=" + child + "]";
	}

	public Menu() {
		super();
	}

	public Menu(String title) {
		super();
		this.title = title;
	}

	public Menu(String title, String href ,String nav) {
		super();
		this.title = title;
		this.href = href;
		this.nav = nav ;
	}

}
