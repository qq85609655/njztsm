package com.sklay.core.sdk.core;

import com.sklay.core.sdk.extractors.AccessTokenExtractor;
import com.sklay.core.sdk.extractors.JsonTokenExtractor;
import com.sklay.core.sdk.model.OAuthConfig;
import com.sklay.core.sdk.model.Token;
import com.sklay.core.sdk.model.Verb;
import com.sklay.core.sdk.oauth.OAuth20ServiceImpl;

/**
 * Default implementation of the OAuth protocol, version 2.0 (draft 11)
 * 
 * This class is meant to be extended by concrete implementations of the API,
 * providing the endpoints and endpoint-http-verbs.
 * 
 * If your Api adheres to the 2.0 (draft 11) protocol correctly, you just need
 * to extend this class and define the getters for your endpoints.
 * 
 * If your Api does something a bit different, you can override the different
 * extractors or services, in order to fine-tune the process. Please read the
 * javadocs of the interfaces to get an idea of what to do.
 * 
 * @author Diego Silveira
 * 
 */
public abstract class DefaultApi20 implements Api {

	protected OAuthService oauth;

	protected OAuthConfig config;

	/**
	 * Returns the access token extractor.
	 * 
	 * @return access token extractor
	 */
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new JsonTokenExtractor();
	}

	/**
	 * Returns the verb for the access token endpoint (defaults to GET)
	 * 
	 * @return access token endpoint verb
	 */
	public Verb getAccessTokenVerb() {
		return Verb.GET;
	}

	/**
	 * Returns the URL that receives the access token requests.
	 * 
	 * @return access token URL
	 */
	public abstract String getAccessTokenEndpoint();

	/**
	 * Returns the URL where you should redirect your users to authenticate your
	 * application.
	 * 
	 * @param config
	 *            OAuth 2.0 configuration param object
	 * @return the URL where you should redirect your users
	 */
	public abstract String getAuthorizationUrl(Token requestToken);

	/**
	 * Returns the {@link OAuthService} for this Api
	 * 
	 * @param apiKey
	 *            Key
	 * @param apiSecret
	 *            Api Secret
	 * @param callback
	 *            OAuth callback (either URL or 'oob')
	 * @param scope
	 *            OAuth scope (optional)
	 */
	public void createService(OAuthConfig config) {
		this.config = config;
		this.oauth = new OAuth20ServiceImpl(this, config);
	}

	/**
	 * 登出通知OAuth
	 * 
	 * @return 通知OAuth的地址
	 */
	public abstract String getLogoutUrl(String callback);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sklay.core.sdk.core.Api#getService()
	 */
	public OAuthService getService() {
		return oauth;
	}

}
