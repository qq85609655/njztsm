package com.sklay.core.sdk.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.sklay.core.sdk.exceptions.OAuthException;
import com.sklay.core.sdk.utils.URLUtils;

/**
 * 发送的Http请求的报文对象
 * 
 * @author 王俞凯
 * 
 */
public class Request {

	// [region] 定数

	/**
	 * content—length http head信息
	 */
	private static final String CONTENT_LENGTH = "Content-Length";

	// [endregion]

	// [region] 变量

	/**
	 * 请求路径
	 */
	private String url;

	/**
	 * http请求动作
	 */
	private Verb verb;

	/**
	 * 请求路径的Url参数
	 */
	private Map<String, String> queryStringParams;

	/**
	 * 请求内容参数
	 */
	private Map<String, String> bodyParams;

	/**
	 * 请求头信息参数
	 */
	private Map<String, String> headers;

	/**
	 * payload data
	 */
	private String payload = null;

	/**
	 * HttpURL链接
	 */
	private HttpURLConnection connection = null;

	/**
	 * 编码格式
	 */
	private String charset;

	/**
	 * payload data
	 */
	private byte[] bytePayload = null;

	/**
	 * 是否使用使用Keep-Alive模式
	 */
	private boolean connectionKeepAlive = false;

	/**
	 * 链接超时时间
	 */
	private Long connectTimeout = null;

	/**
	 * 读超时时间
	 */
	private Long readTimeout = null;

	// [endregion]

	// [region] 属性

	/**
	 * 取得请求地址
	 * 
	 * @return 请求地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置请求地址
	 * 
	 * @param url
	 *            请求地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 取得http请求动作
	 * 
	 * @return http请求动作
	 */
	public Verb getVerb() {
		return verb;
	}

	/**
	 * 设置http请求动作
	 * 
	 * @param verb
	 *            http请求动
	 */
	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	/**
	 * 取得请求路径的Url参数
	 * 
	 * @return 请求路径的Url参数
	 */
	public Map<String, String> getQueryStringParams() {
		return queryStringParams;
	}

	/**
	 * 取得请求内容参数
	 * 
	 * @return 请求内容参数
	 */
	public Map<String, String> getBodyParams() {
		return bodyParams;
	}

	/**
	 * 取得请求头信息参数
	 * 
	 * @return 请求头信息参数
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * 取得payload data
	 * 
	 * @return payload data
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * 设置payload data
	 * 
	 * @param payload
	 *            payload data
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * 取得编码格式
	 * 
	 * @return 编码格式
	 */
	public String getCharset() {
		return charset == null ? Charset.defaultCharset().name() : charset;
	}

	/**
	 * 设置编码格式
	 * 
	 * @param charset
	 *            编码格式
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 设置是否使用使用Keep-Alive模式
	 * 
	 * @param connectionKeepAlive
	 *            是否使用使用Keep-Alive模式
	 */
	public void setConnectionKeepAlive(boolean connectionKeepAlive) {
		this.connectionKeepAlive = connectionKeepAlive;
	}

	/**
	 * 设置
	 * 
	 * @param connectTimeout
	 *            链接超时时间
	 */
	public void setconnectTimeout(Long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 设置读超时时间
	 * 
	 * @param readTimeout
	 *            读超时时间
	 */
	public void setReadTimeout(Long readTimeout) {
		this.readTimeout = readTimeout;
	}

	// [endregion]

	// [region] 构造函数

	/**
	 * 构造函数
	 * 
	 * @param verb
	 *            http请求动作
	 * @param url
	 *            请求路径
	 */
	public Request(Verb verb, String url) {
		this.verb = verb;
		this.url = url;
		this.queryStringParams = new HashMap<String, String>();
		this.bodyParams = new HashMap<String, String>();
		this.headers = new HashMap<String, String>();
	}

	// [endregion]

	// [region] 公共方法

	/**
	 * @return
	 */
	public Response send() {
		try {
			createConnection();
			return doSend();
		} catch (IOException ioex) {
			throw new OAuthException("Problems while creating connection", ioex);
		}
	}

	/**
	 * Add an HTTP Header to the Request
	 * 
	 * @param key
	 *            the header name
	 * @param value
	 *            the header value
	 */
	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	/**
	 * Add a body Parameter (for POST/ PUT Requests)
	 * 
	 * @param key
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 */
	public void addBodyParameter(String key, String value) {
		this.bodyParams.put(key, value);
	}

	/**
	 * Add a QueryString parameter
	 * 
	 * @param key
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 */
	public void addQuerystringParameter(String key, String value) {
		this.queryStringParams.put(key, value);
	}

	/**
	 * Add body payload.
	 * 
	 * This method is used when the HTTP body is not a form-url-encoded string,
	 * but another thing. Like for example XML.
	 * 
	 * Note: The contents are not part of the OAuth signature
	 * 
	 * @param payload
	 *            the body of the request
	 */
	public void addPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * Overloaded version for byte arrays
	 * 
	 * @param payload
	 */
	public void addPayload(byte[] payload) {
		this.bytePayload = payload;
	}

	/**
	 * Returns the URL without the port and the query string part.
	 * 
	 * @return the OAuth-sanitized URL
	 */
	public String getSanitizedUrl() {
		return url.replaceAll("\\?.*", "").replace("\\:\\d{4}", "");
	}

	// [endregion]

	// [region] Private Method

	/**
	 * 创建HttpConnection
	 * 
	 * @throws IOException
	 */
	private void createConnection() throws IOException {
		String effectiveUrl = URLUtils.appendParametersToQueryString(url,
				queryStringParams);
		if (connection == null) {
			System.setProperty("http.keepAlive", connectionKeepAlive ? "true"
					: "false");
			connection = (HttpURLConnection) new URL(effectiveUrl)
					.openConnection();
		}
	}

	/**
	 * 发送Http信息
	 * 
	 * @return 返回的http请求的信息
	 * @throws IOException
	 */
	private Response doSend() throws IOException {
		connection.setRequestMethod(this.verb.name());
		if (connectTimeout != null) {
			connection.setConnectTimeout(connectTimeout.intValue());
		}
		if (readTimeout != null) {
			connection.setReadTimeout(readTimeout.intValue());
		}
		addHeaders(connection);
		if (verb.equals(Verb.PUT) || verb.equals(Verb.POST)) {
			addBody(connection, getByteBodyContents());
		}
		return new Response(connection);
	}

	/**
	 * 加入Head信息
	 * 
	 * @param conn
	 *            HttpConnection信息
	 */
	private void addHeaders(HttpURLConnection conn) {
		for (String key : headers.keySet())
			conn.setRequestProperty(key, headers.get(key));
	}

	/**
	 * 取得内容的信息
	 * 
	 * @return 内容的信息
	 */
	private byte[] getByteBodyContents() {
		if (bytePayload != null)
			return bytePayload;
		String body = (payload != null) ? payload : URLUtils
				.formURLEncodeMap(bodyParams);
		try {
			return body.getBytes(getCharset());
		} catch (UnsupportedEncodingException uee) {
			throw new OAuthException("Unsupported Charset: " + getCharset(),
					uee);
		}
	}

	/**
	 * 加入内容信息
	 * 
	 * @param conn
	 *            HttpConnection
	 * @param content
	 *            内容
	 * @throws IOException
	 */
	private void addBody(HttpURLConnection conn, byte[] content)
			throws IOException {
		conn.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(content);
	}

	// [endregion]
}
