package com.sklay.widgets.slider;

import java.util.Map;

import org.apache.shiro.util.StringUtils;
import org.springframework.ui.ModelMap;

import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;

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

	@Widget(settings = { @Setting(key = "images", name = "图片列表,用逗号分隔", value = "/widgetResource/slider/nivo-slider/demo/images/slide-4.jpg,/widgetResource/slider/nivo-slider/demo/images/slide-5.jpg", inputType = InputType.TEXTAREA) })
	public String carousel(com.sklay.model.Widget widget, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String images = settings.get("images");
		modelMap.put("images", images.split(","));
		return "carousel.tpl";
	}

	@Widget(settings = {
			@Setting(key = "codeBase", name = "swflash版本", value = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0", inputType = InputType.TEXTAREA),
			@Setting(key = "style", name = "css样式", value = "", inputType = InputType.TEXTAREA),
			@Setting(key = "movieSrc", value = "/widgetResource/slider/swf/logo.swf", name = "flash地址", inputType = InputType.TEXTAREA),
			@Setting(key = "itemWidth", value = "100%", name = "宽度", inputType = InputType.INPUT),
			@Setting(key = "itemHeight", value = "100%", name = "高度", inputType = InputType.INPUT) })
	public String swfSlider(com.sklay.model.Widget widget, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String images = settings.get("movieSrc");
		modelMap.put("movieSrc", images);
		return "swfSlider.tpl";
	}

	@Widget(settings = {
			@Setting(key = "content", name = "内容区块", value = " <a href=''><img src='/widgetResource/slider/nivo-slider/demo/images/slide-4.jpg' data-thumb='/widgetResource/slider/nivo-slider/demo/images/slide-5.jpg' alt='' title='This is an example of a caption' /></a>", inputType = InputType.TEXTAREA),
			@Setting(key = "options", name = "JS初始化选项", value = "{controlNav:false}", inputType = InputType.TEXTAREA) })
	public String nivoSlider() {
		return "nivoSlider.tpl";
	}

}
