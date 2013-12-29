package com.sklay.controller.manage;

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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.support.AuthBase;
import com.sklay.core.support.WidgetManager;
import com.sklay.model.AuthorEntity;
import com.sklay.model.Group;
import com.sklay.model.User;
import com.sklay.service.GroupService;
import com.sklay.service.UserService;
import com.sklay.util.Convert;
import com.sklay.util.LoginUserHelper;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/group")
@Widgets(value = "group", description = "会员组管理")
public class GroupController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GroupController.class);

	@Autowired
	private WidgetManager widgetManager;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "group:list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("group:list")
	@Widget(name = ":list", description = "会员组列表")
	public String list(String keyword,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		User session = LoginUserHelper.getLoginUser();
		Long belong = null;
		if (LoginUserHelper.isSuperAdmin()) {
			session = null;
		} else if (LoginUserHelper.isAdmin()) {
			belong = session.getId();
		}

		Page<Group> groupPage = groupService.getGroupPage(keyword, session,
				belong, pageable);
		modelMap.addAttribute("pageModel", groupPage);
		String pageQuery = "";

		if (StringUtils.isNotBlank(keyword))
			pageQuery = "keyword=" + keyword;

		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("keyword", keyword);

		return "manager.group.list";
	}

	@RequestMapping("/initCreate")
	@RequiresPermissions("group:create")
	@Widget(name = ":create", description = "创建会员组", level = WidgetLevel.THIRD)
	public String initCreate() {

		return "modal:group.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("group:create")
	@ResponseBody
	public DataView create(Group group, ModelMap modelMap) {
		if (group == null)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户分组信息");

		if (StringUtils.isEmpty(group.getName()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户分组名称");
		if (null == group.getRole())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户分组类型");
		User session = LoginUserHelper.getLoginUser();

		Group orginal = new Group();
		Group parent = session.getGroup();

		orginal.setName(group.getName().trim());
		orginal.setDescription(group.getDescription());
		orginal.setMemberCount(0L);
		orginal.setOwner(session);
		orginal.setParentGroupId(parent.getId());

		if (LoginUserHelper.isAdmin())
			orginal.setBelong(session.getId());
		else
			orginal.setBelong(session.getBelong());
		orginal.setRole(group.getRole());
		if (null == group.getStatus())
			orginal.setStatus(AuditStatus.WAIT);
		else
			orginal.setStatus(group.getStatus());
		groupService.update(orginal);

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initUpdate/{groupId}")
	@RequiresPermissions("group:update")
	@Widget(name = ":update", description = "编辑组员组", level = WidgetLevel.THIRD)
	public String initUpdate(@PathVariable Long groupId, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "group:update");
		modelMap.addAttribute("thirdbreadcrumb", "编辑会员组");
		Group group = groupService.get(groupId);

		if (AuditStatus.PASS == group.getStatus()) {
			modelMap.addAttribute("readonly", "disabled='disabled'");
		}

		modelMap.addAttribute("model", group);
		return "modal:group.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("group:update")
	@ResponseBody
	public DataView update(Group group) {

		if (group == null)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户分组信息");
		if (null == group.getId())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户分组Id");
		if (StringUtils.isEmpty(group.getName()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "用户分组名称");

		Group orginal = groupService.get(group.getId());

		if (null == orginal)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户分组信息");

		orginal.setName(group.getName().trim());
		orginal.setDescription(group.getDescription());

		if (AuditStatus.PASS != orginal.getStatus()) {
			orginal.setRole(group.getRole());
			orginal.setStatus(group.getStatus());
		}
		groupService.update(orginal);

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initAuthor/{groupId}")
	@RequiresPermissions("group:author")
	@Widget(name = ":author", description = "会员组授权", level = WidgetLevel.THIRD)
	public String initAuthor(@PathVariable Long groupId, ModelMap modelMap) {
		modelMap.addAttribute("thirdbreadcrumb", "会员组授权");

		if (null == groupId)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户组id参数");
		Group group = groupService.get(groupId);

		if (null == group)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户组");

		boolean superAdmin = LoginUserHelper.isSuperAdmin();

		User session = LoginUserHelper.getLoginUser();

		String perm = group.getPerms();

		List<String> perms = (StringUtils.isBlank(perm) ? null : JSONObject
				.parseArray(group.getPerms(), String.class));

		Map<String, AuthorEntity> result = Maps.newHashMap();
		Map<String, AuthBase> authors = widgetManager.authorModels();
		authors = superAdmin ? authors : Convert
				.spiltUserAuth(session, authors);

		if (authors != null && CollectionUtils.isNotEmpty(perms)) {
			Set<String> keySet = authors.keySet();
			for (String key : keySet) {

				if (!authors.get(key).isShow())
					continue;

				AuthBase widgetInfo = authors.get(key);
				AuthorEntity authorEntity = new AuthorEntity("", widgetInfo);
				if (!perms.contains(key)) {
					authorEntity = splitAthor(authorEntity, widgetInfo);
					result.put(key, authorEntity);
					continue;
				}

				authorEntity.setChecked(" checked='true' ");
				List<AuthorEntity> childList = Lists.newArrayList();
				if (CollectionUtils.isNotEmpty(widgetInfo.getChildBase()))
					for (AuthBase child : widgetInfo.getChildBase()) {
						AuthorEntity childAuthorEntity = new AuthorEntity("",
								child);
						String auth_perm = childAuthorEntity.getAuthBase()
								.getName();
						if (!child.isShow()) {
							continue;
						}
						if (perms.contains(auth_perm))
							childAuthorEntity.setChecked(" checked='true' ");
						childList.add(childAuthorEntity);
					}
				if (CollectionUtils.isNotEmpty(childList)) {
					authorEntity.setChild(childList);
					result.put(key, authorEntity);
				}
			}
		} else {
			Set<String> keySet = authors.keySet();
			for (String key : keySet) {
				AuthBase widgetInfo = authors.get(key);

				AuthorEntity authorEntity = new AuthorEntity("", widgetInfo);
				if (!widgetInfo.isShow())
					continue;
				authorEntity = splitAthor(authorEntity, widgetInfo);
				result.put(key, authorEntity);
			}
		}

		modelMap.addAttribute("group", group);
		modelMap.addAttribute("widgets", result);
		modelMap.addAttribute("href", "/admin/group/list");
		return "manager.group.initAuthor";
	}

	@RequestMapping("/author")
	@RequiresPermissions("group:author")
	@ResponseBody
	public DataView author(Long[] groupId, String[] authors) {

		if (null == groupId || 0 == groupId.length || null == authors
				|| 0 == authors.length)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "分组Id或授权");

		String perms = JSONObject.toJSONString(authors);
		LOGGER.debug("author is {}", authors.toString());
		LOGGER.debug("author string is {}", perms);
		Set<Long> ids = Sets.newHashSet();
		for (Long id : groupId)
			ids.add(id);

		List<Group> groups = groupService.getGroupAll(ids);

		if (CollectionUtils.isEmpty(groups))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "分组信息");

		for (Group group : groups)
			group.setPerms(perms);

		groupService.create(Sets.newHashSet(groups));
		return new DataView(0, "操作成功");
	}

	@RequestMapping("/detail")
	@RequiresPermissions("group:detail")
	@Widget(name = ":detail", description = "分组详情", level = WidgetLevel.THIRD)
	public String detail(Long userId, ModelMap modelMap) {

		return "modal:device.detail";
	}

	@RequestMapping("/delete/{groupId}")
	@RequiresPermissions("group:delete")
	@Widget(name = ":delete", description = "删除分组信息", level = WidgetLevel.THIRD)
	@ResponseBody
	public DataView delete(@PathVariable Long groupId) {

		groupService.delete(groupId);

		return new DataView(0, "操作成功");
	}

	@RequestMapping("/initAudit/{groupId}")
	@RequiresPermissions("group:audit")
	@Widget(name = ":audit", description = "审核", level = WidgetLevel.THIRD)
	public String initAudit(@PathVariable Long groupId, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");
		Group group = groupService.get(groupId);
		modelMap.addAttribute("model", group);

		return "modal:group.initAudit";
	}

	@RequestMapping("/audit")
	@RequiresPermissions("group:audit")
	@ResponseBody
	public DataView audit(Long groupId, int auditStatus) {

		AuditStatus status = AuditStatus.findByValue(auditStatus);

		if (null == groupId || null == status)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					"会员分组Id或审核状态");

		Group group = groupService.get(groupId);
		if (null == group)
			throw new SklayException(ErrorCode.FINF_NULL, null, "会员分组信息");
		if (status != group.getStatus()) {
			group.setStatus(status);
			groupService.update(group);
		}

		return new DataView(0, "操作成功!");
	}

	@RequestMapping("/initGive/{groupId}")
	@RequiresPermissions("group:give")
	@Widget(name = ":give", description = "转让会员分组", level = WidgetLevel.THIRD)
	public String initGive(@PathVariable Long groupId, ModelMap modelMap) {
		modelMap.addAttribute("readonly", "readonly='readonly'");
		Group group = groupService.get(groupId);
		modelMap.addAttribute("model", group);

		return "modal:group.initGive";
	}

	@RequestMapping("/give")
	@RequiresPermissions("group:give")
	@ResponseBody
	public DataView give(Long groupId, Long userId) {

		if (null == groupId || null == userId)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					"会员分组信息或会员信息");

		Group group = groupService.get(groupId);
		if (null == group)
			throw new SklayException(ErrorCode.FINF_NULL, null, "会员分组信息");

		User owner = userService.getUser(userId);
		if (null == owner)
			throw new SklayException(ErrorCode.FINF_NULL, null, "会员信息");

		group.setOwner(owner);

		groupService.update(group);

		return new DataView(0, "操作成功!");
	}

	private AuthorEntity splitAthor(AuthorEntity authorEntity,
			AuthBase widgetInfo) {
		if (CollectionUtils.isNotEmpty(widgetInfo.getChildBase()))
			for (AuthBase child : widgetInfo.getChildBase()) {
				if (!child.isShow())
					continue;
				authorEntity.getChild().add(new AuthorEntity("", child));
			}
		return authorEntity;
	}

}
