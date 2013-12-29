package com.sklay.controller.manage;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.alibaba.fastjson.JSONObject;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.MoldType;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.BeanUtils;
import com.sklay.core.util.Constants;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.enums.LogLevelType;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.Mold;
import com.sklay.model.Operation;
import com.sklay.model.User;
import com.sklay.model.UserAttr;
import com.sklay.service.BindingService;
import com.sklay.service.GlobalService;
import com.sklay.service.GroupService;
import com.sklay.service.OperationService;
import com.sklay.service.UserAttrService;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/device")
@Widgets(value = "device", description = "设备管理")
public class DeviceController {

	@Autowired
	private BindingService bindingService;

	@Autowired
	private GlobalService globalService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserAttrService userAttrService;

	@Autowired
	private OperationService operationService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "device:list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("device:list")
	@Widget(name = ":list", description = "查看设备列表")
	public String list(Level level, Long groupId, BindingMold bindingMold,
			String keyword, AuditStatus status, AuditStatus moldStatus,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		if (StringUtils.isNotBlank(keyword))
			keyword = keyword.trim();

		User session = LoginUserHelper.getLoginUser();
		Long belong = null;
		List<Group> groups = null;
		if (LoginUserHelper.isSuperAdmin()) {
			session = null;
			groups = groupService.getGroupAll();
		} else if (LoginUserHelper.isAdmin()) {
			belong = session.getId();
			groups = groupService.getGroupByOwner(session);
		} else
			groups = groupService.getBelongGroup(belong);

		Page<DeviceBinding> page = bindingService.getDeviceBindingPage(groupId,
				keyword, level, bindingMold, session, status, moldStatus,
				belong, pageable);

		modelMap.addAttribute("checkedOption", level);
		modelMap.addAttribute("checkedType", bindingMold);
		modelMap.addAttribute("checkedStatus", status);
		modelMap.addAttribute("checkedMoldStatus", moldStatus);
		modelMap.addAttribute("groups", groups);
		modelMap.addAttribute("checkedId", groupId);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute(
				"pageQuery",
				initQuery(level, groupId, bindingMold, keyword, status,
						moldStatus));
		modelMap.addAttribute("keyword", keyword);

		return "manager.device.list";
	}

