package com.sklay.track.attr;

import com.alibaba.fastjson.JSONObject;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public interface AttrExtractor<T extends Attrable> {
    void extract(T entity, JSONObject data, AttrExtractContext context);
}
