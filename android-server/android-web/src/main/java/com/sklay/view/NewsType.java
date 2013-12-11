package com.sklay.view;

import java.io.Serializable;

public class NewsType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int type;

	public String attachment;

	public int authoruid2;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getAuthoruid2() {
		return authoruid2;
	}

	public void setAuthoruid2(int authoruid2) {
		this.authoruid2 = authoruid2;
	}

	@Override
	public String toString() {
		return "NewsType [type=" + type + ", attachment=" + attachment
				+ ", authoruid2=" + authoruid2 + "]";
	}
}