	@RequestMapping("/initCreate")
	@RequiresPermissions("device:create")
	@Widget(name = ":create", description = "添加设备", level = WidgetLevel.THIRD, isShow = false)
	public String initCreate(ModelMap modelMap) {
		modelMap.addAttribute("nav", "device");
		modelMap.addAttribute("subnav", "device:create");

		return "manager.device.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("device:create")
	public int create(User user, ModelMap modelMap) {

		return 0;
	}

	@RequestMapping("/initUpdate/{id}")
	@RequiresPermissions("device:update")
	@Widget(name = ":update", description = "修改设备", level = WidgetLevel.THIRD)
	public String initUpdate(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("nav", "device");
		modelMap.addAttribute("subnav", "device:update");

		DeviceBinding binding = bindingService.get(id);

		modelMap.addAttribute("model", binding);

		return "modal:device.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("device:update")
	@ResponseBody
	public DataView update(DeviceBinding device, Long targetUserId) {

		User targetUser = null;
		if (targetUserId != null) {
			targetUser = userService.getUser(targetUserId);
			if (targetUser == null)
				throw new SklayException(ErrorCode.FINF_NULL, null, "设备绑定者");
		}

		if (device == null)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备信息");

		if (device.getMold() == null)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备类型");

		if (null == device.getId())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "设备Id");

		if (!MobileUtil.isMobile(device.getSerialNumber()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "设备号码");

		DeviceBinding orginal = bindingService.get(device.getId());

		if (null == orginal)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备信息");

		if (targetUser == null) {
			targetUser = orginal.getTargetUser();
		}
		device.setTargetUser(targetUser);
		long bindingCount = 0L;
		if (BindingMold.FREE != orginal.getMold()
				&& BindingMold.FREE == device.getMold()) {

			GlobalSetting setting = globalService.getGlobalConfig();

			bindingCount = bindingService.getBindingUserCount(
					orginal.getSerialNumber(), null, BindingMold.FREE, null);
			if (!(bindingCount < setting.getBindingCount())) {
				throw new SklayException("免费绑定数量已达到上线,只能绑定付费帐号");
			}
			orginal.setMold(BindingMold.FREE);
		}
		BeanUtils.copyProperties(device, orginal, new String[] { "id",
				"creator", "createTime", "status", "level", "belong" });
		orginal.setStatus(AuditStatus.WAIT);
		bindingService.update(orginal);

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/detail")
	@RequiresPermissions("device:detail")
	@Widget(name = ":detail", description = "设备详情", level = WidgetLevel.THIRD, isShow = false)
	public String detail(Long userId, ModelMap modelMap) {
		modelMap.addAttribute("nav", "member");
		modelMap.addAttribute("subnav", "dd");
		return "modal:device.detail";
	}

	@RequestMapping("/delete/{id}")
	@RequiresPermissions("device:delete")
	@ResponseBody
	@Widget(name = ":delete", description = "删除设备", level = WidgetLevel.THIRD)
	public DataView delete(@PathVariable Long id) {

		if (null == id)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "设备Id");

		DeviceBinding binding = bindingService.get(id);
		if (null == binding)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "设备Id");

		bindingService.deleteByLevel(binding);

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initAudit/{id}")
	@RequiresPermissions("device:audit")
	@Widget(name = ":audit", description = "设备审核", level = WidgetLevel.THIRD)
	public String initAudit(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");
		DeviceBinding deviceBinding = bindingService.get(id);
		modelMap.addAttribute("model", deviceBinding);

		return "modal:device.initAudit";
	}

	@RequestMapping("/audit")
	@RequiresPermissions("device:audit")
	@ResponseBody
	public DataView audit(Long id, AuditStatus auditStatus) {

		// AuditStatus status = AuditStatus.findByValue(auditStatus);

		if (null == id || null == auditStatus)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);

		DeviceBinding deviceBinding = bindingService.get(id);
		if (null == deviceBinding)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备信息");

		if (auditStatus != deviceBinding.getStatus()) {
			deviceBinding.setStatus(auditStatus);
			bindingService.update(deviceBinding);
		}

		return new DataView(0, "操作成功!");
	}

	@RequestMapping("/initMoldAudit/{id}")
	@RequiresPermissions("device:moldAudit")
	@Widget(name = ":moldAudit", description = "付费会员设备审核", level = WidgetLevel.THIRD)
	public String initMoldAudit(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");

		if (null == id)
			throw new SklayException(ErrorCode.FINF_NULL, null, "必要参数");

		DeviceBinding deviceBinding = bindingService.get(id);

		if (null == deviceBinding)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备信息");

		if (AuditStatus.PASS != deviceBinding.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					deviceBinding.getId(), "完成付费审核" });

		String key = Constants.DEVICE_MOLD_TIME;
		Long userId = deviceBinding.getTargetUser().getId();
		List<UserAttr> attrs = userAttrService.findUserAttrByKey(key, userId);
		Mold mold = null;
		if (CollectionUtils.isNotEmpty(attrs)) {
			UserAttr attr = attrs.get(Constants.ZERO);
			mold = JSONObject.parseObject(attr.getValue(), Mold.class);
		}
		modelMap.addAttribute("model", deviceBinding);
		modelMap.addAttribute("mold", mold);
		return "modal:device.initMoldAudit";
	}

