package com.sklay.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sklay.dao.SpecificDao;
import com.sklay.model.GlobalSetting;
import com.sklay.model.LoginView;

public class SklayInterceptor implements HandlerInterceptor {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SklayInterceptor.class);

	@Autowired
	private SpecificDao specificDao;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("set setting");
		if (modelAndView != null
				&& !modelAndView.getModelMap().containsAttribute(
						"golbalSetting")) {
			GlobalSetting setting = specificDao.getGlobalConfig();
			modelAndView.getModelMap().addAttribute("golbalSetting", setting);
			modelAndView.getModelMap().addAttribute("loginView", new LoginView());
			
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
