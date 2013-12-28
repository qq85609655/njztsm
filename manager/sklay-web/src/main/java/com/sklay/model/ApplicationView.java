package com.sklay.model;

public class ApplicationView extends Application {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User creatorUser;

	private User updatorUser;

	public User getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}

	public User getUpdatorUser() {
		return updatorUser;
	}

	public void setUpdatorUser(User updatorUser) {
		this.updatorUser = updatorUser;
	}
}
