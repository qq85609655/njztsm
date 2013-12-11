package com.sklay.vo;

import java.io.Serializable;
import java.util.List;

public class TreeView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3638363649973502206L;

	private String id;

	private String name;

	private Object data;

	private List<TreeView> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<TreeView> getChildren() {
		return children;
	}

	public void setChildren(List<TreeView> children) {
		this.children = children;
	}

	public TreeView() {
		super();
	}

	public TreeView(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "TreeView [id=" + id + ", name=" + name + ", data=" + data
				+ ", children=" + children + "]";
	}

}
