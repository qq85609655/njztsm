package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.AttrType;
import com.sklay.track.attr.Attrable;
import com.sklay.track.util.Pair;

import java.util.Set;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-15
 */
public class CustomsAttrExtractor extends AbstractAttrExtractor {
    private String customsKey;
    private Set<String> includes;
    private Set<String> excludes;

    public void setCustomsKey(String customsKey) {
        this.customsKey = customsKey;
    }

    public void setIncludes(Set<String> includes) {
        this.includes = includes;
    }

    public void setExcludes(Set<String> excludes) {
        this.excludes = excludes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void extract(Attrable entity, JSONObject data, AttrExtractContext context) {
        if (customsKey != null) {
            data = data.getJSONObject(customsKey);
        }
        if (data != null) {
            for (String key : data.keySet()) {
                if (excludes != null && excludes.contains(key)) {
                    continue;
                }
                Pair<String, AttrType> pair = guessType(key);
                if (includes != null && !includes.contains(pair.getKey())) {
                    continue;
                }
                Object value = convertValue(pair, data.get(key), context);
                if (value != null) {
                    entity.setAttr(pair.getKey(), value);
                }
            }
        }
    }
}
