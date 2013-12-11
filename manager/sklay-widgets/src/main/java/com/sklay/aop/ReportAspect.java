package com.sklay.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.service.jta.platform.internal.JOnASJtaPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
public class ReportAspect {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportAspect.class);

	/*
	 * @Autowired private OrderManager orderManager;
	 */

	/**
	 * TODO 添加记录
	 * @param joinPoint
	 * @param data
	 */
	@AfterReturning(value = "anyMethod()", returning = "data")
	public void anyMethodReport(JoinPoint joinPoint, Object data) {
//		try {
//			data.getClass();
//			JSONObject.toJSONString(data);
//			Object[] poitnArgs = joinPoint.getArgs();
//			LOGGER.info("args info is {}", JSONObject.toJSONString(poitnArgs));
//		} catch (Throwable e) {
//			LOGGER.error(e.getMessage());
//		}
	}

	@Pointcut("execution(* com.sklay.service.impl.*.*(..))")
	public void anyMethod() {
	}

}
