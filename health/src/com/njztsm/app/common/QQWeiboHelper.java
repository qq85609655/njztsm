package com.njztsm.app.common;

import java.net.URLEncoder;

import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * 腾讯微博帮助类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-22
 */
public class QQWeiboHelper {
	
	private static final String Share_URL = "http://share.v.t.qq.com/index.php?c=share&a=index";
	private static final String Share_Source = "健康管家";
	private static final String Share_Site = "njztsm.net";
	private static final String Share_AppKey = "6362633ac5acc0b1811071cbb30679ff";
	
	/**
	 * 分享到腾讯微博
	 * @param activity
	 * @param title
	 * @param url
	 */
	public static void shareToQQ(Activity activity,String title,String url){
		String URL = Share_URL;
		try {
			URL += "&title=" + URLEncoder.encode(title, HTTP.UTF_8) + "&url=" + URLEncoder.encode(url, HTTP.UTF_8) + "&appkey=" + Share_AppKey + "&source=" + Share_Source + "&site=" + Share_Site;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		Uri uri = Uri.parse(URL);
		activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));	
	}
}