package com.sklay.api;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.enums.OperatorType;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.sdk.exceptions.JSONException;
import com.sklay.core.sdk.model.Request;
import com.sklay.core.sdk.model.Response;
import com.sklay.core.sdk.model.Verb;
import com.sklay.core.sdk.model.vo.LocationDetail;
import com.sklay.core.sdk.model.vo.Reduced;
import com.sklay.core.sdk.model.vo.SMSCommonReturn;
import com.sklay.core.sdk.model.vo.SMSLoginInfo;
import com.sklay.core.sdk.model.vo.SMSSetting;
import com.sklay.core.sdk.model.vo.SMSStockDetail;
import com.sklay.core.sdk.utils.XML;
import com.sklay.core.util.Constants;
import com.sklay.mobile.Update;
import com.sklay.model.SMS;
import com.sklay.model.SMSLog;

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

	@Value("${" + Constants.SMS_USER_ID + "}")
	private String userId;

	@Value("${" + Constants.SMS_ACCOUNT + "}")
	private String accountId;

	@Value("${" + Constants.SMS_PASSWORD + "}")
	private String password;

	@Value("${" + Constants.SMS_URL + "}")
	private String sendUrl;

	@Value("${" + Constants.SMS_PHYSICAL + "}")
	private String physical;

	@Value("${" + Constants.SMS_SOS + "}")
	private String sos;

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

	/** 中国移动 */
	@Value("${" + Constants.OPERATOR_MOBILE + "}")
	private String chinaMobile;

	/** 中国联通 */
	@Value("${" + Constants.OPERATOR_UNICOM + "}")
	private String chinaUnicom;

	/** 中国电信 */
	@Value("${" + Constants.OPERATOR_TELECOM + "}")
	private String chinaTelecom;

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
	 * 短信登入
	 * 
	 * @return
	 */
	public SMSLoginInfo getSMSLogin() {
		LOGGER.info("sklayApi", "SMS Login");

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(sendUrl + "/UserLogin");

		NameValuePair[] data = initPairs();
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				LOGGER.info("sklayApi SMS getSMSLogin statusCode {}",
						statusCode);
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			SMSLoginInfo reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), SMSLoginInfo.class);
			if (null == reduced
					|| (null != reduced && "Success".equalsIgnoreCase(reduced
							.getRetCode())))
				throw new SklayException(reduced.getMessage());

			return reduced;
		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}

	}

	/**
	 * 短信用户登出
	 */
	public SMSCommonReturn getSMSLogOut(SMSLoginInfo loginInfo) {

		if (null == loginInfo || StringUtils.isBlank(loginInfo.getToken()))
			throw new SklayException("短信登出时 token无效");
		String token = loginInfo.getToken();

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(sendUrl + "/UserLogOff");

		NameValuePair[] data = { new NameValuePair("Token", token) };
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			SMSCommonReturn reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), SMSCommonReturn.class);
			if (null == reduced
					|| (null != reduced && "Success".equalsIgnoreCase(reduced
							.getRetCode())))
				throw new SklayException(reduced.getMessage());

			return reduced;
		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}

	}

	/**
	 * 短信用户登出
	 */
	public SMSCommonReturn getSMSPassowrd(String oldPwd, String newPwd,
			SMSLoginInfo loginInfo) {

		if (null == loginInfo || StringUtils.isBlank(loginInfo.getToken()))
			throw new SklayException("短信登出时 token无效");

		if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd))
			throw new SklayException("短信修改密码原密码或新密码不能为空!");
		String token = loginInfo.getToken();

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(sendUrl + "/PasswordChange");

		NameValuePair[] data = { new NameValuePair("Token", token),
				new NameValuePair("oldPsw", oldPwd.trim()),
				new NameValuePair("newPsw", newPwd.trim()) };
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			SMSCommonReturn reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), SMSCommonReturn.class);
			if (null == reduced
					|| (null != reduced && "Success".equalsIgnoreCase(reduced
							.getRetCode())))
				throw new SklayException(reduced.getMessage());

			return reduced;
		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}

	}

	/**
	 * 查询当前用户的短信总发送量、余量、当日发送量
	 * 
	 * @param loginInfo
	 * @return
	 */
	public SMSStockDetail getSMSStockDetail(SMSLoginInfo loginInfo) {

		if (null == loginInfo || StringUtils.isBlank(loginInfo.getToken()))
			throw new SklayException("短信登出时 token无效");

		String token = loginInfo.getToken();

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(sendUrl + "/GetStockDetails");

		NameValuePair[] data = { new NameValuePair("Token", token) };
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			SMSStockDetail reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), SMSStockDetail.class);
			if (null == reduced
					|| (null != reduced && "Success".equalsIgnoreCase(reduced
							.getRetCode())))
				throw new SklayException(reduced.getMessage());

			return reduced;
		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}

	}

	/**
	 * 短信心跳
	 * 
	 * @param loginInfo
	 * @return
	 */
	public SMSCommonReturn HeartBeating(SMSLoginInfo loginInfo) {

		if (null == loginInfo || StringUtils.isBlank(loginInfo.getToken()))
			throw new SklayException("短信登出时 token无效");

		String token = loginInfo.getToken();

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(sendUrl + "/HeartBeating");

		NameValuePair[] data = { new NameValuePair("Token", token) };
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			SMSCommonReturn reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), SMSCommonReturn.class);
			if (null == reduced
					|| (null != reduced && "Success".equalsIgnoreCase(reduced
							.getRetCode())))
				throw new SklayException(reduced.getMessage());
			return reduced;
		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}

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

	public Map<String, String> sendSMS(Map<OperatorType, Set<String>> recivers,
			String SMSContent) {

		Map<String, String> call = Maps.newHashMap();
		String sendTime = null;
		String fixNumber = null;
		if (null == recivers || recivers.size() == 0)
			throw new SklayException(ErrorCode.SMS_NULL_SMS);

		/** 移动 */
		if (recivers.containsKey(OperatorType.CHINAMOBILE)
				|| recivers.containsKey(OperatorType.CHINAUNICOM)) {

			Set<String> allPhone = Sets.newHashSet();

			Set<String> phoneMolibe = recivers.get(OperatorType.CHINAMOBILE);
			Set<String> phoneUnicom = recivers.get(OperatorType.CHINAUNICOM);

			if (CollectionUtils.isNotEmpty(phoneMolibe))
				allPhone.addAll(phoneMolibe);
			if (CollectionUtils.isNotEmpty(phoneUnicom))
				allPhone.addAll(phoneUnicom);

			String phones = "";
			for (String phone : allPhone) {
				phones += phone + ";";
			}

			call.putAll(directSend(sendUrl, phones, Constants.MOBILE2NUICOM,
					sendTime, fixNumber, SMSContent, phoneMolibe.size()));
		}
		/** 电信 */
		if (recivers.containsKey(OperatorType.CHINATELECOM)) {

			Set<String> phoneMolibe = recivers.get(OperatorType.CHINATELECOM);

			String phones = "";
			for (String phone : phoneMolibe) {
				phones += phone + ";";
			}
			call.putAll(directSend(sendUrl, phones, Constants.TELECOM,
					sendTime, fixNumber, SMSContent, phoneMolibe.size()));
		}

		return call;

	}

	public Map<String, String> reSendSMS(String phones, String type,
			String SMSContent) {

		String sendTime = null;
		String fixNumber = null;

		return directSend(sendUrl, phones, Constants.MOBILE2NUICOM, sendTime,
				fixNumber, SMSContent, 1);

	}

	public SMSSetting getSMSSetting() {
		return new SMSSetting(userId, accountId, password, sendUrl, physical,
				sos, chinaMobile, chinaUnicom, chinaTelecom);
	}

	public boolean setSMSSetting(SMSSetting smsSetting) {
		boolean bRslt = true;

		if (StringUtils.isNotBlank(smsSetting.getPhysical()))
			this.setPhysical(smsSetting.getPhysical().trim());

		if (StringUtils.isNotBlank(smsSetting.getSos()))
			this.setSos(smsSetting.getSos().trim());

		return bRslt;
	}

	public boolean setMobiles(String mobile, String unicom, String telecom) {
		boolean bRslt = true;

		if (StringUtils.isNotBlank(mobile))
			this.setChinaMobile(mobile);

		if (StringUtils.isNotBlank(unicom))
			this.setChinaUnicom(unicom);

		if (StringUtils.isNotBlank(telecom))
			this.setChinaTelecom(telecom);

		return bRslt;
	}

	/**
	 * 调用接口DirectSend,对于参数为中文的调用采用以下方法,为防止中文参数为乱码.
	 * 
	 * @param url
	 * @param directSendDTO
	 * @return 返回发送失败的手机号
	 */
	private Map<String, String> directSend(String url, String phones,
			String type, String sendTime, String fixNumber, String content,
			int phoneSize) {

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		PostMethod postMethod = new UTF8PostMethod(url + "/DirectSend");

		NameValuePair[] data = initPairs(phones, type, sendTime, fixNumber,
				content);
		postMethod.setRequestBody(data);
		postMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode;
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new SklayException(ErrorCode.API_CALL_ERROE);
			}
			byte[] responseBody = postMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}

			com.sklay.core.sdk.utils.JSONObject jsonObject = XML
					.toJSONObject(str);
			Reduced reduced = JSONObject.parseObject(
					jsonObject.getString("ROOT"), Reduced.class);

			String retCode = reduced.getRetCode();

			if (StringUtils.isNotBlank(retCode)
					&& "Sucess".equalsIgnoreCase(retCode)) {
				String errorPhone = reduced.getErrPhones();
				Integer okPhoneCount = reduced.getOKPhoneCounts();
				if (null != okPhoneCount && phoneSize == okPhoneCount)
					return Maps.newHashMap();

				if (StringUtils.isNotBlank(errorPhone))
					errorPhone = errorPhone.replaceFirst(";", "");
				if (StringUtils.isNotBlank(errorPhone)) {
					String[] errorPhones = errorPhone.split(";");
					Map<String, String> call = Maps.newHashMap();
					for (String phone : errorPhones)
						if (StringUtils.isNotBlank(phone))
							call.put(phone, reduced.getMessage());
					return call;
				}
				return Maps.newHashMap();
			}

			String[] errorPhones = phones.split(";");
			Map<String, String> call = Maps.newHashMap();
			for (String phone : errorPhones)
				if (StringUtils.isNotBlank(phone))
					call.put(phone, reduced.getMessage());
			return call;
		} catch (HttpException e) {
			e.printStackTrace();
			return Maps.newHashMap();
		} catch (IOException e) {
			e.printStackTrace();
			return Maps.newHashMap();
		} catch (JSONException e) {
			e.printStackTrace();
			return Maps.newHashMap();
		} finally {
			postMethod.releaseConnection();
		}

	}

	private NameValuePair[] initPairs(String phones, String type,
			String sendTime, String fixNumber, String content) {

		NameValuePair[] data = { new NameValuePair("UserID", this.userId),
				new NameValuePair("Account", this.accountId),
				new NameValuePair("Password", this.password),
				new NameValuePair("Phones", phones),
				new NameValuePair("SendType", type),
				new NameValuePair("SendTime", sendTime),
				new NameValuePair("PostFixNumber", fixNumber),
				new NameValuePair("Content", content) };

		return data;
	}

	private NameValuePair[] initPairs() {

		NameValuePair[] data = { new NameValuePair("UserID", this.userId),
				new NameValuePair("Account", this.accountId),
				new NameValuePair("Password", this.password) };

		return data;
	}

	private static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			return "UTF-8";
		}
	}

	/**
	 * 判断传入的参数号码为哪家运营商
	 * 
	 * @param mobile
	 * @return 运营商名称
	 */
	public SMSLog validateMobile(SMSLog smsLog) {
		/** mobile参数为空或者手机号码长度不为11，错误！ */
		if (smsLog == null
				|| StringUtils.isBlank(smsLog.getReceiver().getPhone())
				|| smsLog.getReceiver().getPhone().length() != 11) {

			smsLog.setOperator(OperatorType.PHONEERROE);
			return smsLog;
		}
		String mobile = smsLog.getReceiver().getPhone();
		String start = mobile.substring(0, 3);
		/** 移动 */
		if (StringUtils.isNotBlank(getChinaMobile())
				&& getChinaMobile().indexOf(start) != -1) {

			smsLog.setOperator(OperatorType.CHINAMOBILE);
			return smsLog;
		}
		/** 联通 */
		else if (StringUtils.isNotBlank(getChinaUnicom())
				&& getChinaUnicom().indexOf(start) != -1) {

			smsLog.setOperator(OperatorType.CHINAUNICOM);
			return smsLog;
		}
		/** 电信 */
		else if (StringUtils.isNotBlank(getChinaTelecom())
				&& getChinaTelecom().indexOf(start) != -1) {

			smsLog.setOperator(OperatorType.CHINATELECOM);
			return smsLog;

		}

		/** 未知运营商 */
		smsLog.setOperator(OperatorType.UNKNOWN);
		return smsLog;
	}

	/**
	 * 判断传入的参数号码为哪家运营商
	 * 
	 * @param mobile
	 * @return 运营商名称
	 */
	public Map<OperatorType, Set<SMSLog>> validateMobile(Set<SMSLog> mobiles) {

		Map<OperatorType, Set<SMSLog>> map = Maps.newHashMap();

		/** mobile参数为空或者手机号码长度不为11，错误！ */
		if (CollectionUtils.isEmpty(mobiles)) {
			return map;
		}
		Set<SMSLog> chinaMobileSet = Sets.newHashSet();
		Set<SMSLog> chinaUnicomSet = Sets.newHashSet();
		Set<SMSLog> chinaTelecomSet = Sets.newHashSet();
		for (SMSLog log : mobiles) {
			String start = log.getReceiver().getPhone().substring(0, 3);

			/** 移动 */
			if (StringUtils.isNotBlank(getChinaMobile())
					&& getChinaMobile().indexOf(start) != -1) {
				log.setOperator(OperatorType.CHINAMOBILE);
				chinaMobileSet.add(log);
			}
			/** 联通 */
			else if (StringUtils.isNotBlank(getChinaUnicom())
					&& getChinaUnicom().indexOf(start) != -1) {

				log.setOperator(OperatorType.CHINAUNICOM);
				chinaMobileSet.add(log);
			}
			/** 电信 */
			else if (StringUtils.isNotBlank(getChinaTelecom())
					&& getChinaTelecom().indexOf(start) != -1) {

				log.setOperator(OperatorType.CHINATELECOM);
				chinaMobileSet.add(log);
			}
		}

		if (CollectionUtils.isNotEmpty(chinaMobileSet))
			map.put(OperatorType.CHINAMOBILE, chinaMobileSet);
		if (CollectionUtils.isNotEmpty(chinaUnicomSet))
			map.put(OperatorType.CHINAUNICOM, chinaUnicomSet);
		if (CollectionUtils.isNotEmpty(chinaTelecomSet))
			map.put(OperatorType.CHINATELECOM, chinaTelecomSet);

		return map;
	}

	/**
	 * 判断传入的参数号码为哪家运营商
	 * 
	 * @param mobile
	 * @return 运营商名称
	 */
	public Map<OperatorType, Set<SMS>> validateSMSMobile(Set<SMS> mobiles) {

		Map<OperatorType, Set<SMS>> map = Maps.newHashMap();

		/** mobile参数为空或者手机号码长度不为11，错误！ */
		if (CollectionUtils.isEmpty(mobiles)) {
			return map;
		}
		Set<SMS> chinaMobileSet = Sets.newHashSet();
		Set<SMS> chinaUnicomSet = Sets.newHashSet();
		Set<SMS> chinaTelecomSet = Sets.newHashSet();
		for (SMS log : mobiles) {
			String start = log.getReceiver().substring(0, 3);

			/** 移动 */
			if (StringUtils.isNotBlank(getChinaMobile())
					&& getChinaMobile().indexOf(start) != -1) {
				chinaMobileSet.add(log);
			}
			/** 联通 */
			else if (StringUtils.isNotBlank(getChinaUnicom())
					&& getChinaUnicom().indexOf(start) != -1) {
				chinaMobileSet.add(log);
			}
			/** 电信 */
			else if (StringUtils.isNotBlank(getChinaTelecom())
					&& getChinaTelecom().indexOf(start) != -1) {
				chinaMobileSet.add(log);
			}
		}

		if (CollectionUtils.isNotEmpty(chinaMobileSet))
			map.put(OperatorType.CHINAMOBILE, chinaMobileSet);
		if (CollectionUtils.isNotEmpty(chinaUnicomSet))
			map.put(OperatorType.CHINAUNICOM, chinaUnicomSet);
		if (CollectionUtils.isNotEmpty(chinaTelecomSet))
			map.put(OperatorType.CHINATELECOM, chinaTelecomSet);

		return map;
	}

	/**
	 * 判断传入的参数号码为哪家运营商
	 * 
	 * @param mobile
	 * @return 运营商名称
	 */
	public Map<OperatorType, Set<SMSLog>> mergeValidateMobile(
			Set<SMSLog> mobiles) {

		Map<OperatorType, Set<SMSLog>> map = Maps.newHashMap();

		Map<OperatorType, Set<SMSLog>> result = this.validateMobile(mobiles);

		if (null == result || result.size() == 0)
			return null;

		Set<SMSLog> chinaMobileSet = Sets.newHashSet();
		Set<SMSLog> chinaTelecomSet = Sets.newHashSet();
		Set<OperatorType> keySet = result.keySet();
		for (OperatorType operatorType : keySet) {

			if (OperatorType.CHINAMOBILE == operatorType
					|| OperatorType.CHINAUNICOM == operatorType) {

				chinaMobileSet.addAll(result.get(operatorType));

			} else if (OperatorType.CHINATELECOM == operatorType) {

				chinaTelecomSet.addAll(result.get(operatorType));

			}
			if (CollectionUtils.isNotEmpty(chinaMobileSet))
				map.put(OperatorType.CHINAMOBILE, chinaMobileSet);
			if (CollectionUtils.isNotEmpty(chinaTelecomSet))
				map.put(OperatorType.CHINATELECOM, chinaTelecomSet);
		}

		return map;
	}

	/**
	 * 判断传入的参数号码为哪家运营商
	 * 
	 * @param mobile
	 * @return 运营商名称
	 */
	public Map<OperatorType, Set<SMS>> mergeSMSValidateMobile(Set<SMS> mobiles) {

		Map<OperatorType, Set<SMS>> map = Maps.newHashMap();

		Map<OperatorType, Set<SMS>> result = this.validateSMSMobile(mobiles);

		if (null == result || result.size() == 0)
			return null;

		Set<SMS> chinaMobileSet = Sets.newHashSet();
		Set<SMS> chinaTelecomSet = Sets.newHashSet();
		Set<OperatorType> keySet = result.keySet();
		for (OperatorType operatorType : keySet) {

			if (OperatorType.CHINAMOBILE == operatorType
					|| OperatorType.CHINAUNICOM == operatorType) {

				chinaMobileSet.addAll(result.get(operatorType));

			} else if (OperatorType.CHINATELECOM == operatorType) {

				chinaTelecomSet.addAll(result.get(operatorType));

			}
			if (CollectionUtils.isNotEmpty(chinaMobileSet))
				map.put(OperatorType.CHINAMOBILE, chinaMobileSet);
			if (CollectionUtils.isNotEmpty(chinaTelecomSet))
				map.put(OperatorType.CHINATELECOM, chinaTelecomSet);
		}

		return map;
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

	public String getChinaMobile() {
		return chinaMobile;
	}

	public void setChinaMobile(String chinaMobile) {
		this.chinaMobile = chinaMobile;
	}

	public String getChinaUnicom() {
		return chinaUnicom;
	}

	public void setChinaUnicom(String chinaUnicom) {
		this.chinaUnicom = chinaUnicom;
	}

	public String getChinaTelecom() {
		return chinaTelecom;
	}

	public void setChinaTelecom(String chinaTelecom) {
		this.chinaTelecom = chinaTelecom;
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
