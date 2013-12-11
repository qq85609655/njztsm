package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.AttrExtractor;
import com.sklay.track.model.Action;

import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-15
 */
public class ActionAttrExtractor extends UrlAttrExtractor<Action> implements AttrExtractor<Action> {
    @Override
    public void extract(Action action, JSONObject data, AttrExtractContext context) {
        int second = data.getIntValue("s");
        action.setHappenAt(DateUtils.addSeconds(new Date(), -second));
        super.extract(action, data, context);
    }
}
