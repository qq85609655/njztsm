package com.sklay.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.enums.OperatorType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.sdk.model.vo.SMSView;
import com.sklay.core.support.AuthBase;
import com.sklay.model.Group;
import com.sklay.model.SMS;
import com.sklay.model.SMSLog;
import com.sklay.model.User;

public class Convert {

	public static Map<OperatorType, Set<SMSView>> toSMSViewMap(
			Map<OperatorType, Set<SMSLog>> in) {

		Map<OperatorType, Set<SMSView>> map = Maps.newHashMap();

		if (null == in || in.size() <= 0)
			return map;

		Set<OperatorType> keySet = in.keySet();

		for (OperatorType key : keySet) {
			Set<SMSView> viewSet = toSMSViewSet(in.get(key));
			if (CollectionUtils.isNotEmpty(viewSet))
				map.put(key, viewSet);
		}

		return map;
	}

	public static Map<OperatorType, Set<String>> toSMSPhoneMap(
			Map<OperatorType, Set<SMSLog>> in) {

		Map<OperatorType, Set<String>> map = Maps.newHashMap();

		if (null == in || in.size() <= 0)
			return map;

		Set<OperatorType> keySet = in.keySet();

		for (OperatorType key : keySet) {
			Set<String> phoneSet = Sets.newHashSet();
			for (SMSLog smsLog : in.get(key))
				if (null != smsLog
						&& StringUtils.isNotBlank(smsLog.getReceiver()
								.getPhone()))
					phoneSet.add(smsLog.getReceiver().getPhone());
			if (CollectionUtils.isNotEmpty(phoneSet))
				map.put(key, phoneSet);
		}

		return map;
	}

	public static Map<OperatorType, Set<String>> toPhoneMap(
			Map<OperatorType, Set<SMS>> in) {

		Map<OperatorType, Set<String>> map = Maps.newHashMap();

		if (null == in || in.size() <= 0)
			return map;

		Set<OperatorType> keySet = in.keySet();

		for (OperatorType key : keySet) {
			Set<String> phoneSet = Sets.newHashSet();
			for (SMS smsLog : in.get(key))
				if (null != smsLog
						&& StringUtils.isNotBlank(smsLog.getReceiver()))
					phoneSet.add(smsLog.getReceiver());
			if (CollectionUtils.isNotEmpty(phoneSet))
				map.put(key, phoneSet);
		}

		return map;
	}

	public static Map<OperatorType, Set<SMSLog>> toSMSLogMap(
			Map<OperatorType, Set<SMSView>> in) {

		Map<OperatorType, Set<SMSLog>> map = Maps.newHashMap();
		if (null == in || in.size() <= 0)
			return map;
		Set<OperatorType> keySet = in.keySet();

		for (OperatorType key : keySet) {
			Set<SMSLog> logSet = toSMSLogSet(in.get(key));
			if (CollectionUtils.isNotEmpty(logSet))
				map.put(key, logSet);
		}
		return map;
	}

	public static Set<SMSLog> toSMSLogSet(Set<SMSView> in) {
		Set<SMSLog> set = Sets.newHashSet();
		if (CollectionUtils.isEmpty(in))
			return set;
		for (SMSView view : in) {
			SMSLog log = toSMSLog(view);
			if (null != log)
				set.add(log);
		}
		return set;
	}

	public static Set<SMSLog> toPhoneSMSLogSet(Map<String, String> result,
			Set<SMSLog> in) {

		for (SMSLog sms : in) {
			String phone = sms.getReceiver().getPhone();
			if (null != result && result.size() > 0) {
				Set<String> keys = result.keySet();
				if (keys.contains(phone)) {
					sms.setStatus(SMSStatus.FAIL);
					sms.setRemark(result.get(phone));
					continue;
				}
				sms.setStatus(SMSStatus.SUCCESS);
				continue;
			}
			sms.setStatus(SMSStatus.SUCCESS);
		}
		return in;
	}

	public static Set<SMS> toPhoneSMSSet(Map<String, String> result, Set<SMS> in) {

		for (SMS sms : in) {
			String phone = sms.getReceiver();
			if (null != result && result.size() > 0) {
				Set<String> keys = result.keySet();
				if (keys.contains(phone)) {
					sms.setStatus(SMSStatus.FAIL);
					continue;
				}
				sms.setStatus(SMSStatus.SUCCESS);
				continue;
			}
			sms.setStatus(SMSStatus.SUCCESS);
		}
		return in;
	}

	public static Set<SMSView> toSMSViewSet(Set<SMSLog> in) {
		Set<SMSView> set = Sets.newHashSet();
		if (CollectionUtils.isEmpty(in))
			return set;
		for (SMSLog log : in) {
			SMSView view = toSMSView(log);
			if (null != view)
				set.add(view);
		}
		return set;
	}

	public static SMSLog toSMSLog(SMSView in) {
		if (null == in)
			return null;
		String sms = JSONObject.toJSONString(in);
		SMSLog log = JSONObject.parseObject(sms, SMSLog.class);
		return log;
	}

	public static SMSView toSMSView(SMSLog in) {
		if (null == in)
			return null;
		String sms = JSONObject.toJSONString(in);
		SMSView view = JSONObject.parseObject(sms, SMSView.class);
		return view;
	}

	public static Map<String, AuthBase> spiltUserAuth(User user,
			Map<String, AuthBase> call) {
		Group group = user.getGroup();

		String perm = (null == group) ? "" : (StringUtils.isBlank(group
				.getPerms()) ? "" : group.getPerms());

		List<String> perms = (StringUtils.isBlank(perm) ? null : JSONObject
				.parseArray(group.getPerms(), String.class));
		Map<String, AuthBase> result = Maps.newHashMap();
		if (call != null && CollectionUtils.isNotEmpty(perms)) {
			Set<String> keySet = call.keySet();
			for (String key : keySet) {
				if (!perms.contains(key))
					continue;
				AuthBase widgetInfo = null;
				if (result.containsKey(key))
					widgetInfo = result.get(key);
				else {
					widgetInfo = call.get(key);
					List<AuthBase> child = Lists.newArrayList();
					if (CollectionUtils.isEmpty(widgetInfo.getChildBase()))
						result.put(key, widgetInfo);
					else {
						for (AuthBase widgetChild : widgetInfo.getChildBase()) {
							if (perms.contains(widgetChild.getName()))
								child.add(widgetChild);
						}
						widgetInfo.setChildBase(child);
						result.put(key, widgetInfo);
					}

				}

			}

		}

		return result;
	}
}
