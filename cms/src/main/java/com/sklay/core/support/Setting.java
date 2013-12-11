package com.sklay.core.support;

import java.lang.reflect.Method;

import com.sklay.core.enums.InputType;

public class Setting {

	private String key;

	private String value;

	private String name;

	private Option[] options;

	private Method optionsLoader;

	private InputType inputType;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public InputType getInputType() {
		return inputType;
	}

	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public Method getOptionsLoader() {
		return optionsLoader;
	}

	public void setOptionsLoader(Method optionsLoader) {
		this.optionsLoader = optionsLoader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
