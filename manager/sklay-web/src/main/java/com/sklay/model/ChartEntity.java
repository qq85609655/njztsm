package com.sklay.model;

import java.io.Serializable;
import java.util.List;

public class ChartEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> labels;

	private List<?> datasets;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<?> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<?> datasets) {
		this.datasets = datasets;
	}

	@Override
	public String toString() {
		return "ChartEntity [labels=" + labels + ", datasets=" + datasets + "]";
	}

}
