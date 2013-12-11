package com.sklay.core.ex.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.collect.Maps;
import com.sklay.core.ex.SklayException;
import com.sklay.core.web.util.WebUtils;

public class SklaySimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SklaySimpleMappingExceptionResolver.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		/** 选择视图ModelAndView暴露错误 */
		String viewName = super.determineViewName(ex, request);

		if (StringUtils.isEmpty(viewName)) {
			return null;
		}

		/** 判断是否是异步请求 */
		if (!WebUtils.isAjaxRequest(request) && !WebUtils.isWapRequest(request)
				&& !WebUtils.isAndroidRequest(request)) {
			if (ex instanceof UnauthenticatedException) {
				return super.getModelAndView("alone:core.login", ex, request);
			}
			if (ex instanceof AuthorizationException) {
				return super.getModelAndView(viewName, ex, request);
			}

			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return super.getModelAndView(viewName, ex, request);
		}

		/** JSON格式返回 */
		String msg = ex.getMessage();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map attributes = Maps.newHashMap();
		attributes.put("msg", msg);
		int code = -1;
		attributes.put("code", code);
		if (ex instanceof SklayException) {
			code = ((SklayException) ex).getCode();
		}
		attributes.put("code", code);

		if (WebUtils.isAndroidRequest(request)) {
			attributes.put("code", -1);
		}

		view.setAttributesMap(attributes);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(view);

		return modelAndView;

	}

}
