package com.sklay.track.util;

import org.sitemesh.content.Content;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.memory.InMemoryContent;
import org.sitemesh.webapp.WebAppContext;

import javax.servlet.jsp.PageContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-9-11
 */
public class SitemeshHelper {

	public static void extractMeta(PageContext context) {
		Content content = (Content) context.getRequest().getAttribute(
				WebAppContext.CONTENT_KEY);
		if (content == null) {
			content = new InMemoryContent();
		}
		ContentProperty cp = content.getExtractedProperties();
		context.setAttribute("_body", cp.getChild("body").getValue());
		context.setAttribute("_title", cp.getChild("title").getValue());
		context.setAttribute("_head", cp.getChild("head").getValue());
		Map<String, String> metaMap = new HashMap<String, String>();
		for (ContentProperty cp1 : cp.getChild("meta").getChildren()) {
			metaMap.put(cp1.getName(), cp1.getValue());
		}
		context.setAttribute("_meta", metaMap);

	}
}
