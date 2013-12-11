package com.sklay.track.attr;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.util.WebUtils;

import com.sklay.track.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class AttrExtractContextImpl extends AbstractAttrable implements AttrExtractContext {
    private final HttpServletRequest request;
    private Integer tsDiff;

    public AttrExtractContextImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public String getHeader(String headerName) {
        return request.getHeader(headerName);
    }

    @Override
    public String getParameter(String paramName) {
        return request.getParameter(paramName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attrName) {
        return (T) request.getAttribute(attrName);
    }

    @Override
    public String getCookie(String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, Constants.COOKIE_CLIENT_ID);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    @Override
    public String getIp() {
        String ip = (String) request.getAttribute(Constants.IP);
        if (ip == null) {
            ip = com.sklay.core.web.util.WebUtils.getClientIp(request);
        }
        return ip;
    }

    @Override
    public Date adjustDate(Date date) {
        if (getTsDiff() == 0l) {
            return date;
        }
        return DateUtils.addMilliseconds(date, getTsDiff());
    }

    @Override
    public long adjustTs(long ts) {
        return ts + getTsDiff();
    }

    private int getTsDiff() {
        if (tsDiff == null) {
            tsDiff = (Integer) request.getAttribute(Constants.TIMESTAMP_DIFF);
            if (tsDiff == null) {
                tsDiff = 0;
            }
        }
        return tsDiff;
    }
}
