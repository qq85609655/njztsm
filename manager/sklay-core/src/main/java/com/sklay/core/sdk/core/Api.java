package com.sklay.core.sdk.core;

import com.sklay.core.sdk.model.OAuthConfig;

/**
 * Contains all the configuration needed to instantiate a valid
 * {@link OAuthService}
 * 
 * @author Pablo Fernandez
 * 
 */
public interface Api {
	/**
	 * 初期化Api
	 * 
	 * @param config
	 *            api配置
	 */
	void createService(OAuthConfig config);

	/**
	 * 取得OAuth服务
	 * 
	 * @return OAuth服务
	 */
	OAuthService getService();
}
