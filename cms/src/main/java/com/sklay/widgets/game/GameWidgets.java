package com.sklay.widgets.game;

import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;

@Widgets("game")
public class GameWidgets {

	@Widget
	public String chess() {
		return "chess.tpl";
	}
	
}
