package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.Attrable;
import com.sklay.track.model.dict.Dicts;
import com.snowplowanalytics.refererparser.Parser;
import com.snowplowanalytics.refererparser.Referal;
import org.springframework.web.util.UriComponents;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class RefererAttrExtractor extends UrlAttrExtractor {
	private Parser refererParser;

	public void setRefererParser(Parser refererParser) {
		this.refererParser = refererParser;
	}

	public RefererAttrExtractor() {
		setKey("referer");
		setPrefix("r");
		try {
			refererParser = new Parser();
		} catch (Exception ignored) {
		}
	}

	@Override
	protected void extractUrl(UriComponents url, Attrable entity,
			JSONObject data, AttrExtractContext context) {
		super.extractUrl(url, entity, data, context);
		Referal referal = refererParser.parse(url.toUri());
		if (referal.search != null) {
			entity.setAttr("searchTerm", referal.search.term);
			entity.setAttr("search",
					getDictItemId(Dicts.SEARCH, referal.referer.name, null));
		}
	}
}
