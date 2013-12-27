package com.sklay.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.vo.DataView;

/**
 * API客户端接口：用于访问网络数据
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ApiClient {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApiClient.class);

	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";

	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;

	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	private static GetMethod getHttpGet(String url) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		return httpGet;
	}

	private static PostMethod getHttpPost(String url) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		return httpPost;
	}

	private static String _MakeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)
			url.append('?');

		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			// url.append(String.valueOf(params.get(name)));
			// 不做URLEncoder处理
			try {
				url.append(URLEncoder.encode(String.valueOf(params.get(name)),
						UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return url.toString().replace("?&", "?");
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static String http_post(String url, NameValuePair[] pairs) {

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		String responseBody = "";
		try {
			httpClient = getHttpClient();
			httpPost = getHttpPost(url);
			httpPost.setRequestBody(pairs);

			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_RESULT_ERROR);
			}
			responseBody = httpPost.getResponseBodyAsString();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
			throw new SklayException(e);
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			throw new SklayException(e);
		} finally {
			// 释放连接
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseBody;
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	private static DataView http_get(String url) {
		// System.out.println("get_url==> "+url);
		DataView dataView = null;
		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url);

				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw new SklayException(ErrorCode.API_RESULT_ERROR);
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw new SklayException(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw new SklayException(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		dataView = JSONObject.parseObject(responseBody, DataView.class);
		return dataView;
	}

	/**
	 * 公用post方法
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	public static String post(String url, Map<String, Object> params) {

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		String responseBody = "";
		try {

			url = _MakeURL(url, params);

			httpClient = getHttpClient();
			httpPost = getHttpPost(url);

			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_RESULT_ERROR);
			}
			responseBody = httpPost.getResponseBodyAsString();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
			throw new SklayException(e);
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			throw new SklayException(e);
		} finally {
			// 释放连接
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseBody;
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	public static DataView method_get(String url) {

		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw new SklayException(ErrorCode.API_ILLEGAL_PARAMETERS);
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw new SklayException(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw new SklayException(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");

		DataView dataView = JSONObject
				.parseObject(responseBody, DataView.class);

		return dataView;
	}

	public static NameValuePair[] splitNameValuePair(Map<String, String> params) {

		if (null == params || params.size() < 1)
			return null;
		Set<String> keys = params.keySet();
		NameValuePair[] param = new NameValuePair[keys.size()];
		int i = 0;
		for (String key : keys) {
			String value = params.get(key);
			NameValuePair pair = new NameValuePair(key, value);
			param[i++] = pair;
		}

		return param;
	}

	/**
	 * 获取资讯的详情
	 * 
	 * @param url
	 * @param news_id
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("serial")
	public static void getNewsDetail(final int news_id, String url) {

		NameValuePair[] pairs = splitNameValuePair(new HashMap<String, String>() {
			{
				put("newsId", String.valueOf(news_id));
			}
		});

		try {

			http_post(url, pairs);

		} catch (Exception e) {
			throw new SklayException(e);
		}
	}

}
