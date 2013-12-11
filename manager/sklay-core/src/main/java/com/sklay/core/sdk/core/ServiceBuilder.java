package com.sklay.core.sdk.core;

import com.sklay.core.sdk.exceptions.OAuthException;
import com.sklay.core.sdk.model.OAuthConfig;
import com.sklay.core.sdk.model.SignatureType;
import com.sklay.core.sdk.utils.Preconditions;

/**
 * OpenApi接口服务builder
 * 
 * @author 王俞凯
 * 
 */
public class ServiceBuilder {

	// [region] 构造函数

	/**
	 * 默认构造函数
	 */
	public ServiceBuilder() {

	}

	// [endregion]

	// [region] Public Method

	/**
	 * 创建Api接口
	 * 
	 * @param apiClass
	 * @param callback
	 * @param apiKey
	 * @param apiSecret
	 * @param scope
	 * @param type
	 * @return
	 */
	public static Api createApi(Class<? extends Api> apiClass, String callback,
			String apiKey, String apiSecret, String scope, SignatureType type) {
		Preconditions.checkNotNull(apiClass, "Api Class cannot be null");

		Api api;
		try {
			api = apiClass.newInstance();
		} catch (Exception ex) {
			throw new OAuthException("Error while creating the Api object", ex);
		}

		api.createService(new OAuthConfig(apiKey, apiSecret, callback, type,
				scope));

		return api;
	}
	// [endregion]
}
