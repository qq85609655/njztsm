package com.sklay.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.OperatorType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.sdk.model.vo.SMSLoginInfo;
import com.sklay.core.sdk.model.vo.SMSSetting;
import com.sklay.core.sdk.model.vo.SMSStockDetail;
import com.sklay.core.util.Constants;
import com.sklay.core.util.PropertieUtils;
import com.sklay.model.SMSLog;
import com.sklay.model.User;
import com.sklay.service.SMSLogService;
import com.sklay.service.UserService;
import com.sklay.util.Convert;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

/**
 * 
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-1
 */
@Controller
@RequestMapping("/admin/sms")
@Widgets(value = "sms", description = "短信管理")
public class SMSController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SMSController.class);

	@Autowired
	private SMSLogService smsLogService;

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "sms:model");
		model.addAttribute("subnav", "sms:list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("sms:list")
	@Widget(name = ":list", description = "短信列表")
	public String list(String keyword, Date startDate, Date endDate,
			Integer status, @PageableDefaults(value = 20) Pageable pageable,
			ModelMap modelMap) {

		SMSStatus smsStatus = null == status ? null : SMSStatus
				.findByValue(status);

		Long userOwner = LoginUserHelper.getLoginUser().getId();

		if (LoginUserHelper.isSuperAdmin())
			userOwner = null;

		Page<SMSLog> page = smsLogService.getSMSPage(keyword, startDate,
				endDate, smsStatus, userOwner, pageable);
		String pageQuery = initQuery(keyword, startDate, endDate, status,
				userOwner);

		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("keyword", keyword);
		modelMap.addAttribute("checkedStatus", smsStatus);

		return "manager.sms.list";
	}

	@RequestMapping("/initJob")
	@RequiresPermissions("sms:job")
	@Widget(name = ":job", description = "短信任务")
	public String initJob(String phones, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:job");
		modelMap.addAttribute("phones", phones);

		return "manager.sms.job";
	}

	@RequestMapping("/job")
	@RequiresPermissions("sms:job")
	@ResponseBody
	public DataView job(String phones, String content, ModelMap modelMap) {

		if (StringUtils.isBlank(phones) || StringUtils.isBlank(content))
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码或短信类容");

		String[] phoneArray = phones.split(",");
		if (null == phoneArray || Constants.ZERO == phoneArray.length)
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码");

		Set<String> phoneSet = Sets.newHashSet();
		for (String phone : phoneArray) {
			if (StringUtils.isBlank(phone))
				continue;
			if (!MobileUtil.isMobile(phone)) {
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号码"
						+ phone);
			}
			phoneSet.add(phone);
		}

		if (CollectionUtils.isEmpty(phoneSet))
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码");

		List<User> reciverList = userService.getUserByPhone(phoneSet);

		if (CollectionUtils.isEmpty(reciverList)
				|| phoneSet.size() != reciverList.size()) {
			if (CollectionUtils.isEmpty(reciverList))
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号码"
						+ phoneSet.toString() + "不是系统会员");
			for (User user : reciverList)
				if (!phoneSet.contains(user.getPhone()))
					throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
							"手机号码" + user.getPhone() + "不是系统会员");
		}
		Set<SMSLog> smsLogs = Sets.newHashSet();

		User session = LoginUserHelper.getLoginUser();
		Date date = new Date();
		for (User reciver : reciverList) {
			SMSLog log = new SMSLog(session, content, date, reciver,
					date.getTime(), SMSStatus.FAIL);
			smsLogs.add(log);
		}

		/** 检查用户手机类型 */
		Map<OperatorType, Set<SMSLog>> mobileResult = sklayApi
				.mergeValidateMobile(smsLogs);

		Set<SMSLog> allSMSLog = Sets.newHashSet();
		Set<OperatorType> keyset = mobileResult.keySet();
		for (OperatorType key : keyset)
			allSMSLog.addAll(mobileResult.get(key));

		Map<String, String> recivers = sklayApi.sendSMS(
				Convert.toSMSPhoneMap(mobileResult), content);

		Set<SMSLog> smsLogSet = Convert.toPhoneSMSLogSet(recivers, allSMSLog);

		if (CollectionUtils.isNotEmpty(smsLogSet))
			smsLogService.createSMSLog(smsLogSet);

		modelMap.addAttribute("nav", "sms");
		modelMap.addAttribute("subnav", "sms:job");

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initSetting")
	@RequiresPermissions("sms:setting")
	@Widget(name = ":setting", description = "短信配置")
	public String initSetting(ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:setting");
		modelMap.addAttribute("active", "api");

		SMSSetting smsSetting = sklayApi.getSMSSetting();

		SMSLoginInfo loginInfo = sklayApi.getSMSLogin();

		SMSStockDetail detail = sklayApi.getSMSStockDetail(loginInfo);

		sklayApi.getSMSLogOut(loginInfo);

		modelMap.addAttribute("smsSetting", smsSetting);
		modelMap.addAttribute("smsInfo", loginInfo);
		modelMap.addAttribute("smsDetail", detail);

		modelMap.addAttribute("readonly", "readonly='readonly'");

		return "manager.sms.setting";
	}

	@RequestMapping("/setting")
	@RequiresPermissions("sms:setting")
	@ResponseBody
	public DataView setting(SMSSetting smsSetting, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:setting");
		modelMap.addAttribute("active", "api");
		if (null == smsSetting)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信配置");

		SMSSetting oldSetting = sklayApi.getSMSSetting();
		if (null == oldSetting)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信默认配置");

		SMSSetting newSetting = null;

		if (!oldSetting.getPhysical().equals(smsSetting.getPhysical())) {
			newSetting = new SMSSetting();
			newSetting.setPhysical(smsSetting.getPhysical().trim());
		}

		if (!oldSetting.getSos().equals(smsSetting.getSos())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setSos(smsSetting.getSos().trim());
		}

		if (null != newSetting) {
			PropertieUtils.instance(newSetting);
			sklayApi.setSMSSetting(newSetting);
		}
		return new DataView(0, "操作成功");
	}

	@RequestMapping("/paragraph")
	@RequiresPermissions("sms:setting")
	@ResponseBody
	public DataView paragraph(String mobile, String unicom, String telecom,
			ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:setting");
		modelMap.addAttribute("active", "phone");
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(unicom)
				|| StringUtils.isBlank(telecom))
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信号码段配置");

		PropertieUtils.instance(mobile, unicom, telecom);
		sklayApi.setMobiles(mobile, unicom, telecom);
		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initResend/{id}")
	@RequiresPermissions("sms:resend")
	@Widget(name = ":resend", description = "重新发送短信", level = WidgetLevel.THIRD)
	public String initResend(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:resend");

		if (null == id)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信");
		SMSLog sms = smsLogService.getSMS(id);
		if (null == sms)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信");
		modelMap.addAttribute("model", sms);

		return "modal:sms.resend";
	}

	@RequestMapping("/resend/{id}")
	@RequiresPermissions("sms:resend")
	@ResponseBody
	public DataView resend(@PathVariable Long id, String content, String remark) {
		LOGGER.debug("resend sms id is {}", id);
		if (null == id)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "短信Id");

		if (StringUtils.isBlank(content))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "短信内容");

		SMSLog sms = smsLogService.getSMS(id);
		if (null == sms)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信");

		sms.setContent(content);
		sms.setRemark(remark);

		DataView dataView = new DataView(0, "操作成功");
		String phones = sms.getReceiver().getPhone() + ";";
		String type = OperatorType.CHINAMOBILE == sms.getOperator()
				|| OperatorType.CHINAUNICOM == sms.getOperator() ? Constants.MOBILE2NUICOM
				: Constants.TELECOM;
		String SMSContent = sms.getContent();

		Map<String, String> call = sklayApi.reSendSMS(phones, type, SMSContent);

		if (null != call && call.size() > Constants.ZERO) {
			sms.setStatus(SMSStatus.FAIL);
			sms.setRemark(sms.getRemark() + ";" + call.get(sms.getReceiver()));
			dataView.setCode(-1);
			dataView.setMsg(call.get(sms.getReceiver()));
		} else {
			sms.setSendTime(new Date());
			sms.setStatus(SMSStatus.SUCCESS);
		}
		smsLogService.updateSMSLog(sms);

		return dataView;
	}

	private static String initQuery(String keyword, Date startDate,
			Date endDate, Integer status, Long userOwner) {

		String query = "";

		if (null != userOwner)
			query += "userOwner=" + userOwner + "&";
		if (null != status)
			query += "status=" + status + "&";
		if (null != endDate && null != startDate)
			query += "startDate=" + startDate + "&endDate=" + endDate + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

}
