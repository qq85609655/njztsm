package com.sklay.core.sdk.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

/**
 * 处理cookies的一些方法.
 * @author ganqing
 *
 */
public class CookieUtils {
	/**
     * 获取cookie值.
     *
     * @param request HttpServletRequest
     * @param name    cookie名称
     * @return cookie的值
     */
    public static String getCookieValue(final HttpServletRequest request, final String name) {
        Cookie[] cookies = request.getCookies();
        // 添加cookie数组可能为空的判断.
        String cvalue = null;
        if (cookies != null) {
            Cookie cookie;
            for (int i = 0, n = cookies.length; i < n; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals(name)) {
                    cvalue = cookie.getValue();
                    break;
                }
            }
        }
        return cvalue;
    }
}
