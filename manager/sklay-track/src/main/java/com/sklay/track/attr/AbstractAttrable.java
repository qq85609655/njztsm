package com.sklay.track.attr;

import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.data.annotation.Transient;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-14
 */
public abstract class AbstractAttrable implements Attrable {
    @Transient
    private Map<String, Object> attrs = new HashMap<String, Object>();

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    @Override
    public boolean hasAttr(String key) {
        return attrs.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttr(String key) {
        return (T) attrs.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttr(String key, Class<T> type) {
        Object value = attrs.get(key);
        if (value == null) {
            return null;
        }
        if (type == value.getClass()) {
            return (T) value;
        }
        return TypeUtils.castToJavaBean(attrs.get(key), type);
    }

    public void setAttr(String key, Object value) {
        if (value != null) {
            attrs.put(key, value);
        }
    }
}
