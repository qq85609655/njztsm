package com.sklay.track.attr;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public interface AttrExtractContext extends Attrable {
    HttpServletRequest getRequest();

    String getHeader(String headerName);

    String getParameter(String paramName);

    <T> T getAttribute(String attrName);

    String getCookie(String cookieName);

    String getIp();

    Date adjustDate(Date date);

    long adjustTs(long ts);
}
