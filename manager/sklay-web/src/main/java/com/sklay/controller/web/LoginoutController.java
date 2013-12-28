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

import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.message.NLS;
import com.sklay.core.util.Constants;
import com.sklay.core.util.PwdUtils;
import com.sklay.model.Application;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
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

	@Autowired
	private ApplicationService appService ;
	
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
		User reciver = users.get(Constants.ZERO);
		if (AuditStatus.PASS != reciver.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"手机号码[" + phone + "]用户信息", "重置密码" });
		Long belong = reciver.getBelong() ;
		Application app = appService.getByCreator(AppType.PWD, belong) ;
		if(null == app || AuditStatus.PASS != app.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"密码业务", "重置密码" });
		
		String pwd = PwdUtils.genRandomNum(6);
		reciver.setPassword(PwdUtils.MD256Pws(pwd.trim()));
		userService.update(reciver);
		String content = NLS.getMsg(sklayApi.getPwdPairs(), new Object[] {
				reciver.getName(), pwd.trim() });

		Set<SMS> smsLogs = Sets.newHashSet();

		Date date = new Date();
		SMS log = new SMS(reciver.getId(), content, date, reciver.getId(),
				reciver.getPhone(), SMSStatus.SUCCESS);
		log.setBelong(belong) ;
		log.setApp(app) ;
		smsLogs.add(log);

		smsLogs = sklayApi.resetPwd(smsLogs);

		if (CollectionUtils.isNotEmpty(smsLogs))
			smsService.create(smsLogs);

		modelMap.addAttribute("model", reciver);

		return "alone:core.reset";
	}

	@RequestMapping("/logout")
	public String logout() {
		LoginUserHelper.logout();
		return "redirect:/";
	}

}
