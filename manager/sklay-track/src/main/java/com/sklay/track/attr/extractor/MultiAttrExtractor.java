package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.*;
import com.sklay.track.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-14
 */
public class MultiAttrExtractor extends AbstractAttrExtractor implements AttrExtractor {
    private Set<AttrConfig> mapping = new HashSet<AttrConfig>();

    public void setMapping(Set<AttrConfig> mapping) {
        this.mapping.addAll(mapping);
    }

    public void setSimpleMapping(Map<String, AttrType> mapping) {
        for (Map.Entry<String, AttrType> entry : mapping.entrySet()) {
            AttrConfig config = new AttrConfig();
            config.setKey(entry.getKey());
            config.setType(entry.getValue());
            this.mapping.add(config);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void extract(Attrable entity, JSONObject data, AttrExtractContext context) {
        for (AttrConfig config : mapping) {
            String key = config.getKey();
            String targetKey = config.getTargetKey();
            AttrType type = config.getType();
            if (type == null) {
                Pair<String, AttrType> pair = guessType(key);
                if (targetKey == null) {
                    targetKey = pair.getKey();
                }
            }
            if (targetKey == null) {
                targetKey = key;
            }
            Object value;
            if (type == AttrType.DICT) {
                String dictKey = config.getDictKey();
                if (dictKey == null) {
                    dictKey = targetKey;
                }
                value = getDictItemId(dictKey, data.getString(key), config.getParentKey() == null ? null : getInteger(config.getParentKey(), data, context));
            } else {
                value = getValue(key, type, data, context);
            }
            entity.setAttr(targetKey, value);
        }
    }
}
