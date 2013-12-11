package com.sklay.widgets.tags;

import org.springframework.ui.ModelMap;

import com.sklay.core.annotation.NameAndValue;
import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;

@Widgets("tags")
public class TagsWidgets {

	@Widget(settings = {
			@Setting(key = "offset", value = "0", name = "偏移量"),
			@Setting(key = "limit", value = "10", name = "显示个数"),
			@Setting(key = "showType", value = "true", name = "是否显示类型"),
			@Setting(key = "dateRegion", name = "时间范围", inputType = InputType.SELECT, options = {
					@NameAndValue(name = "24小时内", value = "1"),
					@NameAndValue(name = "一周内", value = "7"),
					@NameAndValue(name = "一月内", value = "30"),
					@NameAndValue(name = "一季度内", value = "90"),
					@NameAndValue(name = "一年内", value = "365") }),
			@Setting(key = "logic", value = "0", name = "逻辑", options = {
					@NameAndValue(name = "最新", value = "0"),
					@NameAndValue(name = "最多查看", value = "1"),
					@NameAndValue(name = "最多回复", value = "2"),
					@NameAndValue(name = "最多喜欢", value = "3"),
					@NameAndValue(name = "别人正在看", value = "4") }, inputType = InputType.SELECT),
			@Setting(key = "types", value = "[]", name = "类型", optionsLoader = "findLeafTypes", inputType = InputType.CHECKBOX),
			@Setting(key = "width", value = "600px", name = "宽度"),
			@Setting(key = "height", value = "600px", name = "高度") })
	public String ball(com.sklay.model.Widget widget, ModelMap modelMap) {
		// Map<String, String> settings = widget.getSettings();
		// String items = settings.get("items");
		// modelMap.put("items", items.split(","));
		// String itemWidth = settings.get("itemWidth");
		// if (StringUtils.hasText(itemWidth)) {
		// modelMap.put("itemWidth", itemWidth);
		// }
		return "ball.tpl";
	}

}
