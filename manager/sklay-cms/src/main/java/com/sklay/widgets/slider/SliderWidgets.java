package com.sklay.widgets.slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.StringUtils;
import org.springframework.ui.ModelMap;

import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;
import com.sklay.core.support.Option;

@Widgets("slider")
public class SliderWidgets {

	@Widget(settings = {
			@Setting(key = "items", value = "", name = "元素列表,用逗号分隔", inputType = InputType.TEXTAREA),
			@Setting(key = "itemWidth", value = "", name = "宽度") })
	public String flexSlider(com.sklay.model.Widget widget, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String items = settings.get("items");
		modelMap.put("items", items.split(","));
		String itemWidth = settings.get("itemWidth");
		if (StringUtils.hasText(itemWidth)) {
			modelMap.put("itemWidth", itemWidth);
		}
		return "flexSlider.tpl";
	}

	@Widget(settings = { @Setting(key = "images", name = "图片列表,用逗号分隔", value = "/widgetResource/slider/demo/slide1.jpg,/widgetResource/slider/demo/slide2.jpg,/widgetResource/slider/demo/slide3.jpg") })
	public String carousel(com.sklay.model.Widget widget, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String images = settings.get("images");
		modelMap.put("images", images.split(","));
		return "carousel.tpl";
	}

	@Widget(settings = {
			@Setting(key = "content", name = "内容区块", value = " <a href=''><img src='/widgetResource/slider/nivo-slider/demo/images/up.jpg' data-thumb='/widgetResource/slider/nivo-slider/demo/images/up.jpg' alt='' title='This is an example of a caption' /></a>", inputType = InputType.TEXTAREA),
			@Setting(key = "options", name = "JS初始化选项", value = "{controlNav:false}", inputType = InputType.TEXTAREA),
			@Setting(key = "themes", name = "轮播主题", optionsLoader = "findThemeTypes", value = "default", inputType = InputType.SELECT) })
	public String nivoSlider() {
		return "nivoSlider.tpl";
	}

	public Option[] findThemeTypes() {

		List<Option> opts = new ArrayList<Option>();
		Option opt_default = new Option();
		opt_default.setName("黑色");
		opt_default.setValue("default");
		opts.add(opt_default);
		Option opt_bar = new Option();
		opt_bar.setName("横条");
		opt_bar.setValue("bar");
		opts.add(opt_bar);
		Option opt_dark = new Option();
		opt_dark.setName("深色");
		opt_dark.setValue("dark");
		opts.add(opt_dark);
		Option opt_light = new Option();
		opt_light.setName("光良");
		opt_light.setValue("light");
		opts.add(opt_light);

		return opts.toArray(new Option[opts.size()]);
	}
}
