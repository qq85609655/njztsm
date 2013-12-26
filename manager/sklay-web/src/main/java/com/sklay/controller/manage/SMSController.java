package com.sklay.controller.manage;

import java.util.Date;
import java.util.List;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.sdk.model.vo.SMSLoginInfo;
import com.sklay.core.sdk.model.vo.SMSSetting;
import com.sklay.core.sdk.model.vo.SMSStockDetail;
import com.sklay.core.util.Constants;
import com.sklay.core.util.PropertieUtils;
import com.sklay.model.Group;
import com.sklay.model.SMS;
import com.sklay.model.SMSLog;
import com.sklay.model.User;
import com.sklay.service.GroupService;
import com.sklay.service.SMSService;
import com.sklay.service.UserService;
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
	private SMSService smsService;

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "sms:model");
		model.addAttribute("subnav", "sms:list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("sms:list")
	@Widget(name = ":list", description = "短信列表")
	public String list(String keyword, Date startDate, Date endDate,
			Integer status, Long groupId,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		SMSStatus smsStatus = null == status ? null : SMSStatus
				.findByValue(status);

		User session = LoginUserHelper.getLoginUser();
		Long userOwner = session.getId();

		Group parentGroup = session.getGroup();
		Long parentGroupId = parentGroup.getId();

		List<Group> list = LoginUserHelper.isSuperAdmin() ? groupService
				.getGroupAll() : groupService.getGroupByOwner(session);

		String pageQuery = "";
		Set<Long> groupIds = Sets.newHashSet(parentGroupId);
		Set<Long> allGroupId = Sets.newHashSet(parentGroupId);

		Page<SMSLog> page = null;
		/** 取得当前分组 */
		if (CollectionUtils.isNotEmpty(list)) {
			for (Group currentGroup : list) {
				Long currentGroupId = currentGroup.getId();
				groupIds.add(currentGroupId);
			}
		}
		allGroupId.addAll(groupIds);

		boolean hasNext = (!LoginUserHelper.isSuperAdmin() && LoginUserHelper
				.isAdmin());

		while (hasNext) {
			List<Group> nextGroups = groupService.getGroupByParentId(groupIds);

			groupIds = Sets.newHashSet();

			if (CollectionUtils.isEmpty(nextGroups)) {
				hasNext = false;
				break;
			}
			if (CollectionUtils.isEmpty(list))
				list = Lists.newArrayList();

			list.addAll(nextGroups);

			for (Group currentGroup : nextGroups) {
				Long currentGroupId = currentGroup.getId();
				groupIds.add(currentGroupId);
			}
			allGroupId.addAll(groupIds);
		}

		if (LoginUserHelper.isSuperAdmin()) {
			userOwner = null;
			allGroupId.clear();
			if (null != groupId) {
				allGroupId.add(groupId);
			}
		} else {
			if (null != groupId) {
				allGroupId.clear();
				allGroupId.add(groupId);
			}
			if (CollectionUtils.isNotEmpty(allGroupId))
				userOwner = null;
		}

		// TODO
		page = null;// smsLogService.getSMSPage(keyword, startDate, endDate,
					// smsStatus,
		// userOwner, allGroupId, pageable);
		pageQuery = initQuery(keyword, startDate, endDate, status, userOwner);

		modelMap.addAttribute("checkedGroup", groupId);
		modelMap.addAttribute("groups", list);
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
		Set<SMS> smsLogs = Sets.newHashSet();

		User session = LoginUserHelper.getLoginUser();
		Date date = new Date();
		for (User reciver : reciverList) {
			SMS log = new SMS(session.getId(), content, date, reciver.getPhone(),
					SMSStatus.FAIL, date.getTime());
			smsLogs.add(log);
		}

		smsLogs = sklayApi.sendSMS(smsLogs);

		if (CollectionUtils.isNotEmpty(smsLogs))
			smsService.create(smsLogs);

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

		SMSLoginInfo loginInfo = null;// sklayApi.getSMSLogin();

		SMSStockDetail detail = null;// sklayApi.getSMSStockDetail(loginInfo);

		// sklayApi.getSMSLogOut(loginInfo);

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

		if (!oldSetting.getPwd().equals(smsSetting.getPwd())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setPwd(smsSetting.getPwd().trim());
		}

		if (!oldSetting.getSign().equals(smsSetting.getSign())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setSign(smsSetting.getSign().trim());
		}

		if (!oldSetting.getAccount().equals(smsSetting.getAccount())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setAccount(smsSetting.getAccount().trim());
		}

		if (!oldSetting.getPassword().equals(smsSetting.getPassword())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setPassword(smsSetting.getPassword().trim());
		}

		if (!oldSetting.getSendUrl().equals(smsSetting.getSendUrl())) {
			newSetting = null == newSetting ? new SMSSetting() : newSetting;
			newSetting.setSendUrl(smsSetting.getSendUrl().trim());
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

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initResend/{id}")
	@RequiresPermissions("sms:resend")
	@Widget(name = ":resend", description = "重新发送短信", level = WidgetLevel.THIRD)
	public String initResend(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "sms:resend");

		if (null == id)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信");
		SMS sms = smsService.getSMS(id);
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

		SMS sms = smsService.getSMS(id);
		if (null == sms)
			throw new SklayException(ErrorCode.FINF_NULL, null, "短信");

		sms.setContent(content);
		sms.setRemark(remark);

		DataView dataView = new DataView(0, "操作成功");
		String phones = sms.getReceiver() + ";";
		String SMSContent = sms.getContent();

		Set<SMS> call = sklayApi.sendSMS(Sets.newHashSet(sms));

		//TODO
//		smsService.update(call);

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
