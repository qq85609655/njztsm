package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.Attrable;
import com.sklay.track.model.dict.Dicts;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class UrlAttrExtractor<T extends Attrable> extends AbstractSingleAttrExtractor<T> {
    private String prefix;

    public UrlAttrExtractor() {
        setKey("url");
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void extract(T entity, JSONObject data, AttrExtractContext context) {
        String urlString = getString(getKey(), data, context);
        if (urlString == null) {
            return;
        }
        entity.setAttr(getTargetKey(), urlString);
        UriComponents url = UriComponentsBuilder.fromUriString(urlString).build();
        extractUrl(url, entity, data, context);
    }

    protected void extractUrl(UriComponents url, Attrable entity, JSONObject data, AttrExtractContext context) {
        //entity.setAttr(getFullKey(Constants.DOMAIN), url.getHost());
        String path = url.getPath();
        if (StringUtils.isNotEmpty(path) && !"/".equals(path)) {
            entity.setAttr(getFullKey("path"), path);
        }
        entity.setAttr(getFullKey("query"), url.getQuery());
        entity.setAttr(getFullKey("domainId"), getDictItemId(Dicts.DOMAIN, url.getHost(), null));
        String fragment = url.getFragment();
        if (fragment != null) {
            entity.setAttr(getFullKey("hash"), fragment);
        }
    }

    private String getFullKey(String key) {
        return prefix == null ? key : prefix + StringUtils.capitalize(key);
    }
}
