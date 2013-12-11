package com.sklay.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.sklay.core.support.AuthBase;

public class AuthorEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String checked = "";

	private AuthBase authBase;

	private List<AuthorEntity> child = Lists.newArrayList();

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public AuthBase getAuthBase() {
		return authBase;
	}

	public void setAuthBase(AuthBase authBase) {
		this.authBase = authBase;
	}

	public AuthorEntity() {
		super();
	}

	public AuthorEntity(String checked, AuthBase authBase) {
		super();
		this.checked = checked;
		this.authBase = authBase;
	}

	@Override
	public String toString() {
		return "AuthorEntity [checked=" + checked + ", authBase=" + authBase
				+ "]";
	}

	public List<AuthorEntity> getChild() {
		return child;
	}

	public void setChild(List<AuthorEntity> child) {
		this.child = child;
	}

}
