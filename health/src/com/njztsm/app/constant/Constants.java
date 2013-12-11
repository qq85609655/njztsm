package com.njztsm.app.constant;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-29
 */
public class Constants {

	public static final String UTF8 = "UTF-8";
	public static final String DEFAULT_CHARSET = UTF8;
	public static final Charset CHARSET = Charset.forName(DEFAULT_CHARSET);
	public static final Pattern MOBILE_PATTERN = Pattern
			.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
	public final static String NODE_ROOT = "njztsm";
	public final static int ERROR = 0;
	public final static int SUCCESS = 1;

	public final static int CLIENT_MOBILE = 2;
	public final static int CLIENT_ANDROID = 3;
	public final static int CLIENT_IPHONE = 4;
	public final static int CLIENT_WINDOWS_PHONE = 5;

	public static class QUICKACTION {
		public static final int LOGIN_OR_LOGOUT = 0;
		public static final int USERINFO = 1;
		public static final int USERREPORT = 2;
		public static final int SETTING = 3;
		public static final int EXIT = 4;
	}

	public static class CHART_QUICKACTION {
		public static final int SETTING = 0;
		public static final int EXIT = 1;
	}

	public static class Active {
		public final static int CATALOG_OTHER = 0;// 其他
		public final static int CATALOG_NEWS = 1;// 新闻
		public final static int CATALOG_POST = 2;// 帖子
		public final static int CATALOG_TWEET = 3;// 动弹
		public final static int CATALOG_BLOG = 4;// 博客

	}

	public static class Comment {
		public final static int CATALOG_NEWS = 1;
		public final static int CATALOG_POST = 2;
		public final static int CATALOG_TWEET = 3;
		public final static int CATALOG_ACTIVE = 4;
		public final static int CATALOG_MESSAGE = 4;// 动态与留言都属于消息中心
	}

	public static class Favorite {
		public final static int TYPE_ALL = 0x00;
		public final static int TYPE_SOFTWARE = 0x01;
		public final static int TYPE_POST = 0x02;
		public final static int TYPE_BLOG = 0x03;
		public final static int TYPE_NEWS = 0x04;
		public final static int TYPE_CODE = 0x05;
	}

	public static class Friend {
		public final static int TYPE_FANS = 0x00;
		public final static int TYPE_FOLLOWER = 0x01;
	}

	public static class News {

		public final static int NEWSTYPE_NEWS = 0x01;// 1 新闻

		/** 最新 */
		public final static int CATALOG_LATEST = 1;
		/** 热门 */
		public final static int CATALOG_HOT = 2;
		/** 推荐 */
		public final static int CATALOG_RECOMMEND = 3;

		public final static String NODE_ID = "id";
		public final static String NODE_TITLE = "title";
		public final static String NODE_URL = "url";
		public final static String NODE_BODY = "body";
		public final static String NODE_AUTHORID = "authorid";
		public final static String NODE_AUTHOR = "author";
		public final static String NODE_PUBDATE = "pubDate";
		public final static String NODE_COMMENTCOUNT = "commentCount";
		public final static String NODE_FAVORITE = "favorite";
		public final static String NODE_START = "news";

		public final static String NODE_SOFTWARELINK = "softwarelink";
		public final static String NODE_SOFTWARENAME = "softwarename";

		public final static String NODE_NEWSTYPE = "newstype";
		public final static String NODE_TYPE = "type";
		public final static String NODE_ATTACHMENT = "attachment";
		public final static String NODE_AUTHORUID2 = "authoruid2";

	}

	public static class Post {

		public final static String NODE_ID = "id";
		public final static String NODE_TITLE = "title";
		public final static String NODE_URL = "url";
		public final static String NODE_FACE = "portrait";
		public final static String NODE_BODY = "body";
		public final static String NODE_AUTHORID = "authorid";
		public final static String NODE_AUTHOR = "author";
		public final static String NODE_PUBDATE = "pubDate";
		public final static String NODE_ANSWERCOUNT = "answerCount";
		public final static String NODE_VIEWCOUNT = "viewCount";
		public final static String NODE_FAVORITE = "favorite";
		public final static String NODE_START = "post";

		public final static int CATALOG_ASK = 1;
		public final static int CATALOG_SHARE = 2;
		public final static int CATALOG_OTHER = 3;
		public final static int CATALOG_JOB = 4;
		public final static int CATALOG_SITE = 5;

	}

	/** TODO 没用被引用 */
	public static class Relation {

		public final static int ACTION_DELETE = 0x00;// 取消关注
		public final static int ACTION_ADD = 0x01;// 加关注

		public final static int TYPE_BOTH = 0x01;// 双方互为粉丝
		public final static int TYPE_FANS_HIM = 0x02;// 你单方面关注他
		public final static int TYPE_NULL = 0x03;// 互不关注
		public final static int TYPE_FANS_ME = 0x04;// 只有他关注我
	}

	/** TODO 没用被引用 */
	public static class Notice {
		public final static int TYPE_ATME = 1;
		public final static int TYPE_MESSAGE = 2;
		public final static int TYPE_COMMENT = 3;
		public final static int TYPE_NEWFAN = 4;
	}
}
