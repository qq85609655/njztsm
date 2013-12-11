/*
 * Project:  any-parent
 * Module:   any-framework
 * File:     ErrorCode.java
 * Modifier: ozn
 * Modified: 2012-08-13 19:06
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.ex;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-2
 */
public interface ErrorCode {

	/**
	 * 请求成功执行
	 */
	static final int SUCCEED = 0;

	/**
	 * 服务器内部错误
	 */
	static final int SERVER_ERROR = 1;

	/**
	 * {0}参数非法
	 */
	static final int ILLEGAL_PARAM = 2;

	/**
	 * 必须参数{0}不存在
	 */
	static final int MISS_PARAM = 3;

	/**
	 * API返回值为空
	 */
	static final int API_CALL_ERROE = 1000;

	/**
	 * API非法密钥
	 */
	static final int API_ILLEGAL_KEY = 1001;

	/**
	 * API非法参数，参数错误时候给出
	 */
	static final int API_ILLEGAL_PARAMETERS = 1002;

	/**
	 * API返回结果不正确
	 */
	static final int API_RESULT_ERROR = 1003;

	/**
	 * 数据采集转化异常!
	 */
	static final int SMS_GATHER_ERROR = 1004;

	/**
	 * 采集卡号不能为空!
	 */
	static final int SMS_GATHER_SIMNO_EMPTY = 1005;

	/**
	 * 设备:{0}还未绑定主用户
	 */
	static final int SMS_GATHER_NOBANDING = 1006;

	/**
	 * 用户:{0}未匹配到体检结果
	 */
	static final int RESULT_NO_MATCHING = 1007;
	
	/**
	 * 系統配置不存在
	 */
	static final int SYS_CONFIG_EMPTY = 1008;
	
	/**
	 * 没有短信任务
	 */
	static final int SMS_NULL_SMS = 1009;
	
	/**
	 * {0}不存在
	 */
	static final int FINF_NULL = 1010;
	
	/**
	 * {0}已存在,无法{1}.
	 */
	static final int EXIST = 1011;
	
	/**
	 * {0}功能已关闭.
	 */
	static final int CLOSED = 1012;
	
	/**
	 * 不在体检时间内,暂时无法体检
	 */
	static final int NO_PERIOD = 1013 ;
	
	/**
	 * 每天的体检次数已达到上线
	 */
	static final int MORE_COUNT = 1014 ;
	
	/**
	 * {0}审核未完成,无法{1}
	 */
	static final int AUTIT_ERROR = 1015 ;
}
