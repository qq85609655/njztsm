package com.sklay.track.attr;

import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public interface Attrable {
    boolean hasAttr(String key);

    <T> T getAttr(String key);

    <T> T getAttr(String key, Class<T> type);

    void setAttr(String key, Object value);

    Map<String, Object> getAttrs();
}
