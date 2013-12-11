package com.sklay.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sklay.model.News;
import com.sklay.model.User;
import com.sklay.service.NewsService;
import com.sklay.util.LoginUserHelper;

@Controller
public class IndexController {

	@Autowired
	private NewsService newsService;

	@RequestMapping("/login")
	public String login(User user, boolean rememberMe, ModelMap model) {
		try {
			LoginUserHelper.login(user.getEmail(), user.getPassword(),
					rememberMe);
			return "redirect:/supervise/";
		} catch (UnknownAccountException uae) {
			model.addAttribute("error", "帐号不存在!");
			model.addAttribute("email", "");
			return "forward:/";
		} catch (IncorrectCredentialsException ice) {
			model.addAttribute("error", "密码错误!");
			model.addAttribute("email", user.getEmail());
			return "forward:/";
		}
	}

	@RequestMapping("/")
	public String index(ModelMap model) {

		return "login";
	}

	@RequestMapping("/news/detail/{id}")
	public String newsDetail(@PathVariable Long id, ModelMap model) {
		News news = newsService.get(id);

		model.addAttribute("model", news);

		return "news.detail";
	}
}
