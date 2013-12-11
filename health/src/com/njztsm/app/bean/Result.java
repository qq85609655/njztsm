package com.njztsm.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.njztsm.app.AppException;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.constant.Constants;

import android.annotation.SuppressLint;
import android.util.Xml;

/**
 * 数据操作结果实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("DefaultLocale")
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage;

	protected Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public boolean OK() {
		return errorCode == 1;
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static Result parse(InputStream stream) throws IOException,
			AppException {
		Result res = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(stream, Constants.UTF8);
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {

				case XmlPullParser.START_TAG:
					// 如果是标签开始，则说明需要实例化对象了
					if (tag.equalsIgnoreCase("result")) {
						res = new Result();
					} else if (res != null) {
						if (tag.equalsIgnoreCase("errorCode")) {
							res.errorCode = StringUtils.toInt(
									xmlParser.nextText(), -1);
						} else if (tag.equalsIgnoreCase("errorMessage")) {
							res.errorMessage = xmlParser.nextText().trim();
						}
						// 通知信息
						else if (tag.equalsIgnoreCase("notice")) {
							res.setNotice(new Notice());
						} else if (res.getNotice() != null) {
							if (tag.equalsIgnoreCase("atmeCount")) {
								res.getNotice().setAtmeCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("msgCount")) {
								res.getNotice().setMsgCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("reviewCount")) {
								res.getNotice().setReviewCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("newFansCount")) {
								res.getNotice().setNewFansCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							}
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
			stream.close();
		}

		return res;

	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return String.format("RESULT: CODE:%d,MSG:%s", errorCode, errorMessage);
	}

}
