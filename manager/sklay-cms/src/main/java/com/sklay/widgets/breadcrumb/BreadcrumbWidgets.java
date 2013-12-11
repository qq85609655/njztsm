package com.sklay.widgets.breadcrumb;

import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;


@Widgets(value = "breadcrumb")
public class BreadcrumbWidgets {

	@Widget
	public String line() {
		return "line.tpl";
	}
}
