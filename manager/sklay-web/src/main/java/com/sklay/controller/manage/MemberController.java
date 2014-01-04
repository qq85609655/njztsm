package com.sklay.controller.manage;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.BeanUtils;
import com.sklay.core.util.Constants;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.core.util.PwdUtils;
import com.sklay.model.Application;
import com.sklay.model.ChartData;
import com.sklay.model.ChartEntity;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.HealthReport;
import com.sklay.model.MedicalReport;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
import com.sklay.service.BindingService;
import com.sklay.service.GlobalService;
import com.sklay.service.GroupService;
import com.sklay.service.MedicalReportService;
import com.sklay.service.UserAttrService;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-15
 */

@Controller
@RequestMapping("/admin/member")
@Widgets(value = "member", description = "会员管理")
public class MemberController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MemberController.class);

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserAttrService userAttrService;

	@Autowired
	private UserService userService;

	@Autowired
	private BindingService bindingService;

	@Autowired
	private GlobalService globalService;

	@Autowired
	private MedicalReportService reportService;

	@Autowired
	private ApplicationService appService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "member:model");
		model.addAttribute("subnav", "member:list");
		model.addAttribute("subHref", "/admin/member/list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("member:list")
	@Widget(name = ":list", description = "查看会员列表")
	public String list(Long groupId, String keyword,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		User session = LoginUserHelper.getLoginUser();
		String pageQuery = "";
		Long belong = null;
		List<Group> list = null;
		if (LoginUserHelper.isSuperAdmin()) {
			belong = null;
			session = null;
			list = groupService.getGroupAll();
		} else if (LoginUserHelper.isAdmin()) {
			belong = session.getId();
			list = groupService.getBelongGroup(belong);
		} else if (LoginUserHelper.isAgent()) {
			list = groupService.getGroupByOwner(session);
		}

		Page<User> page = userAttrService.getUserPage(groupId, keyword,
				session, belong, pageable);

		if (StringUtils.isNotBlank(keyword)) {
			pageQuery = "keyword=" + keyword + "&";
			if (null != groupId)
				pageQuery += "groupId=" + groupId + "&";
		} else {
			if (null != groupId)
				pageQuery = "groupId=" + groupId + "&";
		}
		modelMap.addAttribute("groups", list);
		modelMap.addAttribute("checkedGroup", groupId);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("keyword", keyword);

		return "manager.member.list";
	}

	@RequestMapping("/initCreate")
	@RequiresPermissions("member:create")
	@Widget(name = ":create", description = "会员创建功能")
	public String initCreate(Long userId, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "member:create");
		modelMap.addAttribute("thirdbreadcrumb", "创建会员");
		User session = LoginUserHelper.getLoginUser();

		modelMap = getUserGroup(session, modelMap);
		return "manager.member.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("member:create")
	@ResponseBody
	public DataView create(User user, Long groupId, String birth,
			ModelMap modelMap) {

		if (null == user)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户信息");

		if (StringUtils.isEmpty(birth))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户生日");
		Date birthDay = DateTimeUtil.getStringToDate(birth);
		if (DateTimeUtil.getCurrentDay().before(birthDay))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "用户生日");

		if (null == groupId)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户所在分组");

		Group targetGroup = groupService.get(groupId);

		if (null == targetGroup)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户所在分组");

		if (AuditStatus.PASS != targetGroup.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"会员分组", "添加会员" });

		user.setGroup(targetGroup);
		user.setBirthday(birthDay);
		targetGroup.setMemberCount(targetGroup.getMemberCount() + 1);

		boolean checkGroup = true;
		user = checkUser(user, checkGroup);
		if (userService.exist(user.getPhone()))
			throw new SklayException(ErrorCode.EXIST, null, "手机号码:"
					+ user.getPhone(), "再次新增");

		GlobalSetting setting = globalService.getGlobalConfig();

		User session = LoginUserHelper.getLoginUser();
		if (null == user.getStatus())
			user.setStatus(setting.getUserAudite());

		groupService.update(targetGroup);

		if (LoginUserHelper.isAdmin())
			user.setBelong(session.getId());
		else
			user.setBelong(session.getBelong());

		user = userService.create(user);

		createMemberGroup(user, setting, session);
		createApplication(user, session);

		DataView dataView = new DataView();
		dataView.setMsg("操作成功");

		return dataView;
	}

	@RequestMapping("/initUpdate/{userId}")
	@RequiresPermissions("member:update")
	@Widget(name = ":update", description = "编辑", level = WidgetLevel.THIRD)
	public String initUpdate(@PathVariable Long userId, ModelMap modelMap) {
		modelMap.addAttribute("thirdbreadcrumb", "编辑会员");

		User member = userService.getUser(userId);
		modelMap.addAttribute("member", member);

		if (AuditStatus.WAIT != member.getStatus()) {
			modelMap.addAttribute("disabled", "disabled='disabled'");
			modelMap.addAttribute("readonly", "readonly='readonly'");
		}
		User session = LoginUserHelper.getLoginUser();

		modelMap = getUserGroup(session, modelMap);

		return "manager.member.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("member:update")
	@ResponseBody
	public DataView update(User user, String birth, Long groupId,
			ModelMap modelMap) {

		boolean checkGroup = false;
		user = checkUser(user, checkGroup);

		if (StringUtils.isEmpty(birth))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户生日");
		Date birthDay = DateTimeUtil.getStringToDate(birth);
		if (DateTimeUtil.getCurrentDay().before(birthDay))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "用户生日");

		if (null == user.getId())
			throw new SklayException(ErrorCode.MISS_PARAM, null, ":会员信息");

		user.setBirthday(birthDay);
		User orginal = userService.getUser(user.getId());
		Set<Group> groups = null;
		if (null == orginal)
			throw new SklayException(ErrorCode.MISS_PARAM, null, ":会员信息");

		if (AuditStatus.WAIT == orginal.getStatus()) {
			if (null == groupId)
				throw new SklayException(ErrorCode.MISS_PARAM, null, "用户所在分组");
			Group targetGroup = groupService.get(groupId);
			if (null == targetGroup)
				throw new SklayException(ErrorCode.MISS_PARAM, null, "用户所在分组");

			if (AuditStatus.PASS == targetGroup.getStatus())
				throw new SklayException(ErrorCode.AUTIT_ERROR, null,
						new Object[] { "会员分组", "修改会员分组" });

			if (orginal.getGroup() != targetGroup) {

				Group orginalGroup = orginal.getGroup();
				orginalGroup.setMemberCount(orginalGroup.getMemberCount() - 1);
				targetGroup.setMemberCount(targetGroup.getMemberCount() + 1);
				orginal.setGroup(targetGroup);
				groups = Sets.newHashSet();
				groups.add(targetGroup);
				groups.add(orginalGroup);
			}

			if (!user.getPhone().equals(orginal.getPhone())
					&& userService.exist(user.getPhone()))
				throw new SklayException(ErrorCode.EXIST, null, "手机号码:"
						+ user.getPhone(), "再次新增");

			BeanUtils.copyProperties(user, orginal, new String[] { "id",
					"status", "group" });

			if (MemberRole.AGENT == targetGroup.getRole()
					|| MemberRole.ADMINSTROTAR == targetGroup.getRole())
				orginal.setPassword(PwdUtils
						.MD256Pws(orginal.getPhone().trim()));

		} else {
			BeanUtils.copyProperties(user, orginal, new String[] { "id",
					"status", "group", "phone" });
		}

		orginal.setStatus(AuditStatus.WAIT);
		userService.update(orginal);

		if (CollectionUtils.isNotEmpty(groups))
			groupService.update(groups);
		return new DataView(0, "绑定成功");
	}

	@RequestMapping("/initBinding/{userId}")
	@RequiresPermissions("member:binding")
	@Widget(name = ":binding", description = "绑定主号", level = WidgetLevel.THIRD)
	public String initBinding(@PathVariable Long userId, ModelMap modelMap) {

		User sesson = LoginUserHelper.getLoginUser();

		if (null == userId)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员" });

		User member = userService.getUser(userId);
		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员信息" });

		if (AuditStatus.PASS != member.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					member.getId(), "绑定设备" });

		List<DeviceBinding> bindings = null;

		Set<Long> owners = Sets.newHashSet(userId);

		/** 查看此手机号是否已绑定主设备 */
		if (MemberRole.ADMINSTROTAR == sesson.getGroup().getRole())
			bindings = bindingService.getUserBinding(owners, Level.FIRST, null);
		else
			bindings = bindingService.getUserBinding(owners, Level.FIRST,
					sesson);

		// DeviceBinding binding = CollectionUtils.isEmpty(bindings) ? null
		// : bindings.get(0);
		List<User> ownerList = userService.getUser(owners);
		modelMap.addAttribute("bindings", bindings);
		modelMap.addAttribute("owners", ownerList);
		modelMap.addAttribute("action", "binding");
		return "modal:member.mainBinding";
	}

	@RequestMapping("/binding")
	@RequiresPermissions("member:binding")
	@ResponseBody
	public DataView binding(Long userId, String[] devices) {

		User session = LoginUserHelper.getLoginUser();
		if (null == userId || null == devices
				|| Constants.ZERO == devices.length)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员或者设备号" });

		User member = userService.getUser(userId);
		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员信息" });

		if (AuditStatus.PASS != member.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					member.getName() + ":" + member.getId(), "绑定设备" });

		Set<String> deviceSet = Sets.newHashSet();
		for (String device : devices)
			deviceSet.add(device);

		List<DeviceBinding> bindings = null;
		Set<Long> owners = Sets.newHashSet(userId);
		/** 查看此手机号是否已绑定主设备 */
		if (MemberRole.ADMINSTROTAR == session.getGroup().getRole())
			bindings = bindingService.getUserBinding(owners, Level.FIRST, null);
		else
			bindings = bindingService.getUserBinding(owners, Level.FIRST,
					session);
		Set<DeviceBinding> removeSet = Sets.newHashSet();
		if (CollectionUtils.isNotEmpty(bindings)) {
			for (DeviceBinding device : bindings) {
				String sn = device.getSerialNumber();
				if (!deviceSet.contains(sn))
					removeSet.add(device);
				else
					deviceSet.remove(sn);
			}
		}

		List<DeviceBinding> deviceList = bindingService.getDefaultBindings(
				deviceSet, Level.FIRST);
		if (CollectionUtils.isNotEmpty(deviceList)) {
			for (DeviceBinding device : deviceList) {
				/** 已经存在该设备的主邦定者 */
				if (null != device && device.getTargetUser().getId() != userId)
					throw new SklayException(ErrorCode.EXIST, null,
							new Object[] {
									"设备号:" + device.getSerialNumber() + "已存在"
											+ Level.FIRST.getLable(),
									"继续绑定" + Level.FIRST.getLable() });
				/** 当前设备与当前用户邦定信息已存在 */
				else if (null != device
						&& device.getTargetUser().getId() == userId)
					deviceSet.remove(device.getId());

			}
		}

		Set<DeviceBinding> set = Sets.newHashSet();

		for (String device : deviceSet) {
			if (!MobileUtil.isMobile(device))
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
						new Object[] { "设备号码格式不正确" });

			device = device.trim();

			GlobalSetting setting = globalService.getGlobalConfig();
			Date operateTime = new Date();
			Long operator = session.getId();
			DeviceBinding binding = new DeviceBinding(device, member,
					setting.getDeviceBindingStatus(), Level.FIRST, operateTime,
					operateTime, session, operator, BindingMold.FREE);
			if (LoginUserHelper.isAdmin())
				binding.setBelong(session.getId());
			else
				binding.setBelong(session.getBelong());
			set.add(binding);
		}
		if (CollectionUtils.isNotEmpty(removeSet))
			bindingService.delete(removeSet);
		if (CollectionUtils.isNotEmpty(set))
			bindingService.createBinding(set);
		return new DataView(0, "绑定成功");
	}

	@RequestMapping("/initMultipleBinding")
	@RequiresPermissions("member:multipleBinding")
	@Widget(name = ":multipleBinding", description = "绑定附属号码", level = WidgetLevel.THIRD)
	public String initMultipleBinding(Long[] userIds, ModelMap modelMap) {

		if (null == userIds || userIds.length == 0)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员" });
		Set<Long> owners = Sets.newHashSet();
		for (Long id : userIds)
			owners.add(id);
		List<User> ownerList = userService.getUser(owners);
		Set<Long> errorId = Sets.newHashSet();
		for (User member : ownerList)
			if (AuditStatus.PASS != member.getStatus())
				errorId.add(member.getId());
		if (CollectionUtils.isNotEmpty(errorId))
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					errorId, "绑定设备" });

		modelMap.addAttribute("owners", ownerList);
		modelMap.addAttribute("action", "multipleBinding");
		return "modal:member.partBinding";
	}

	@RequestMapping("/multipleBinding")
	@RequiresPermissions("member:multipleBinding")
	@ResponseBody
	public DataView multipleBinding(Long[] userId, String sn, BindingMold mold) {

		if (null == mold)
			throw new SklayException(ErrorCode.MISS_PARAM, null,
					new Object[] { "设备类型" });

		if (null == userId || userId.length == 0)
			throw new SklayException(ErrorCode.MISS_PARAM, null,
					new Object[] { "需绑定的会员" });
		Set<Long> targetUser = Sets.newHashSet();
		for (Long id : userId)
			targetUser.add(id);

		User session = LoginUserHelper.getLoginUser();
		if (StringUtils.isBlank(sn))
			throw new SklayException(ErrorCode.MISS_PARAM, null,
					new Object[] { "需绑定的设备号" });

		if (!MobileUtil.isMobile(sn))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					new Object[] { "设备号码格式不正确" });

		List<User> members = userService.getUser(targetUser);
		if (CollectionUtils.isEmpty(members))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员信息" });

		if (targetUser.size() != members.size()) {
			for (User member : members)
				targetUser.remove(member.getId());
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "会员编号：" + targetUser + "的会员信息" });

		}

		sn = sn.trim();
		/** 查看此设备号是否已绑定主号 */
		checkDeviceBinding(sn, targetUser, Level.SECOND);

		GlobalSetting setting = globalService.getGlobalConfig();

		AuditStatus status = setting.getDeviceBindingStatus();
		if (mold == BindingMold.FREE) {
			long count = bindingService.getBindingUserCount(sn, null, mold,
					null);
			if (!(count < setting.getBindingCount())) {
				throw new SklayException("免费绑定数量已达到上线,只能绑定付费帐号");
			}
		} else {
			status = AuditStatus.WAIT;
		}

		Date operateTime = new Date();
		Long operator = session.getId();
		Set<DeviceBinding> bindings = Sets.newHashSet();
		for (User member : members) {
			DeviceBinding binding = new DeviceBinding(sn.trim(), member,
					status, Level.SECOND, operateTime, operateTime, session,
					operator, mold);
			if (LoginUserHelper.isAdmin())
				binding.setBelong(session.getId());
			else
				binding.setBelong(session.getBelong());
			bindings.add(binding);
		}

		bindingService.createBinding(bindings);

		return new DataView(0, "绑定成功");
	}

	@RequestMapping("/detail/{userId}")
	@RequiresPermissions("member:detail")
	@Widget(name = ":detail", description = "详情", level = WidgetLevel.THIRD)
	public String detail(@PathVariable Long userId, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");
		User member = userService.getUser(userId);

		Set<Long> targetUser = Sets.newHashSet(userId);
		List<DeviceBinding> deviceList = bindingService.getUserBinding(
				targetUser, null, null);

		modelMap.addAttribute("member", member);
		modelMap.addAttribute("devices", deviceList);
		return "modal:member.detail";
	}

	@RequestMapping("/delete/{userId}")
	@RequiresPermissions("member:delete")
	@Widget(name = ":delete", description = "删除", level = WidgetLevel.THIRD)
	@ResponseBody
	public DataView delete(@PathVariable Long userId) {

		if (null == userId)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需删除的会员" });
		User member = userService.getUser(userId);

		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需删除的会员" });

		if (AuditStatus.WAIT != member.getStatus())
			throw new SklayException(ErrorCode.EXIST, null, new Object[] {
					"会员的审核信息", "删除" });

		groupService.deleteByUser(member);
		userService.delete(member);
		Group group = member.getGroup();
		group.setMemberCount(group.getMemberCount() - 1);
		groupService.update(group);

		return new DataView(0, "操作成功!");
	}

	@RequestMapping("/initLineChart/{userId}")
	@RequiresUser
	public String initLineChart(@PathVariable Long userId, ModelMap modelMap) {
		if (null == userId)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员" });
		List<String> labels = Lists.newArrayList();
		List<Integer> systolic = Lists.newArrayList();
		List<Integer> diastolic = Lists.newArrayList();
		List<Integer> health = Lists.newArrayList();
		List<String> reports = Lists.newArrayList();

		User member = userService.getUser(userId);
		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需绑定的会员信息" });

		List<MedicalReport> lastReports = reportService.getLastReports(userId,
				-7);

		if (CollectionUtils.isEmpty(lastReports))
			return "modal:member.error";

		long firstDate = lastReports.get(Constants.ZERO).getReportTime();

		Map<String, HealthReport> result4health = Maps.newHashMap();

		for (MedicalReport mReoprt : lastReports) {
			String x_time = DateTimeUtil.getDate(mReoprt.getReportTime());
			String text = mReoprt.getOriginalData();
			ChartData gatherData = JSONObject
					.parseObject(text, ChartData.class);

			Integer systolic_val = Integer
					.valueOf(gatherData.getHighPressure());
			Integer diastolic_val = Integer
					.valueOf(gatherData.getLowPressure());
			Integer health_val = gatherData.getIndexHealth();
			String reports_val = mReoprt.getResult();

			HealthReport healthReport = new HealthReport(systolic_val,
					diastolic_val, health_val, reports_val);

			result4health.put(x_time, healthReport);
		}

		for (int i = 0; i < 7; i++) {
			Integer systolic_val = 0;
			Integer diastolic_val = 0;
			Integer health_val = 0;
			String reports_val = "";

			long cutDate = firstDate - 1000 * 3600 * 24 * i;
			String x_time = DateTimeUtil.getDate(cutDate);
			labels.add(x_time);

			if (result4health.containsKey(x_time)) {

				HealthReport healthReport = result4health.get(x_time);

				systolic_val = healthReport.getSystolic();
				diastolic_val = healthReport.getDiastolic();
				health_val = healthReport.getHealth();
				reports_val = healthReport.getReport();
			}

			systolic.add(systolic_val);
			diastolic.add(diastolic_val);
			health.add(health_val);
			reports.add(reports_val);
		}
		Collections.reverse(labels);
		Collections.reverse(systolic);
		Collections.reverse(diastolic);
		Collections.reverse(health);
		Collections.reverse(reports);
		modelMap.addAttribute("member", member);
		modelMap.addAttribute("labels", JSON.toJSONString(labels));
		modelMap.addAttribute("systolic", JSON.toJSONString(systolic));
		modelMap.addAttribute("diastolic", JSON.toJSONString(diastolic));
		modelMap.addAttribute("health", JSON.toJSONString(health));
		modelMap.addAttribute("reports", JSON.toJSONString(reports));
		modelMap.addAttribute("vertical", 6);

		return "modal:member.lineChart";
	}

	// TODO
	@RequestMapping("/lineChart/{userId}/{length}")
	@RequiresUser
	@ResponseBody
	public ChartEntity lineChart(@PathVariable Long userId,
			@PathVariable Integer length) {

		return null;
	}

	@RequestMapping("/remove/{userId}")
	@RequiresPermissions("member:remove")
	@Widget(name = ":remove", description = "彻底删除(不可分配给下级)", level = WidgetLevel.THIRD)
	@ResponseBody
	public DataView remove(@PathVariable Long userId) {

		if (null == userId)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需删除的会员" });
		User member = userService.getUser(userId);

		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "需删除的会员" });

		// if (AuditStatus.WAIT == member.getStatus()) {

		// userService.delete(member);
		// groupService.deleteByUser(member);
		// Group group = member.getGroup();
		// group.setMemberCount(group.getMemberCount() - 1);
		// groupService.update(group);
		// } else {

		Group superGroup = groupService.getSuperAdminGroup();
		User superUser = superGroup.getOwner();
		List<Group> gList = groupService.getGroupByOwner(member);
		Group parentGroup = member.getGroup();
		parentGroup.setMemberCount(parentGroup.getMemberCount() - 1);

		Set<Group> groups = Sets.newHashSet();
		groups.add(parentGroup);

		if (CollectionUtils.isNotEmpty(gList)) {
			for (Group g : gList) {
				g.setOwner(superGroup.getOwner());
				g.setDescription("来至于用户" + member.getName() + " Id为" + userId
						+ "的分组");
				groups.add(g);
			}
		}
		groupService.update(groups);

		reportService.deleteMedicalReport(userId);
		userAttrService.removeAttrByUser(userId);

		DeviceBinding mainBinding = bindingService.findTargetBinding(userId,
				Level.FIRST);
		if (null == mainBinding)
			bindingService.deleteTargetBinding(userId);
		else
			bindingService.deleteBinding(mainBinding.getSerialNumber());

		bindingService.updateBindingCreator(superUser, superUser.getId(),
				member);
		// smsService.removeSMS(member.getId());
		// appService.remove(member.getId());
		userService.delete(member);

		// }
		return new DataView(0, "操作成功!");
	}

	@RequestMapping("/initAudit/{userId}")
	@RequiresPermissions("member:audit")
	@Widget(name = ":audit", description = "审核", level = WidgetLevel.THIRD)
	public String initAudit(@PathVariable Long userId, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");
		User member = userService.getUser(userId);
		modelMap.addAttribute("member", member);

		return "modal:member.initAudit";
	}

	@RequestMapping("/audit")
	@RequiresPermissions("member:audit")
	@ResponseBody
	public DataView audit(Long userId, int auditStatus) {

		AuditStatus status = AuditStatus.findByValue(auditStatus);

		if (null == userId || null == status)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);

		User user = userService.getUser(userId);
		if (null == user)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户信息");
		if (status != user.getStatus()) {
			user.setStatus(status);
			userService.update(user);
		}

		return new DataView(0, "操作成功!");
	}

	@RequestMapping("/resetpwd/${userId}")
	@RequiresPermissions("member:resetPwd")
	@ResponseBody
	public DataView resetPwd(@PathVariable Long userId) {
		if (null == userId)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);

		User user = userService.getUser(userId);
		if (null == user)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户信息");
		user.setPassword(PwdUtils.MD256Pws(user.getPhone().trim()));
		userService.update(user);

		return new DataView(0, "操作成功!");
	}

	private static User checkUser(User user, boolean checkGroup) {

		if (null == user)
			throw new SklayException(ErrorCode.MISS_PARAM, null, ":会员信息");

		if (StringUtils.isBlank(user.getName()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, ":会员名称");

		if (StringUtils.isBlank(user.getPhone()))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号不能为空");

		if (!MobileUtil.isMobile(user.getPhone()))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号格式不正确");

		if (null == user.getSex())
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "性别");

		if (null == user.getAge())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "年龄");

		if (StringUtils.isEmpty(user.getArea()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "地区");

		if (0 >= user.getAge() || user.getAge() > 150)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "年龄不在正常范围内");

		if (!checkGroup)
			return user;
		if (StringUtils.isNotBlank(user.getPassword())) {
			if (user.getPassword().length() < 6)
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, "密码少于6位");

			user.setPassword(PwdUtils.MD256Pws(user.getPassword().trim()));
		} else if (null != user.getGroup().getRole()
				&& (MemberRole.AGENT == user.getGroup().getRole() || MemberRole.ADMINSTROTAR == user
						.getGroup().getRole()))
			user.setPassword(PwdUtils.MD256Pws(user.getPhone().trim()));
		return user;
	}

	private User createMemberGroup(User member, GlobalSetting setting,
			User session) {

		if (SwitchStatus.OPEN != setting.getAutoCreateGroup())
			return member;

		if (null == session || null == session.getGroup()
				|| null == session.getGroup().getRole())
			return member;

		MemberRole groupRole = session.getGroup().getRole();

		/** 超级管理员 */
		if (MemberRole.ADMINSTROTAR == groupRole
				|| MemberRole.AGENT == groupRole) {

			if (MemberRole.AGENT == member.getGroup().getRole())
				groupService.create(autoInitGroup(member, session,
						setting.getUserAudite()));
		}

		return member;
	}

	private User createApplication(User member, User session) {

		if (null == member || null == member.getGroup()
				|| null == member.getGroup().getRole())
			return member;

		MemberRole groupRole = member.getGroup().getRole();

		/** 超级管理员 */
		if (MemberRole.ADMINSTROTAR == groupRole
				&& null != member.getGroup().getParentGroupId()) {

			if (MemberRole.AGENT == member.getGroup().getRole())
				appService.cerate(autoInitApplication(member, session));
		}

		return member;
	}

	private static Set<Group> autoInitGroup(User member, User session,
			AuditStatus groupStatus) {

		Set<Group> groups = Sets.newHashSet();
		Long parentGroup = session.getGroup().getId();
		Group agentGroup = new Group(member.getId() + "下级代理商",
				MemberRole.AGENT, "下级代理商", member, groupStatus, parentGroup);
		agentGroup.setMemberCount(0L);
		Group memberGroup = new Group(member.getId() + "普通会员",
				MemberRole.MENBER, "普通会员", member, groupStatus, parentGroup);
		memberGroup.setMemberCount(0L);

		groups.add(memberGroup);
		groups.add(agentGroup);

		return groups;
	}

	private static List<Application> autoInitApplication(User member,
			User session) {
		Date date = new Date();
		List<Application> apps = Lists.newArrayList();
		Application app_physical = new Application(AppType.PHYSICAL,
				AuditStatus.WAIT, 0, "", member.getId(), date, session.getId(),
				date);
		Application app_sos = new Application(AppType.SOS, AuditStatus.WAIT, 0,
				"", member.getId(), date, session.getId(), date);

		apps.add(app_physical);
		apps.add(app_sos);
		return apps;
	}

	private void checkDeviceBinding(String sn, Set<Long> targetUser, Level level) {

		if (Level.FIRST == level) {
			/** 查看此设备号是否已绑定主号 */
			DeviceBinding binding = bindingService.getDefaultBinding(sn);
			if (null != binding)
				throw new SklayException(ErrorCode.EXIST, null, new Object[] {
						"设备号:" + sn + "已存在" + level.getLable(),
						"继续绑定" + level.getLable() });
		}
		List<DeviceBinding> list = bindingService.getUserBindingByLevel(
				targetUser, null, sn);

		if (CollectionUtils.isNotEmpty(list)) {
			for (DeviceBinding db : list) {
				targetUser.remove(db.getTargetUser());
			}
			throw new SklayException(ErrorCode.EXIST, null, new Object[] {
					"会员:" + targetUser.toString() + "已是绑定为" + level.getLable(),
					"再次绑定为" + level.getLable() });
		}
	}

	private ModelMap getUserGroup(User user, ModelMap modelMap) {

		List<Group> list = groupService.getGroupByOwner(user);
		modelMap.addAttribute("groups", list);
		modelMap.addAttribute("session", user);
		return modelMap;
	}
}
