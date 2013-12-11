package com.sklay.widgets.pushs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sklay.dao.CommentDao;
import com.sklay.model.Comment;
import com.sklay.widgets.comment.Constants;
import com.sklay.core.util.JsonUtils;
import com.sklay.event.EventComsumer;

@Component
public class AddCommentToBizAndOwnerComsumer implements EventComsumer {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public void resolve(String json) throws Exception {
		Comment comment = JsonUtils.toType(json, Comment.class);
		if("newsComment".endsWith(comment.getBiz())){
			String owner = comment.getOwner();
			Comment c = commentDao.findOne(Long.valueOf(owner));
			if(c!=null){
				c.setCommented(c.getCommented()+1);
			}
			commentDao.save(c);
		}
	}

	@Override
	public boolean accept(String topic) {
		return Constants.EventTopic.ADD_COMMENT_TO_BIZ_AND_OWNER.equals(topic);
	}

}
