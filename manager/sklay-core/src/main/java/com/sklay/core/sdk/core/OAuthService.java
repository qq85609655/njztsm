package com.sklay.core.sdk.core;

import com.sklay.core.sdk.model.OAuthRequest;
import com.sklay.core.sdk.model.Token;
import com.sklay.core.sdk.model.Verifier;

/**
 * 取得请求令牌、访问令牌和签名发送报文的接口
 * 
 * @author 王俞凯
 *
 */
public interface OAuthService {
	
	/**
	 * 取得请求令牌的方法
	 * @return 请求令牌
	 */
	public Token getRequestToken();
	
	/**
	 * 根据请求令牌和验证码取得访问令牌
	 * @param requestToken 请求令牌
	 * @param verifier 验证码
	 * @return 访问令牌
	 */
	public Token getAccessToken(Token requestToken, Verifier verifier);
	
	/**
	 * 使用访问令牌对请求参数的报文签名
	 * @param accessToken 访问令牌
	 * @param request OAuth请求
	 */
	public void signRequest(Token accessToken, OAuthRequest request);
	
	/**
	 * 取得OAuth的版本号
	 * @return OAuth的版本号
	 */
	public String getVersion();
	
	/**
	 * 取得用户验证的地址
	 * @return 用户验证的地址
	 */
	public String getAuthorizationUrl(Token requestToken);
	
	/**
	 * 登出以后在OAuth上注销
	 * @return
	 */
	public String getLogoutUrl(String callback);
}

