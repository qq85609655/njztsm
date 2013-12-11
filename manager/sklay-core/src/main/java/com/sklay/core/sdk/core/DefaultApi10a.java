package com.sklay.core.sdk.core;

import com.sklay.core.sdk.extractors.AccessTokenExtractor;
import com.sklay.core.sdk.extractors.BaseStringExtractor;
import com.sklay.core.sdk.extractors.BaseStringExtractorImpl;
import com.sklay.core.sdk.extractors.HeaderExtractor;
import com.sklay.core.sdk.extractors.HeaderExtractorImpl;
import com.sklay.core.sdk.extractors.RequestTokenExtractor;
import com.sklay.core.sdk.extractors.TokenExtractorImpl;
import com.sklay.core.sdk.model.OAuthConfig;
import com.sklay.core.sdk.model.Token;
import com.sklay.core.sdk.model.Verb;
import com.sklay.core.sdk.oauth.OAuth10aServiceImpl;
import com.sklay.core.sdk.service.HMACSha1SignatureService;
import com.sklay.core.sdk.service.SignatureService;
import com.sklay.core.sdk.service.TimestampService;
import com.sklay.core.sdk.service.TimestampServiceImpl;

/**
 * Default implementation of the OAuth protocol, version 1.0a
 * 
 * This class is meant to be extended by concrete implementations of the API,
 * providing the endpoints and endpoint-http-verbs.
 * 
 * If your Api adheres to the 1.0a protocol correctly, you just need to extend
 * this class and define the getters for your endpoints.
 * 
 * If your Api does something a bit different, you can override the different
 * extractors or services, in order to fine-tune the process. Please read the
 * javadocs of the interfaces to get an idea of what to do.
 * 
 * @author Pablo Fernandez
 * 
 */
public abstract class DefaultApi10a implements Api {
	
	protected OAuthService oauth;
	
	protected OAuthConfig config;

	/**
	 * Returns the access token extractor.
	 * 
	 * @return access token extractor
	 */
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new TokenExtractorImpl();
	}

	/**
	 * Returns the base string extractor.
	 * 
	 * @return base string extractor
	 */
	public BaseStringExtractor getBaseStringExtractor() {
		return new BaseStringExtractorImpl();
	}

	/**
	 * Returns the header extractor.
	 * 
	 * @return header extractor
	 */
	public HeaderExtractor getHeaderExtractor() {
		return new HeaderExtractorImpl();
	}

	/**
	 * Returns the request token extractor.
	 * 
	 * @return request token extractor
	 */
	public RequestTokenExtractor getRequestTokenExtractor() {
		return new TokenExtractorImpl();
	}

	/**
	 * Returns the signature service.
	 * 
	 * @return signature service
	 */
	public SignatureService getSignatureService() {
		return new HMACSha1SignatureService();
	}

	/**
	 * Returns the timestamp service.
	 * 
	 * @return timestamp service
	 */
	public TimestampService getTimestampService() {
		return new TimestampServiceImpl();
	}

	/**
	 * Returns the verb for the access token endpoint (defaults to POST)
	 * 
	 * @return access token endpoint verb
	 */
	public Verb getAccessTokenVerb() {
		return Verb.GET;
	}

	/**
	 * Returns the verb for the request token endpoint (defaults to POST)
	 * 
	 * @return request token endpoint verb
	 */
	public Verb getRequestTokenVerb() {
		return Verb.GET;
	}

	/**
	 * Returns the URL that receives the request token requests.
	 * 
	 * @return request token URL
	 */
	public abstract String getRequestTokenEndpoint();

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
	 * @param requestToken
	 *            the request token you need to authorize
	 * @return the URL where you should redirect your users
	 */
	public abstract String getAuthorizationUrl(Token requestToken);

	/**
	 * 登出通知OAuth
	 * @return 通知OAuth的地址
	 */
	public abstract String getLogoutUrl(String callback);
	
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
		this.oauth = new OAuth10aServiceImpl(this, config);
	}
	
	/* (non-Javadoc)
	 * @see com.sklay.core.sdk.core.Api#getService()
	 */
	public OAuthService getService(){
		return oauth;
	}
	// [endregion]
}
