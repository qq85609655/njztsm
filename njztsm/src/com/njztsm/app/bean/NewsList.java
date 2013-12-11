package com.njztsm.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.njztsm.app.AppException;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.constant.Constants;

import android.util.Xml;

/**
 * 新闻列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class NewsList extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int catalog;
	private int pageSize;
	private int newsCount;
	private List<News> newslist = new ArrayList<News>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getNewsCount() {
		return newsCount;
	}

	public List<News> getNewslist() {
		return newslist;
	}

	public static NewsList parse(InputStream inputStream) throws IOException,
			AppException {
		NewsList newslist = new NewsList();
		News news = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(inputStream, Constants.UTF8);
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {
				case XmlPullParser.START_TAG:
					if (tag.equalsIgnoreCase("catalog")) {
						newslist.catalog = StringUtils.toInt(
								xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase("pageSize")) {
						newslist.pageSize = StringUtils.toInt(
								xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase("newsCount")) {
						newslist.newsCount = StringUtils.toInt(
								xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase(Constants.News.NODE_START)) {
						news = new News();
					} else if (news != null) {
						if (tag.equalsIgnoreCase(Constants.News.NODE_ID)) {
							news.id = StringUtils
									.toInt(xmlParser.nextText(), 0);
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_TITLE)) {
							news.setTitle(xmlParser.nextText());
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_URL)) {
							news.setUrl(xmlParser.nextText());
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_AUTHOR)) {
							news.setAuthor(xmlParser.nextText());
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_AUTHORID)) {
							news.setAuthorId(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_COMMENTCOUNT)) {
							news.setCommentCount(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_PUBDATE)) {
							news.setPubDate(xmlParser.nextText());
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_TYPE)) {
							news.getNewType().type = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_ATTACHMENT)) {
							news.getNewType().attachment = xmlParser.nextText();
						} else if (tag
								.equalsIgnoreCase(Constants.News.NODE_AUTHORUID2)) {
							news.getNewType().authoruid2 = StringUtils.toInt(
									xmlParser.nextText(), 0);
						}
					}
					// 通知信息
					else if (tag.equalsIgnoreCase("notice")) {
						newslist.setNotice(new Notice());
					} else if (newslist.getNotice() != null) {
						if (tag.equalsIgnoreCase("atmeCount")) {
							newslist.getNotice().setAtmeCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("msgCount")) {
							newslist.getNotice().setMsgCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("reviewCount")) {
							newslist.getNotice().setReviewCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("newFansCount")) {
							newslist.getNotice().setNewFansCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					// 如果遇到标签结束，则把对象添加进集合中
					if (tag.equalsIgnoreCase("news") && news != null) {
						newslist.getNewslist().add(news);
						news = null;
					}
					break;
				}
				// 如果xml没有结束，则导航到下一个节点
				int a = xmlParser.next();
				evtType = a;
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw AppException.xml(e);
		} finally {
			inputStream.close();
		}
		return newslist;
	}
}