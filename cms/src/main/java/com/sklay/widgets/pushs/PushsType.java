package com.sklay.widgets.pushs;

import com.sklay.core.io.image.ImgConfig;
import com.sklay.model.NewsGroup;

public class PushsType {

	private NewsGroup group;

	private ImgConfig imgConfig;

	public NewsGroup getGroup() {
		return group;
	}

	public void setGroup(NewsGroup group) {
		this.group = group;
	}

	public ImgConfig getImgConfig() {
		return imgConfig;
	}

	public void setImgConfig(ImgConfig imgConfig) {
		this.imgConfig = imgConfig;
	}
}
