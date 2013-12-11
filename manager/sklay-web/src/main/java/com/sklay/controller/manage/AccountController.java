package com.sklay.controller.manage;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.support.AuthBase;
import com.sklay.core.support.WidgetManager;
import com.sklay.core.util.BeanUtils;
import com.sklay.core.util.PwdUtils;
import com.sklay.model.Group;
import com.sklay.model.User;
import com.sklay.service.UserService;
import com.sklay.util.Convert;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/account")
public class AccountController {

	@Autowired
	private WidgetManager widgetManager;

	@Autowired
	private UserService userService;

	@RequestMapping("/initPwd/{id}")
	@RequiresUser
	public String initPassword(@PathVariable Long id, ModelMap modelMap) {

		if (null == id || id <= 0)
			throw new SklayException(ErrorCode.FINF_NULL, null, "用户Id");

		modelMap.addAttribute("userId", id);
		return "modal:account.password";
	}

	@RequestMapping("/password")
	@RequiresUser
	@ResponseBody
	public DataView password(Long userId, String oldPwd, String[] newPwd) {

		if (StringUtils.isEmpty(oldPwd))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "原密码");

		if (null == newPwd || newPwd.length != 2)
			throw new SklayException(ErrorCode.FINF_NULL, null, "新密码");

		if (!newPwd[0].equals(newPwd[1]))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					"新密码两次输入不一致");
		if (oldPwd.equals(newPwd[1]))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "新旧密码相同");
		if (newPwd[1].trim().length() < 6)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					"新旧密码不能少于6位");

		User member = LoginUserHelper.getLoginUser();// userService.getUser(userId);
		if (null == member || null == member.getId())
			throw new SklayException(ErrorCode.FINF_NULL, null, "个人资料信息");
		member.setPassword(PwdUtils.MD256Pws(newPwd[0].trim()));

		userService.update(member);

		return new DataView(0, "修改陈功");
	}

	@RequestMapping("/initProfile/{userId}")
	@RequiresUser
	public String initProfile(@PathVariable Long userId, ModelMap modelMap) {

		User member = userService.getUser(userId);
		modelMap.addAttribute("member", member);

		return "modal:account.profile";
	}

	@RequestMapping("/profile")
	@RequiresUser
	@ResponseBody
	public DataView profile(User user) {

		if (null == user || null == user.getId())
			throw new SklayException(ErrorCode.FINF_NULL, null, "个人资料信息");
		if (StringUtils.isBlank(user.getPhone()))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					new Object[] { "手机号不能为空" });
		if (!MobileUtil.isMobile(user.getPhone()))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null,
					new Object[] { "手机号格式不正确" });

		User member = LoginUserHelper.getLoginUser();
		if (null == member)
			throw new SklayException(ErrorCode.FINF_NULL, null, "个人资料信息");

		if (!user.getPhone().equals(member.getPhone()))
			if (userService.exist(user.getPhone()))
				throw new SklayException(ErrorCode.EXIST, null, new Object[] {
						"手机号码:" + user.getPhone(), "再次新增" });

		BeanUtils.copyProperties(user, member, new String[] { "id", "password",
				"salt", "status", "role", "owner", "group" });
		userService.update(member);
		return new DataView(0, "修改陈功");
	}

	@RequestMapping("/group")
	@RequiresUser
	public String group(ModelMap modelMap) {
		long userId = LoginUserHelper.getLoginUserId();

		User user = userService.getUser(userId);
		Group group = user.getGroup();
		Map<String, AuthBase> result = Convert.spiltUserAuth(user,
				widgetManager.authorModels());

		modelMap.addAttribute("group", group);
		modelMap.addAttribute("widgets", result);
		return "modal:account.group";
	}
}
