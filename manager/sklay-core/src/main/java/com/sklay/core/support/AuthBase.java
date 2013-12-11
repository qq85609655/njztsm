package com.sklay.core.support;

import java.io.Serializable;
import java.util.List;

import com.sklay.core.enums.WidgetLevel;

public class AuthBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private String uri;

	private WidgetLevel level;

	private boolean show;

	private List<AuthBase> childBase;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public WidgetLevel getLevel() {
		return level;
	}

	public void setLevel(WidgetLevel level) {
		this.level = level;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public List<AuthBase> getChildBase() {
		return childBase;
	}

	public void setChildBase(List<AuthBase> childBase) {
		this.childBase = childBase;
	}

	public AuthBase() {
		super();
	}

	public AuthBase(String name, String description, WidgetLevel level,
			boolean show) {
		super();
		this.name = name;
		this.description = description;
		this.level = level;
		this.show = show;
	}

	public AuthBase(String name, String description, String uri,
			WidgetLevel level, boolean show) {
		super();
		this.name = name;
		this.description = description;
		this.uri = uri;
		this.level = level;
		this.show = show;
	}

	@Override
	public String toString() {
		return "AuthBase [name=" + name + ", description=" + description
				+ ", uri=" + uri + ", level=" + level + ", show=" + show
				+ ", childBase=" + childBase + "]";
	}

}
