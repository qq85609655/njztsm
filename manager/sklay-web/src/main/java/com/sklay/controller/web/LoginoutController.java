package com.sklay.controller.web;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.Constants;
import com.sklay.core.util.PwdUtils;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.service.SMSService;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

@Controller
public class LoginoutController {

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private UserService userService;

	@Autowired
	private SMSService smsService;

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String regist() {
		return "alone:core.regist";
	}

	@RequestMapping("/doRegist")
	@ResponseBody
	public int doRegist(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return 3;
		}
		if (userService.exist(user.getName())) {
			return 2;
		} else {
			userService.regist(user);
			return 0;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, boolean rememberMe) {
		try {
			LoginUserHelper.login(username, password, rememberMe);
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.GET)
	@ResponseBody
	public DataView doLogin(String username, String password, boolean rememberMe) {

		DataView dataView = new DataView();
		try {
			LoginUserHelper.login(username, password, rememberMe);
			dataView.setCode(1);
			dataView.setData("http://www.njztsm.net/admin/");
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			dataView.setCode(-1);
			dataView.setMsg("登入失败请确认帐号信息与密码是否正确!");
		}
		return dataView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "alone:core.login";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String reset() {
		return "alone:core.reset";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String reset(String phone, ModelMap modelMap) {

		if (StringUtils.isBlank(phone) || !MobileUtil.isMobile(phone))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号码");

		Set<String> phones = Sets.newHashSet(phone.trim());
		List<User> users = userService.getUserByPhone(phones);
		if (CollectionUtils.isEmpty(users))
			throw new SklayException(ErrorCode.FINF_NULL, null, "手机号码[" + phone
					+ "]用户信息");
		User user = users.get(Constants.ZERO);
		if (AuditStatus.PASS != user.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"手机号码[" + phone + "]用户信息", "重置密码" });
		String pwd = PwdUtils.genRandomNum(6);
		user.setPassword(PwdUtils.MD256Pws(pwd.trim()));
		userService.update(user);
		String content = "亲 ，" + user.getName() + "，您本次重置的新密码为:" + pwd.trim()
				+ "请及时登录修改!确保您的账户安全。【安全中心】";
		sendSMS(Lists.newArrayList(user), content);
		modelMap.addAttribute("model", user);

		return "alone:core.reset";
	}

	@RequestMapping("/logout")
	public String logout() {
		LoginUserHelper.logout();
		return "redirect:/";
	}

	private void sendSMS(List<User> reciverList, String content) {
		Set<SMS> smsLogs = Sets.newHashSet();

		User session = reciverList.get(Constants.ZERO);
		Date date = new Date();
		for (User reciver : reciverList) {
			SMS log = new SMS(session.getId(), content, date, reciver.getPhone(), SMSStatus.FAIL,
					date.getTime());
			smsLogs.add(log);
		}

		smsLogs = sklayApi.resetPwd(smsLogs) ;

		if (CollectionUtils.isNotEmpty(smsLogs))
			smsService.create(smsLogs);
	}
}