	@RequestMapping("/moldAudit")
	@RequiresPermissions("device:moldAudit")
	@ResponseBody
	public DataView moldAudit(Long id, AuditStatus moldStatus,
			String startTime, String endTime) {

		if (null == id || null == moldStatus)
			throw new SklayException(ErrorCode.FINF_NULL, null, "必要参数");

		DeviceBinding deviceBinding = bindingService.get(id);

		Operation operation = null;
		User session = LoginUserHelper.getLoginUser();
		Date current = new Date();

		if (null == deviceBinding)
			throw new SklayException(ErrorCode.FINF_NULL, null, "设备信息");

		if (AuditStatus.PASS != deviceBinding.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					deviceBinding.getId(), "完成付费审核" });

		if (AuditStatus.PASS == moldStatus) {
			if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))
				throw new SklayException(ErrorCode.FINF_NULL, null, "必要参数");

			Date startDate = DateTimeUtil.getStringToDate(startTime);
			Date endDate = DateTimeUtil.getStringToDate(endTime + " 23:59:59");

			if (!(startDate.before(current)) || !(startDate.before(endDate)))
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
						"起始时间或截止时间");

			String key = Constants.DEVICE_MOLD_TIME;
			Long targetUserId = deviceBinding.getTargetUser().getId();
			Mold mold = new Mold(startDate, endDate, MoldType.DEVICE);
			String value = JSONObject.toJSONString(mold);
			UserAttr attr = null;
			List<UserAttr> attrs = userAttrService.findUserAttrByKey(key,
					targetUserId);

			if (CollectionUtils.isNotEmpty(attrs)) {
				attr = attrs.get(Constants.ZERO);
				String original = attr.getValue();
				attr.setRemark(original);
				attr.setValue(value);
				attr.setInsertTime(current);

				String content = "用户【" + session.getName() + "-"
						+ session.getId() + "】将 id为【" + attr.getId()
						+ "】的用户属性由【" + original + "】修改为【" + value + "】付费状态由【"
						+ deviceBinding.getMoldStatus().getLable() + "】修改为【"
						+ moldStatus.getLable() + "】";

				operation = initOperation(current, content);

				userAttrService.updateUserAttr(attr);
			} else {
				attr = new UserAttr(key, value, targetUserId, current);

				userAttrService.createUserAttr(attr);

				String content = "用户【" + session.getName() + "-"
						+ session.getId() + "】将 创建用户属性【" + attr.getId()
						+ "】对象为【" + JSONObject.toJSONString(attr) + "】付费状态由【"
						+ deviceBinding.getMoldStatus().getLable() + "】修改为【"
						+ moldStatus.getLable() + "】";

				operation = initOperation(current, content);

			}

			bindingService.update(deviceBinding);
			deviceBinding.setMoldStatus(moldStatus);

		} else if (AuditStatus.NOT == moldStatus) {

			String content = "用户【" + session.getName() + "-" + session.getId()
					+ "】将 id为【" + deviceBinding.getId() + "】的用户绑定信息付费状态由【"
					+ deviceBinding.getMoldStatus().getLable() + "】修改为【"
					+ moldStatus.getLable() + "】";
			operation = initOperation(current, content);

			deviceBinding.setMoldStatus(moldStatus);
			bindingService.update(deviceBinding);

		}

		if (null != operation)
			operationService.create(operation);

		return new DataView(0, "操作成功!");
	}

	private static Operation initOperation(Date current, String content) {
		Operation operation = new Operation();
		operation.setName(LogLevelType.DEVICE_MOLD.getLable());
		operation.setCreateTime(current);
		operation.setType(LogLevelType.DEVICE_MOLD);
		operation.setContent(content);

		return operation;
	}

	private static String initQuery(Level levelValue, Long groupId,
			BindingMold bindingMold, String keyword, AuditStatus status,
			AuditStatus moldStatus) {

		String query = "";

		if (null != levelValue)
			query += "levelValue=" + levelValue + "&";
		if (null != groupId)
			query += "groupId=" + groupId + "&";
		if (null != levelValue)
			query += "bindingMold=" + bindingMold + "&";
		if (null != levelValue)
			query += "status=" + status + "&";
		if (null != moldStatus)
			query += "moldStatus=" + moldStatus + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";

		return query;
	}
}
