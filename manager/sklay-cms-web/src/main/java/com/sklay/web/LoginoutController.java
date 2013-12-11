package com.sklay.web;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sklay.util.LoginUserHelper;


@Controller
public class LoginoutController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public int login(String username,String password,boolean rememberMe){
		try{
			LoginUserHelper.login(username, password, rememberMe);
			return 0;
		} catch (UnknownAccountException uae) {
			return 1;
		} catch (IncorrectCredentialsException ice){
			return 1;
		}catch (AuthenticationException ace) {
			return 1;
		}
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "alone:core.login";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		LoginUserHelper.logout();
		return "redirect:/";
	}
}
