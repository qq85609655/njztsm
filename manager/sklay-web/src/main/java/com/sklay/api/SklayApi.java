package com.sklay.api;

import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.sdk.exceptions.JSONException;
import com.sklay.core.sdk.model.Request;
import com.sklay.core.sdk.model.Response;
import com.sklay.core.sdk.model.Verb;
import com.sklay.core.sdk.model.vo.LocationDetail;
import com.sklay.core.sdk.model.vo.SMSSetting;
import com.sklay.core.util.Constants;
import com.sklay.mobile.Update;
import com.sklay.model.SMS;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-9
 */
public class SklayApi {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SklayApi.class);

	/**
	 * 搜索地区
	 */
	@Value("${search.url}")
	private String searchURL;

	/**
	 * key
	 */
	@Value("${search.key}")
	private String searchKey;

	@Value("${" + Constants.SMS_ACCOUNT + "}")
	private String account;

	@Value("${" + Constants.SMS_PASSWORD + "}")
	private String password;

	@Value("${" + Constants.SMS_URL + "}")
	private String sendUrl;

	@Value("${" + Constants.SMS_PHYSICAL + "}")
	private String physical;

	@Value("${" + Constants.SMS_SOS + "}")
	private String sos;

	@Value("${" + Constants.SMS_PWD + "}")
	private String pwd;

	@Value("${" + Constants.SMS_SIGN + "}")
	private String sign;

	@Value("${" + Constants.SMS_SOS_PAIRS + "}")
	private String sosPairs;

	@Value("${" + Constants.SMS_PWD_PAIRS + "}")
	private String pwdPairs;

	// 版本号
	@Value("${" + Constants.ANDROID_VER_CODE + "}")
	private int versionCode;

	// 版本描述字符串
	@Value("${" + Constants.ANDROID_VER_NAME + "}")
	private String versionName;

	// 新版本更新下载地址
	@Value("${" + Constants.ANDROID_DOWLOAD_URL + "}")
	private String downloadUrl;

	// 更新描述信息
	@Value("${" + Constants.ANDROID_UPDATE_LOG + "}")
	private String updateLog;

	public Update getMobileAndroidVer() {
		return new Update(versionCode, versionName, downloadUrl, updateLog);
	}

	public boolean setMobileAndroidVer(Update update) {

		boolean bRslt = false;

		if (null == update)
			return bRslt;
		if (StringUtils.isBlank(update.getDownloadUrl()))
			return bRslt;
		if (StringUtils.isBlank(update.getUpdateLog()))
			return bRslt;
		if (StringUtils.isBlank(update.getVersionName()))
			return bRslt;
		if (update.getVersionCode() < 1)
			return bRslt;

		this.setUpdateLog(update.getUpdateLog());
		this.setDownloadUrl(update.getDownloadUrl());
		this.setVersionCode(update.getVersionCode());
		this.setVersionName(update.getVersionName());
		bRslt = true;

		return bRslt;

	}

	/**
	 * 根据经纬度定位
	 * 
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 * @return
	 * @throws JSONException
	 */
	public String SearchLocation(String latitude, String longitude) {
		Request request = new Request(Verb.GET, searchURL);

		request.addQuerystringParameter("location", latitude.trim() + ","
				+ longitude.trim());
		request.addQuerystringParameter("output", "json");
		request.addQuerystringParameter("key", searchKey);
		Response response = request.send();
		// new
		// { "status":"OK", "result":{ "location":{"lng":116.322987,
		// "lat":39.983424},
		// "formatted_address":"北京市海淀区中关村大街27号1101-08室","business":"人民大学,中关村,苏州街","addressComponent":{"city":"北京市","district":"海淀区",
		// "province":"北京市","street":"中关村大街","street_number":"27号1101-08室"},"cityCode":131}}

		if (StringUtils.isBlank(response.getBody()))
			throw new SklayException(ErrorCode.API_CALL_ERROE);

		JSONObject jsonObject = JSONObject.parseObject(response.getBody());

		if (!jsonObject.containsKey("status"))
			throw new SklayException(ErrorCode.API_CALL_ERROE);

		String status = jsonObject.getString("status");

		if ("INVILID_KEY".equals(status))
			throw new SklayException(ErrorCode.API_ILLEGAL_KEY);
		if ("INVALID_PARAMETERS".equals(status))
			throw new SklayException(ErrorCode.API_ILLEGAL_PARAMETERS);
		if ("OK".equalsIgnoreCase(status)) {

			String result = jsonObject.getString("result");
			if (StringUtils.isBlank(result))
				throw new SklayException(ErrorCode.API_RESULT_ERROR);

			LocationDetail location = JSONObject.parseObject(result,
					LocationDetail.class);

			return location.getFormatted_address();
		}
		throw new SklayException(ErrorCode.API_CALL_ERROE);
	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> physical(Set<SMS> smsList) {

		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("signtag", sign);
				pairs.put("mobile", sms.getReceiver());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));

				LOGGER.info("physical result {} " + result);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsList;
	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> sms(Set<SMS> smsList) {

		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("signtag", sign);
				pairs.put("mobile", sms.getReceiver());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));

				LOGGER.info("sms result {} " + result);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsList;
	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> resetPwd(Set<SMS> smsList) {

		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("tempid", pwdPairs);
				pairs.put("signtag", sign);
				pairs.put("mobile", sms.getReceiver());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));

				LOGGER.info("resetPwd result {} " + result);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsList;
	}

	public Set<SMS> sos(Set<SMS> smsList) {

		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("tempid", sosPairs);
				pairs.put("signtag", sign);
				pairs.put("mobile", sms.getReceiver());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));

				LOGGER.info("sos result {} " + result);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}

			}
		}

		return smsList;
	}

	public boolean setSMSSetting(SMSSetting smsSetting) {
		boolean bRslt = true;

		if (StringUtils.isNotBlank(smsSetting.getPhysical()))
			this.setPhysical(smsSetting.getPhysical().trim());

		if (StringUtils.isNotBlank(smsSetting.getSos()))
			this.setSos(smsSetting.getSos().trim());

		if (StringUtils.isNotBlank(smsSetting.getSign()))
			this.setSos(smsSetting.getSign().trim());

		return bRslt;
	}

	public SMSSetting getSMSSetting() {
		return new SMSSetting(pwd, account, password, sendUrl, physical, sos,
				sign, sosPairs, pwdPairs);
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public String getSos() {
		return sos;
	}

	public void setSos(String sos) {
		this.sos = sos;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

}
