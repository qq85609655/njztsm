package com.sklay.widgets.gather;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;
import com.sklay.core.js.JavaScriptHandler;
import com.sklay.core.util.StringUtils;

@Widgets("gather")
public class GatherWidgets{
	
	@Autowired
	private JavaScriptHandler javaScriptHandler;

	@Widget(settings = { @Setting(key = "url", value = "",name="网址"),
			@Setting(key = "width", value = "100%",name="宽度"),
			@Setting(key = "height", value = "100%",name="高度") })
	public String iframe() {
		return "iframe.tpl";
	}

	@Widget(settings = { @Setting(key = "uri", value = "",name="网址"),
			@Setting(key = "method", value = "GET",name="请求方式"),@Setting(key="callback",value="",name="JS回调函数(处理内容并返回)",inputType=InputType.TEXTAREA)})
	public String proxy(com.sklay.model.Widget widget, ModelMap modelMap) {
		Map<String,String> settings = widget.getSettings();
		String uri = StringUtils.trimWhitespace(settings.get("uri"));
		if (StringUtils.hasText(uri) && uri.toLowerCase().startsWith("http")) {
			String method = settings.get("method");
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
			HttpUriRequest request = null;
			if (!StringUtils.hasText(method) || "get".equalsIgnoreCase(method)) {
				request = new HttpGet(uri);
			} else {
				request = new HttpPost(uri);
			}
			try {
				String result = client.execute(request,new BasicResponseHandler());
				// do basic cleaning
				String callback = StringUtils.trimWhitespace(settings.get("callback"));
				if(StringUtils.hasText(callback)){
					result = (String)javaScriptHandler.evalFunction(callback, result);
				}
				modelMap.put("content", result);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "proxy.tpl";
	}
}
