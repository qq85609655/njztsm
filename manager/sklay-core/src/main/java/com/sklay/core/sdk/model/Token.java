package com.sklay.core.sdk.model;

/**
 * 令牌对象(包括请求和访问令牌)
 * @author 王俞凯
 *
 */
public class Token {
	
	/**
	 * 令牌值
	 */
	private final String token;
	
	/**
	 * 密码
	 */
	private final String secret;
	
	/**
	 * response的内容 
	 */
	private final String rawResponse;
	
	/**
	 * 构造函数
	 * @param token 令牌值
	 * @param secret 密码
	 */
	public Token(String token, String secret){
		this(token, secret, null);
	}
	
	/**
	 * 构造函数
	 * @param token 令牌值
	 * @param secret 密码
	 * @param rawResponse response的内容 
	 */
	public Token(String token, String secret, String rawResponse){
		this.token = token;
		this.secret = secret;
		this.rawResponse = rawResponse;
	}

	/**
	 * 取得令牌值
	 * @return 令牌值
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 取得密码
	 * @return 密码
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * 取得response的内容 
	 * @return response的内容 
	 */
	public String getRawResponse() {
		return rawResponse;
	}
	
	
}
