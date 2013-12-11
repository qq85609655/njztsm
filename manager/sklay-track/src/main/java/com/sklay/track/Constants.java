package com.sklay.track;

import java.nio.charset.Charset;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-29
 */
public class Constants {
    private Constants() {
    }

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final Charset CHARSET = Charset.forName(DEFAULT_CHARSET);

    public static final String DOMAIN = "domain";
    public static final String SESSION_DOMAIN = "sDomain";
    public static final String SESSION_TIMEOUT = "sTimeout";

    public static final String COOKIE_CLIENT_ID = "ak_c";
    public static final String DATA = "d";
    public static final String TYPE = "t";
    public static final String TIMESTAMP = "s";

    public static final String IS_NEW = "isNew";
    public static final String ID = "id";
    public static final String CLIENT_ID = "ci";
    public static final String SESSION_ID = "si";
    public static final String EVENT_ID = "ei";
    public static final String IP = "__ip";
    public static final String TIMESTAMP_DIFF = "__ts_diff";

    public static final String CUSTOMS_KEY = "cs";

    public static final String P3P = "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA div COM NAV OTC NOI DSP COR\"";
}
