package com.sklay.track.attr.extractor;

import com.sklay.track.attr.Attrable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public abstract class AbstractSingleAttrExtractor<T extends Attrable> extends AbstractAttrExtractor<T> {
    private String key;
    private String targetKey;

    protected AbstractSingleAttrExtractor(String key, String targetKey) {
        this.key = key;
        this.targetKey = targetKey;
    }

    protected AbstractSingleAttrExtractor(String key) {
        this.key = key;
    }

    protected AbstractSingleAttrExtractor() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTargetKey() {
        return targetKey == null ? key : targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }
}
