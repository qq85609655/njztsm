package com.sklay.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class PageModel<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5339531835214647672L;

	/** 记录数 */
	private int totalElements = 0;

	/** 总页数 */
	private int totalPages = 0;

	/** 当前页 */
	private int number = 0;

	/** 每页显示记录数 */
	private int size = 10;

	/** 每页显示的数据列表 */
	private List<E> content = null;

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<E> getContent() {
		return content;
	}

	public void setContent(List<E> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PageModel [totalElements=" + totalElements + ", totalPages="
				+ totalPages + ", number=" + number + ", size=" + size
				+ ", content=" + content + "]";
	}

	public PageModel() {
		super();
	}

	public PageModel(int totalElements, int number, int size, List<E> content) {
		super();
		this.totalElements = totalElements;
		this.number = number;
		this.size = size;
		this.content = content;

		if (CollectionUtils.isNotEmpty(content))
			this.totalPages = (totalElements + size - 1) / size;
	}

}
