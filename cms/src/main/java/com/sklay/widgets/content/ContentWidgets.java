package com.sklay.widgets.content;

import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;

@Widgets("content")
public class ContentWidgets {

	@Widget(settings={@Setting(key="content",value="",name="内容",inputType=InputType.TEXTAREA)})
	public String simple(){
		return "simple.tpl";
	}
}
