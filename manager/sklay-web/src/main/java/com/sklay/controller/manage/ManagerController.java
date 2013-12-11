/*
 * Project:  sklay
 * Module:   sklay-web
 * File:     LoginoutController.java
 * Modifier: fuyu
 * Modified: 2012-12-14 下午2:17:48 
 *
 * Copyright (c) 2012 SKLAY Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.controller.manage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.annotation.AvoidDuplicateSubmission;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.TipType;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.BeanUtils;
import com.sklay.core.util.Constants;
import com.sklay.core.util.PropertieUtils;
import com.sklay.dao.SpecificDao;
import com.sklay.enums.LogLevelType;
import com.sklay.mobile.Update;
import com.sklay.model.Application;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.MedicalReport;
import com.sklay.model.Operation;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
import com.sklay.service.GlobalService;
import com.sklay.service.GroupService;
import com.sklay.service.MedicalReportService;
import com.sklay.util.LoginUserHelper;
import com.sklay.vo.DataView;

/**
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-6-27
 */
@Controller
@RequestMapping("/admin")
@Widgets(value = "admin", description = "后台管理")
public class ManagerController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManagerController.class);

	@Autowired
	private SpecificDao specificDao;

	@Autowired
	private GlobalService globalService;

	@Autowired
	private ApplicationService appService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private MedicalReportService medicalReportService;

	@Autowired
	private SklayApi sklayApi;

	@RequestMapping
	@RequiresUser
	public String index(String keyword, TipType tipType, ReadType readType,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		User session = LoginUserHelper.getLoginUser();
		if (AuditStatus.PASS == session.getStatus()
				&& AuditStatus.PASS == session.getGroup().getStatus()) {
			tipType = TipType.WARNING;
			readType = ReadType.WAIT;

			modelMap = medicalTips(keyword, tipType, readType, pageable,
					modelMap);
		}
		return "manager:index";
	}

	@RequiresPermissions("admin:cms")
	@Widget(name = ":cms", description = "CMS管理", level = WidgetLevel.THIRD)
	public void cmsManager() {
	}

	@RequestMapping("/app/list")
	@RequiresPermissions("admin:appList")
	@Widget(name = ":appList", description = "应用申请列表")
	public String appList(AuditStatus status, AppType appType, String keyword,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {
		modelMap.addAttribute("nav", "admin:model");
		modelMap.addAttribute("subnav", "admin:appList");

		User creator = null;

		if (!LoginUserHelper.isAdmin()) {
			creator = LoginUserHelper.getLoginUser();
		}
		Page<Application> page = appService.getPage(keyword, appType, status,
				creator, pageable);

		modelMap.addAttribute("status", status);
		modelMap.addAttribute("app", appType);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", initQuery(keyword, status, appType));
		modelMap.addAttribute("keyword", keyword);

		return "manager.app.page";
	}

	private ModelMap medicalTips(String keyword, TipType tipType,
			ReadType readType, Pageable pageable, ModelMap modelMap) {

		User creator = LoginUserHelper.getLoginUser();

		Group parentGroup = creator.getGroup();
		Set<Group> groups = Sets.newHashSet();
		Page<MedicalReport> page = null;

		if (MemberRole.ADMINSTROTAR == parentGroup.getRole()) {
			if (null == parentGroup.getParentGroupId()) {
				creator = null;
				page = medicalReportService.search(keyword, tipType, readType,
						creator, groups, pageable);
			} else {

				List<Group> list = groupService.getGroupByOwner(creator);
				Set<Long> groupIds = Sets.newHashSet(parentGroup
						.getParentGroupId());
				/** 取得当前分组 */
				if (CollectionUtils.isNotEmpty(list)) {
					for (Group currentGroup : list) {
						Long currentGroupId = currentGroup.getId();
						groupIds.add(currentGroupId);
					}
				}

				boolean hasNext = true;

				while (hasNext) {

					List<Group> nextGroups = groupService
							.getGroupByParentId(groupIds);

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
				}

				if (CollectionUtils.isNotEmpty(list)) {
					groups = Sets.newHashSet(list);
					creator = null;
				}

				page = medicalReportService.search(keyword, tipType, readType,
						creator, groups, pageable);
			}
		} else
			page = medicalReportService.search(keyword, tipType, readType,
					creator, groups, pageable);

		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery",
				initQuery(keyword, tipType, readType));
		modelMap.addAttribute("keyword", keyword);

		return modelMap;
	}

	@RequestMapping("app/{id}/audit")
	@RequiresPermissions("admin:appAudit")
	@Widget(name = ":appAudit", description = "应用审核", level = WidgetLevel.THIRD)
	public String initAppAudit(@PathVariable Long id, ModelMap modelMap) {

		Application app = null;

		if (null != id)
			app = appService.get(id);

		modelMap.addAttribute("model", app);
		return "modal:app.audit";
	}

	@RequestMapping("app/audit")
	@RequiresPermissions("admin:appAudit")
	@ResponseBody
	public DataView appAudit(Application app) {

		if (null != app && null != app.getId() && null != app.getStatus()) {
			Application orignal = appService.get(app.getId());
			if (null != orignal && app.getStatus() != orignal.getStatus()) {
				orignal.setStatus(app.getStatus());
				orignal.setUpdator(LoginUserHelper.getLoginUser());
				orignal.setUpdatorTime(new Date());
				appService.update(orignal);
			}
		}

		return new DataView();
	}

	@RequestMapping("app/{id}/detail")
	@RequiresPermissions("admin:detail")
	@Widget(name = ":detail", description = "应用详情", level = WidgetLevel.THIRD)
	public String initAppDetail(@PathVariable Long id, ModelMap modelMap) {

		Application app = null;

		if (null != id)
			app = appService.get(id);

		modelMap.addAttribute("model", app);
		return "modal:app.detail";
	}

	@RequestMapping("/initSetting")
	@RequiresPermissions("admin:setting")
	@AvoidDuplicateSubmission(needSaveToken = true)
	@Widget(name = ":setting", description = "系统配置")
	public String initSetting(ModelMap modelMap) {
		modelMap.addAttribute("nav", "admin:model");
		modelMap.addAttribute("subnav", "admin:setting");

		GlobalSetting setting = specificDao.getGlobalConfig();
		Update mobileVer = sklayApi.getMobileAndroidVer();

		modelMap.addAttribute("setting", setting);
		modelMap.addAttribute("mobileVer", mobileVer);

		return "manager.system.setting";
	}

	@RequestMapping("/upver")
	@RequiresPermissions("admin:setting")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public String uploadVersion(Update update, String active,
			@RequestParam("ver") MultipartFile ver, HttpServletRequest request,
			ModelMap modelMap) {

		modelMap.addAttribute("nav", "admin:model");
		modelMap.addAttribute("subnav", "admin:setting");
		modelMap.addAttribute("active", active);

		String error = null;
		String success = null;
		if (null == update) {
			error = "客户端版本信息不能为空";
			modelMap.addAttribute("error", error);
			return initSetting(modelMap);
		}

		if (StringUtils.isBlank(update.getVersionName())
				|| StringUtils.isBlank(update.getDownloadUrl())
				|| StringUtils.isBlank(update.getUpdateLog())
				|| update.getVersionCode() < 1) {
			error = "客户端版本信息不完整";
			modelMap.addAttribute("error", error);
			return initSetting(modelMap);
		}

		if (null == ver || ver.getSize() <= 0) {
			error = "请选择要发布的客户端文件";
			modelMap.addAttribute("error", error);
			return initSetting(modelMap);
		}
		String filename = ver.getOriginalFilename();
		String extName = filename.substring(filename.lastIndexOf("."))
				.toLowerCase();
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path + Constants.APP_PUBLISH_PATH;

		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File temp = new File(path);
		if (!temp.isDirectory()) {
			temp.mkdir();
		}
		// 图片存储的全路径
		String fileFullPath = path + "jkgj." + update.getVersionName()
				+ extName;
		try {
			FileCopyUtils.copy(ver.getBytes(), new File(fileFullPath));

			PropertieUtils.instance(update.getVersionCode(),
					update.getVersionName(), update.getDownloadUrl(),
					update.getUpdateLog());

			sklayApi.setMobileAndroidVer(update);

		} catch (IOException e) {
			error = "文件上传失败,请稍候再试";
			modelMap.addAttribute("error", error);
			LOGGER.error("version upload error {} ", e);
			return initSetting(modelMap);
		}

		success = "发布成功";

		modelMap.addAttribute("success", success);
		return initSetting(modelMap);
	}

	@RequestMapping("/setting")
	@RequiresPermissions("admin:setting")
	@ResponseBody
	public DataView setting(GlobalSetting setting) {

		if (null == setting || null == setting.getId())
			throw new SklayException(ErrorCode.SYS_CONFIG_EMPTY);
		checkSetting(setting);
		GlobalSetting orginal = globalService.findOne(setting.getId());

		if (null == orginal)
			throw new SklayException(ErrorCode.SYS_CONFIG_EMPTY);

		BeanUtils.copyProperties(setting, orginal, new String[] { "id",
				"webAuthor" });
		globalService.update(orginal);

		return new DataView(0, "操作完成!");
	}

	@RequestMapping("/logList")
	@RequiresPermissions("admin:logList")
	@Widget(name = ":logList", description = "日志列表")
	public String initLogList(Integer levelType,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {
		modelMap.addAttribute("nav", "admin:model");
		modelMap.addAttribute("subnav", "admin:logList");

		LogLevelType type = null == levelType ? null : LogLevelType
				.findByValue(levelType);
		Page<Operation> page = specificDao.getOperationPage(type, pageable);
		modelMap.addAttribute("pageModel", page);
		String pageQuery = levelType == null ? "" : "levelType=" + levelType
				+ "&";
		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("checkedType", levelType);
		return "manager.system.logList";
	}

	private static void checkSetting(GlobalSetting setting) {
		if (null == setting || null == setting.getId())
			throw new SklayException(ErrorCode.SYS_CONFIG_EMPTY);
		if (StringUtils.isEmpty(setting.getSendStartTime())
				|| StringUtils.isEmpty(setting.getSendEndTime()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "体检短信发送时间段");
		String string_start_hour = setting.getSendStartTime().split(":")[0];
		String string_end_hour = setting.getSendEndTime().split(":")[0];
		int start_hour = Integer.valueOf(string_start_hour);
		int end_hour = Integer.valueOf(string_end_hour);
		end_hour = end_hour == 0 ? 24 : end_hour;
		if (!(end_hour > start_hour))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					"体检短信发送结束时间要晚于开始时间");
	}

	private static String initQuery(String keyword, AuditStatus status,
			AppType appType) {

		String query = "";

		if (null != status)
			query += "status=" + status + "&";
		if (null != appType)
			query += "appType=" + appType + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

	private static String initQuery(String keyword, TipType tipType,
			ReadType readType) {

		String query = "";

		if (null != tipType)
			query += "tipType=" + tipType + "&";
		if (null != readType)
			query += "readType=" + readType + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}
}
