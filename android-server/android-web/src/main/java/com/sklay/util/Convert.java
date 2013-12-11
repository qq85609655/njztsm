package com.sklay.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.sklay.view.News;
import com.sklay.view.NewsType;
import com.sklay.view.Notice;

public class Convert {

	public static News toApiNews(com.sklay.model.News news, Notice notice,
			com.sklay.model.NewsType newsType, boolean hasBody) {
		if (null == news)
			return null;
		News out = new News();

		if (null != newsType) {
			NewsType type = toApiNewsType(newsType);
			out.setNewType(type);
		}

		if (null != notice)
			out.setNotice(notice);
		if (hasBody)
			out.setBody(news.getBody());

		out.setId(news.getId().intValue());
		out.setCommentCount(news.getCommentCount());
		out.setFavorite(news.getFavorite());
		out.setTitle(news.getTitle());
		out.setAuthor(news.getCreator().getNickName());
		out.setAuthorId(news.getCreator().getId().intValue());
		out.setPubDate(news.getPubDate());
		out.setSoftwareLink(news.getSoftwareLink());
		out.setSoftwareName(news.getSoftwareName());

		return out;
	}

	public static NewsType toApiNewsType(com.sklay.model.NewsType newsType) {
		if (null == newsType)
			return null;
		NewsType out = new NewsType();

		out.setAttachment(newsType.getAttachment());
		out.setType(newsType.getId().intValue());

		return out;
	}

	public static List<News> toApiNewsList(List<com.sklay.model.News> newsList,
			Notice notice, com.sklay.model.NewsType newsType, boolean hasBody) {
		if (CollectionUtils.isEmpty(newsList))
			return null;
		List<News> out = Lists.newArrayList();
		for (com.sklay.model.News news : newsList) {
			News outNews = toApiNews(news, notice, newsType, hasBody);

			if (null != outNews)
				out.add(outNews);
		}

		return out;
	}
}
