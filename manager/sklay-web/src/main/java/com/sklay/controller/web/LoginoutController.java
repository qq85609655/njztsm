package com.sklay.controller.web;

import javax.validation.Valid;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sklay.model.User;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.vo.DataView;

@Controller
public class LoginoutController {

	@Autowired
	private UserService userService;

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
		
		DataView dataView = new DataView() ;
		try {
			LoginUserHelper.login(username, password, rememberMe);
			dataView.setCode(1) ;
			dataView.setData("http://www.njztsm.net/admin/") ;
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			dataView.setCode(-1) ;
			dataView.setMsg("登入失败请确认帐号信息与密码是否正确!") ;
		}
		return dataView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "alone:core.login";
	}

	@RequestMapping("/logout")
	public String logout() {
		LoginUserHelper.logout();
		return "redirect:/";
	}
}
