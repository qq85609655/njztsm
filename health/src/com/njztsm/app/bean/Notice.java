package com.njztsm.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.njztsm.app.AppException;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.constant.Constants;

import android.util.Xml;

/**
 * 通知信息实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int atmeCount;
	private int msgCount;
	private int reviewCount;
	private int newFansCount;

	public int getAtmeCount() {
		return atmeCount;
	}

	public void setAtmeCount(int atmeCount) {
		this.atmeCount = atmeCount;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getNewFansCount() {
		return newFansCount;
	}

	public void setNewFansCount(int newFansCount) {
		this.newFansCount = newFansCount;
	}

	public static Notice parse(InputStream inputStream) throws IOException,
			AppException {
		Notice notice = null;
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
					// 通知信息
					if (tag.equalsIgnoreCase("notice")) {
						notice = new Notice();
					} else if (notice != null) {
						if (tag.equalsIgnoreCase("atmeCount")) {
							notice.setAtmeCount(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("msgCount")) {
							notice.setMsgCount(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("reviewCount")) {
							notice.setReviewCount(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("newFansCount")) {
							notice.setNewFansCount(StringUtils.toInt(
									xmlParser.nextText(), 0));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				// 如果xml没有结束，则导航到下一个节点
				evtType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			throw AppException.xml(e);
		} finally {
			inputStream.close();
		}
		return notice;
	}
}
