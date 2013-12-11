package com.sklay.core.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.sklay.core.support.Setting;
import com.sklay.core.enums.WidgetLevel;

public class WidgetInfo {

	private String region;

	private String name;

	private String description;

	private List<WidgetInfo> child;

	private String checked;

	private String uri;

	private WidgetLevel level;

	private boolean show;

	private Object handler;

	private Method method;

	private List<Setting> settings = new ArrayList<Setting>();

	private Map<String, String> settingsMap = new HashMap<String, String>();

	private Method onAdd;

	private Method onEdit;

	private Method onRemove;

	private String[] js;

	private String[] css;

	public WidgetInfo() {
		super();
	}

	public WidgetInfo(String name, String description, String uri,
			WidgetLevel level, boolean show) {
		super();
		this.name = name;
		this.description = description;
		this.uri = uri;
		this.level = level;
		this.show = show;
	}

	public WidgetInfo(String name, String description, WidgetLevel level,
			boolean show, List<WidgetInfo> child) {
		super();
		this.name = name;
		this.description = description;
		this.level = level;
		this.show = show;
		this.child = CollectionUtils.isEmpty(child) ? null : child;
	}

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

	public List<WidgetInfo> getChild() {
		return child;
	}

	public void setChild(List<WidgetInfo> child) {
		this.child = child;
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

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean isShow) {
		this.show = isShow;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public List<Setting> getSettings() {
		return settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	public Map<String, String> getSettingsMap() {
		return settingsMap;
	}

	public void setSettingsMap(Map<String, String> settingsMap) {
		this.settingsMap = settingsMap;
	}

	public Method getOnAdd() {
		return onAdd;
	}

	public void setOnAdd(Method onAdd) {
		this.onAdd = onAdd;
	}

	public Method getOnEdit() {
		return onEdit;
	}

	public void setOnEdit(Method onEdit) {
		this.onEdit = onEdit;
	}

	public Method getOnRemove() {
		return onRemove;
	}

	public void setOnRemove(Method onRemove) {
		this.onRemove = onRemove;
	}

	public String[] getJs() {
		return js;
	}

	public void setJs(String[] js) {
		this.js = js;
	}

	public String[] getCss() {
		return css;
	}

	public void setCss(String[] css) {
		this.css = css;
	}

	public void addSetting(Setting setting) {
		this.settings.add(setting);
		this.settingsMap.put(setting.getKey(), setting.getValue());
	}

	@Override
	public String toString() {
		return "WidgetInfo [region=" + region + ", name=" + name
				+ ", description=" + description + ", child=" + child
				+ ", checked=" + checked + ", uri=" + uri + ", level=" + level
				+ ", show=" + show + ", handler=" + handler + ", method="
				+ method + ", settings=" + settings + ", settingsMap="
				+ settingsMap + ", onAdd=" + onAdd + ", onEdit=" + onEdit
				+ ", onRemove=" + onRemove + ", show=" + show + ", js="
				+ Arrays.toString(js) + ", css=" + Arrays.toString(css) + "]";
	}

}
