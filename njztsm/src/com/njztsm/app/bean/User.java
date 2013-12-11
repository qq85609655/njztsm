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
 * 登录用户实体类
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int uid;
	private String location;
	private String name;
	private int followers;
	private int fans;
	private int score;
	private String face;
	private String account;
	private String pwd;
	private Result validate;
	private boolean isRememberMe;

	private String jointime;
	private String gender;
	private String devplatform;
	private String expertise;
	private int relation;
	private String latestonline;

	protected Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public boolean isRememberMe() {
		return isRememberMe;
	}

	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}

	public String getJointime() {
		return jointime;
	}

	public void setJointime(String jointime) {
		this.jointime = jointime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDevplatform() {
		return devplatform;
	}

	public void setDevplatform(String devplatform) {
		this.devplatform = devplatform;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public String getLatestonline() {
		return latestonline;
	}

	public void setLatestonline(String latestonline) {
		this.latestonline = latestonline;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public Result getValidate() {
		return validate;
	}

	public void setValidate(Result validate) {
		this.validate = validate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public static User parse(InputStream stream) throws IOException,
			AppException {
		User user = new User();
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
					} else if (tag.equalsIgnoreCase("errorCode")) {
						res.setErrorCode(StringUtils.toInt(
								xmlParser.nextText(), -1));
					} else if (tag.equalsIgnoreCase("errorMessage")) {
						res.setErrorMessage(xmlParser.nextText().trim());
					} else if (res != null && res.OK()) {
						if (tag.equalsIgnoreCase("uid")) {
							user.uid = StringUtils.toInt(xmlParser.nextText(),
									0);
						} else if (tag.equalsIgnoreCase("location")) {
							user.setLocation(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase("name")) {
							user.setName(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase("followers")) {
							user.setFollowers(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("fans")) {
							user.setFans(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("score")) {
							user.setScore(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("portrait")) {
							user.setFace(xmlParser.nextText());
						}
						// 通知信息
						else if (tag.equalsIgnoreCase("notice")) {
							user.setNotice(new Notice());
						} else if (user.getNotice() != null) {
							if (tag.equalsIgnoreCase("atmeCount")) {
								user.getNotice().setAtmeCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("msgCount")) {
								user.getNotice().setMsgCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("reviewCount")) {
								user.getNotice().setReviewCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							} else if (tag.equalsIgnoreCase("newFansCount")) {
								user.getNotice().setNewFansCount(
										StringUtils.toInt(xmlParser.nextText(),
												0));
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:
					// 如果遇到标签结束，则把对象添加进集合中
					if (tag.equalsIgnoreCase("result") && res != null) {
						user.setValidate(res);
					}
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
		return user;
	}
}
