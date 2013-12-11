package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.AttrType;
import com.sklay.track.attr.Attrable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public class SingleAttrExtractor extends AbstractSingleAttrExtractor {
    private AttrType type;
    private String dictKey;
    private String parentKey;

    public SingleAttrExtractor(String key, String targetKey, AttrType type) {
        super(key, targetKey);
        this.type = type;
    }

    public SingleAttrExtractor(String key, AttrType type) {
        super(key);
        this.type = type;
    }

    public SingleAttrExtractor() {
    }

    public void setType(AttrType type) {
        this.type = type;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public void extract(Attrable entity, JSONObject data, AttrExtractContext context) {
        Object value;
        if (type == AttrType.DICT) {
            value = getDictItemId(dictKey == null ? getTargetKey() : dictKey, data.getString(getKey()), parentKey == null ? null : getInteger(parentKey, data, context));
        } else {
            value = getValue(getKey(), type, data, context);
        }
        entity.setAttr(getTargetKey(), value);
    }
}
