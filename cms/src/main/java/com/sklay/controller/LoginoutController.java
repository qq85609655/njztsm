package com.sklay.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sklay.utils.LoginUserHelper;


@Controller
public class LoginoutController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,boolean rememberMe){
		try{
			LoginUserHelper.login(username, password, rememberMe);
		}  catch (AuthenticationException ae){
			ae.printStackTrace() ;
		} 
		return "redirect:/";
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
