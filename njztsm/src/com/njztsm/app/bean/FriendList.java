package com.njztsm.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.njztsm.app.AppException;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.constant.Constants;

import android.util.Xml;

/**
 * 好友列表实体类
 */
public class FriendList extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Friend> friendlist = new ArrayList<Friend>();

	/**
	 * 好友实体类
	 */
	public static class Friend implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int userid;
		private String name;
		private String face;
		private String expertise;
		private int gender;

		public int getUserid() {
			return userid;
		}

		public void setUserid(int userid) {
			this.userid = userid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFace() {
			return face;
		}

		public void setFace(String face) {
			this.face = face;
		}

		public String getExpertise() {
			return expertise;
		}

		public void setExpertise(String expertise) {
			this.expertise = expertise;
		}

		public int getGender() {
			return gender;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}
	}

	public List<Friend> getFriendlist() {
		return friendlist;
	}

	public void setFriendlist(List<Friend> resultlist) {
		this.friendlist = resultlist;
	}

	public static FriendList parse(InputStream inputStream) throws IOException,
			AppException {
		FriendList friendlist = new FriendList();
		Friend friend = null;
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
					if (tag.equalsIgnoreCase("friend")) {
						friend = new Friend();
					} else if (friend != null) {
						if (tag.equalsIgnoreCase("userid")) {
							friend.userid = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase("name")) {
							friend.name = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase("portrait")) {
							friend.face = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase("expertise")) {
							friend.expertise = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase("gender")) {
							friend.gender = StringUtils.toInt(
									xmlParser.nextText(), 0);
						}
					}
					// 通知信息
					else if (tag.equalsIgnoreCase("notice")) {
						friendlist.setNotice(new Notice());
					} else if (friendlist.getNotice() != null) {
						if (tag.equalsIgnoreCase("atmeCount")) {
							friendlist.getNotice().setAtmeCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("msgCount")) {
							friendlist.getNotice().setMsgCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("reviewCount")) {
							friendlist.getNotice().setReviewCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("newFansCount")) {
							friendlist.getNotice().setNewFansCount(
									StringUtils.toInt(xmlParser.nextText(), 0));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					// 如果遇到标签结束，则把对象添加进集合中
					if (tag.equalsIgnoreCase("friend") && friend != null) {
						friendlist.getFriendlist().add(friend);
						friend = null;
					}
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
		return friendlist;
	}
}
