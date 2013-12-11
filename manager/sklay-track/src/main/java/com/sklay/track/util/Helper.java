package com.sklay.track.util;

import org.springframework.web.util.WebUtils;

import com.sklay.core.util.UUIDUtils;
import com.sklay.track.Constants;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class Helper {
    private Helper() {
    }

    public static String uuid() {
        return UUIDUtils.random(12);
    }

    public static String getCookie(String key, HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public static void setupTs(HttpServletRequest request) {//客户端时间戳
        try {
            String tsString = request.getParameter(Constants.TIMESTAMP);
            if (tsString != null) {
                request.setAttribute(Constants.TIMESTAMP_DIFF, System.currentTimeMillis() - Long.parseLong(tsString));
            }
        } catch (NumberFormatException ignored) {
        }
    }
}
