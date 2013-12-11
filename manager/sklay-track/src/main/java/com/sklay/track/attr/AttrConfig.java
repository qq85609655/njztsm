package com.sklay.track.attr;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-19
 */
public final class AttrConfig {
    /**
     * 属性名
     */
    private String key;
    /**
     * 映射到的属性名
     */
    private String targetKey;
    /**
     * 对应的字典名
     */
    private String dictKey;
    /**
     * 字典父键的属性名
     */
    private String parentKey;
    /**
     * 属性类型
     */
    private AttrType type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public AttrType getType() {
        return type;
    }

    public void setType(AttrType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AttrConfig && key.equals(((AttrConfig)obj).getKey());
    }
}
