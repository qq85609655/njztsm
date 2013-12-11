package com.sklay.dao;

import java.util.Date;
import java.util.List;

import com.sklay.model.Comment;

public interface CmsDao {

	public List<Comment> getLatest(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

	public List<Comment> getMostCommented(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

	public List<Comment> getMostViewed(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

	public List<Comment> getMostLiked(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

}
