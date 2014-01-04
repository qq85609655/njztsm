package com.sklay.api;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.sdk.exceptions.JSONException;
import com.sklay.core.sdk.model.Request;
import com.sklay.core.sdk.model.Response;
import com.sklay.core.sdk.model.Verb;
import com.sklay.core.sdk.model.vo.LocationDetail;
import com.sklay.core.sdk.model.vo.SMSSetting;
import com.sklay.core.util.Constants;
import com.sklay.enums.LogLevelType;
import com.sklay.enums.SMSResult;
import com.sklay.mobile.Update;
import com.sklay.model.Blackword;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.service.BlackwordService;

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

	@Value("${" + Constants.SMS_TEMPLATE_PHYSICAL + "}")
	private String physical_tpl;

	@Value("${" + Constants.SMS_TEMPLATE_SOS + "}")
	private String sos_tpl;

	@Value("${" + Constants.SMS_TEMPLATE_PWD + "}")
	private String pwd_tpl;

	@Value("${" + Constants.SMS_TEMPLATE_SIGN + "}")
	private String sign_tpl;

	@Value("${" + Constants.SMS_SOS_PAIRS + "}")
	private String sosPairs;

	@Value("${" + Constants.SMS_PWD_PAIRS + "}")
	private String pwdPairs;

	@Value("${" + Constants.SMS_BIRTHDAY_PAIRS + "}")
	private String birthPairs;

	@Value("${" + Constants.SMS_CHANGE_PWD + "}")
	private String changePwd;

	@Value("${" + Constants.SMS_BALANCE + "}")
	private String balance;

	@Value("${" + Constants.SMS_TEMPLATE_BIRTHDAY + "}")
	private String birthday_tpl;

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

	@Resource
	private BlackwordService blackwordService;

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
		Set<SMS> smsResult = Sets.newHashSet();

		if (CollectionUtils.isNotEmpty(smsList)) {

			Set<String> blackwords = blackwordList();

			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				String content = sms.getContent();

				content = replaceBlackword(content, blackwords);

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("signtag", sign_tpl);
				pairs.put("mobile", sms.getMobile());
				pairs.put("content", content);

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));
				sms.setResult(result);

				sms.setContent(content);
				SMSResult sRslt = SMSResult.findByLable(result);
				if (null != sRslt)
					sms.setResult(sms.getResult() + "【" + sRslt.getLable()
							+ "】");
				if (SMSResult.SUCCESS != sRslt)
					sms.setStatus(SMSStatus.FAIL);
				else if (SMSResult.SUCCESS == sRslt)
					sms.setStatus(SMSStatus.SUCCESS);
				LOGGER.info("physical result {} " + result);
				smsResult.add(sms);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsResult;
	}

	/**
	 * 修改短信密码
	 * 
	 * @param newPwd
	 * @return
	 */
	public SMSResult changePwd(String newPwd) {

		if (StringUtils.isNotBlank(newPwd)) {
			Map<String, String> pairs = Maps.newHashMap();

			pairs.put("username", account);
			pairs.put("scode", password);
			pairs.put("newscode", newPwd);

			String result = ApiClient.http_post(changePwd,
					ApiClient.splitNameValuePair(pairs));

			Operation operation = new Operation();

			operation.setType(LogLevelType.API);
			operation.setCreateTime(new Date());
			operation.setName("修改短信平台密码");
			operation.setContent("旧密码为：" + password + "新密码为【" + newPwd + "】");
			operation.setDesctiption(result);

			SMSResult sRslt = SMSResult.findByLable(result);
			if (null != sRslt)
				operation.setDesctiption(operation.getDesctiption() + "【"
						+ sRslt.getLable() + "】");
			LOGGER.info("change password result {} " + result);
			return sRslt;
		}

		return null;
	}

	/**
	 * 查询余额： 0# 数字查询成功，格式：返回值#短信余额条数 余额条数
	 */
	public Operation balance() {
		Map<String, String> pairs = Maps.newHashMap();

		pairs.put("username", account);
		pairs.put("scode", password);

		String result = ApiClient.http_post(balance,
				ApiClient.splitNameValuePair(pairs));

		Operation operation = new Operation();

		operation.setName("查询短信剩余量");
		operation.setContent(result);

		SMSResult sRslt = SMSResult.findByLable(result);
		if (null != sRslt) {
			operation.setDesctiption("【" + sRslt.getLable() + "】");
			if (SMSResult.SUCCESS == sRslt)
				operation.setContent(result.replaceFirst("0#", ""));
		}
		LOGGER.info("change password result {} " + result);
		return operation;

	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> sms(Set<SMS> smsList) {
		Set<SMS> smsResult = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(smsList)) {

			Set<String> blackwords = blackwordList();

			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				String content = sms.getContent();

				content = replaceBlackword(content, blackwords);
				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("signtag", sign_tpl);
				pairs.put("mobile", sms.getMobile());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));
				sms.setResult(result);
				sms.setContent(content);
				SMSResult sRslt = SMSResult.findByLable(result);
				if (null != sRslt)
					sms.setResult(sms.getResult() + "【" + sRslt.getLable()
							+ "】");
				if (SMSResult.SUCCESS != sRslt)
					sms.setStatus(SMSStatus.FAIL);
				else if (SMSResult.SUCCESS == sRslt)
					sms.setStatus(SMSStatus.SUCCESS);
				LOGGER.info("sms result {} " + result);
				smsResult.add(sms);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsResult;
	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> resetPwd(Set<SMS> smsList) {
		Set<SMS> smsResult = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("tempid", pwd_tpl);
				pairs.put("signtag", sign_tpl);
				pairs.put("mobile", sms.getMobile());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));
				sms.setResult(result);
				sms.setRemark(pwd_tpl);
				SMSResult sRslt = SMSResult.findByLable(result);
				if (null != sRslt)
					sms.setResult(sms.getResult() + "【" + sRslt.getLable()
							+ "】");
				if (SMSResult.SUCCESS != sRslt)
					sms.setStatus(SMSStatus.FAIL);
				else if (SMSResult.SUCCESS == sRslt)
					sms.setStatus(SMSStatus.SUCCESS);
				LOGGER.info("resetPwd result {} " + result);
				smsResult.add(sms);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsResult;
	}

	/**
	 * @param recivers
	 * @param SMSContent
	 * @return
	 */
	public Set<SMS> birthday(Set<SMS> smsList) {
		Set<SMS> smsResult = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(smsList)) {
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("tempid", birthday_tpl);
				pairs.put("signtag", sign_tpl);
				pairs.put("mobile", sms.getMobile());
				pairs.put("content", sms.getContent());

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));
				sms.setResult(result);
				sms.setRemark(birthday_tpl);
				SMSResult sRslt = SMSResult.findByLable(result);
				if (null != sRslt)
					sms.setResult(sms.getResult() + "【" + sRslt.getLable()
							+ "】");
				if (SMSResult.SUCCESS != sRslt)
					sms.setStatus(SMSStatus.FAIL);
				else if (SMSResult.SUCCESS == sRslt)
					sms.setStatus(SMSStatus.SUCCESS);
				LOGGER.info("resetPwd result {} " + result);
				smsResult.add(sms);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}
			}
		}

		return smsResult;
	}

	public Set<SMS> sos(Set<SMS> smsList) {
		Set<SMS> smsResult = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(smsList)) {
			Set<String> blackwords = blackwordList();
			for (SMS sms : smsList) {
				Map<String, String> pairs = Maps.newHashMap();

				String content = sms.getContent();
				content = replaceBlackword(content, blackwords);

				pairs.put("username", account);
				pairs.put("scode", password);
				pairs.put("tempid", sos_tpl);
				pairs.put("signtag", sign_tpl);
				pairs.put("mobile", sms.getMobile());
				pairs.put("content", content);

				String result = ApiClient.http_post(sendUrl,
						ApiClient.splitNameValuePair(pairs));
				sms.setResult(result);
				sms.setRemark(sos_tpl);
				sms.setContent(content);

				SMSResult sRslt = SMSResult.findByLable(result);
				if (null != sRslt)
					sms.setResult(sms.getResult() + "【" + sRslt.getLable()
							+ "】");
				if (SMSResult.SUCCESS != sRslt)
					sms.setStatus(SMSStatus.FAIL);
				else if (SMSResult.SUCCESS == sRslt)
					sms.setStatus(SMSStatus.SUCCESS);
				smsResult.add(sms);

				LOGGER.info("sos result {} " + result);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new SklayException(e);
				}

			}
		}

		return smsResult;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher((CharSequence) str);
		return matcher.matches();
	}

	public boolean setSMSSetting(SMSSetting smsSetting) {
		boolean bRslt = true;

		if (StringUtils.isNotBlank(smsSetting.getPhysicalTpl()))
			this.setPhysical_tpl(smsSetting.getPhysicalTpl().trim());

		if (StringUtils.isNotBlank(smsSetting.getSosTpl()))
			this.setSos_tpl(smsSetting.getSosTpl().trim());

		if (StringUtils.isNotBlank(smsSetting.getSignTpl()))
			this.setSos_tpl(smsSetting.getSignTpl().trim());

		if (StringUtils.isNotBlank(smsSetting.getAccount()))
			this.setAccount(smsSetting.getAccount().trim());

		if (StringUtils.isNotBlank(smsSetting.getBalance()))
			this.setBalance(smsSetting.getBalance().trim());

		if (StringUtils.isNotBlank(smsSetting.getChangePwd()))
			this.setChangePwd(smsSetting.getChangePwd().trim());

		if (StringUtils.isNotBlank(smsSetting.getPassword()))
			this.setPassword(smsSetting.getPassword().trim());

		if (StringUtils.isNotBlank(smsSetting.getPwdTpl()))
			this.setPwd(smsSetting.getPwdTpl().trim());

		if (StringUtils.isNotBlank(smsSetting.getPwdPairs()))
			this.setPwdPairs(smsSetting.getPwdPairs().trim());

		if (StringUtils.isNotBlank(smsSetting.getSendUrl()))
			this.setSendUrl(smsSetting.getSendUrl().trim());

		if (StringUtils.isNotBlank(smsSetting.getSosPairs()))
			this.setSosPairs(smsSetting.getSosPairs().trim());

		if (StringUtils.isNotBlank(smsSetting.getBirthdayTpl()))
			this.setBirthday_tpl(smsSetting.getBirthdayTpl().trim());

		if (StringUtils.isNotBlank(smsSetting.getBirthPairs()))
			this.setBirthPairs(smsSetting.getBirthPairs().trim());

		return bRslt;
	}

	public SMSSetting getSMSSetting() {
		return new SMSSetting(pwd_tpl, account, password, sendUrl,
				physical_tpl, sos_tpl, sign_tpl, sosPairs, pwdPairs, changePwd,
				balance, birthday_tpl, birthPairs);
	}

	public String getPhysical_tpl() {
		return physical_tpl;
	}

	public void setPhysical_tpl(String physical_tpl) {
		this.physical_tpl = physical_tpl;
	}

	public String getSosPairs() {
		return sosPairs;
	}

	public String getPwdPairs() {
		return pwdPairs;
	}

	public String getSos_tpl() {
		return sos_tpl;
	}

	public void setSos_tpl(String sos_tpl) {
		this.sos_tpl = sos_tpl;
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

	public String getSearchURL() {
		return searchURL;
	}

	public void setSearchURL(String searchURL) {
		this.searchURL = searchURL;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getPwd_tpl() {
		return pwd_tpl;
	}

	public void setPwd(String pwd_tpl) {
		this.pwd_tpl = pwd_tpl;
	}

	public String getSign_tpl() {
		return sign_tpl;
	}

	public void setSign_tpl(String sign_tpl) {
		this.sign_tpl = sign_tpl;
	}

	public String getBirthday_tpl() {
		return birthday_tpl;
	}

	public void setBirthday_tpl(String birthday_tpl) {
		this.birthday_tpl = birthday_tpl;
	}

	public String getBirthPairs() {
		return birthPairs;
	}

	public void setBirthPairs(String birthPairs) {
		this.birthPairs = birthPairs;
	}

	public String getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setSosPairs(String sosPairs) {
		this.sosPairs = sosPairs;
	}

	public void setPwdPairs(String pwdPairs) {
		this.pwdPairs = pwdPairs;
	}

	private Set<String> blackwordList() {

		Set<String> wordList = Sets.newHashSet();
		List<Blackword> list = blackwordService.blackwordList();

		if (CollectionUtils.isNotEmpty(list)) {
			for (Blackword word : list) {
				if (StringUtils.isNotBlank(word.getBlackword()))
					wordList.add(word.getBlackword().trim());
			}
		}

		return wordList;
	}

	private String replaceBlackword(String content, Set<String> blackwords) {

		if (StringUtils.isEmpty(content) || CollectionUtils.isEmpty(blackwords))
			return content;

		for (String blackword : blackwords) {
			if (content.contains(blackword)) {
				content = content.replaceAll(blackword, appendSpace(blackword));
			}
		}
		return content;
	}

	public static String appendSpace(String para) {
		int length = para.length();
		char[] value = new char[length << 1];
		for (int i = 0, j = 0; i < length; ++i, j = i << 1) {
			value[j] = para.charAt(i);
			value[1 + j] = ' ';
		}
		return (new String(value)).trim();
	}

	public static void main(String[] args) {
		// String str = "0#1230#";
		// System.out.println(str.replaceFirst("0#", ""));
		// String str = "12dfg中三z中ss三 级ss在12";
		// String str2 = "三 级";
		// System.out.println(str2.length());
		System.out.println(appendSpace("三    级中三 级中三 级中"));

	}

}
