package com.sklay.widgets.comment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.util.JsonUtils;
import com.sklay.event.EventPublisher;
import com.sklay.model.Comment;
import com.sklay.service.CommentService;

@Widgets("comment")
public class CommentWidgets {

	@Autowired
	private CommentService commentService;
	
//	@Autowired
//	private CmsService cmsService;
	
	@Autowired
	private EventPublisher eventPublisher;
	
	@Widget(settings = { @Setting(key = "limit", value = "10",name="数量"),
			@Setting(key = "biz", value = "",name="业务标识"),
			@Setting(key = "ownerParam", value = "",name="接受来自页面的参数作为owenr") })
	public String pagination(com.sklay.model.Widget widget,
			@RequestParam(defaultValue="1")Integer page, WebRequest request, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String ownerParam = settings.get("ownerParam");
		String owner = null;
		if (!StringUtils.isEmpty(ownerParam)) {
			owner = request.getParameter(ownerParam);
		}
		if(owner==null){
			owner="";
		}
		String biz = settings.get("biz");
		PageRequest pageRequest = new PageRequest(page - 1,
				NumberUtils.parseNumber(settings.get("limit"), Integer.class));
		modelMap.put("biz", biz);
		modelMap.put("owner", owner);
		modelMap.put("page", commentService.getComments(pageRequest,biz, owner));
		return "comment-pagination.tpl";
	}

	public void addComment(com.sklay.model.Widget actionFrom,Comment comment,ModelMap modelMap) {
		Map<String, String> settings = actionFrom.getSettings();
		String ownerParam = settings.get("ownerParam");
		modelMap.put(ownerParam, comment.getOwner());
		commentService.save(comment, 50, null);
		eventPublisher.publish(Constants.EventTopic.ADD_COMMENT_TO_BIZ_AND_OWNER, JsonUtils.toJSON(comment));
	}
}
