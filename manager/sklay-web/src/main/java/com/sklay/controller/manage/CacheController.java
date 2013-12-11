package com.sklay.controller.manage;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.cache.SimpleEhCacheCacheManager;

@Controller
@RequestMapping("/admin/cache")
@Widgets(value = "cache", description = "缓存管理", isShow = false)
public class CacheController {

	@Autowired
	private SimpleEhCacheCacheManager cacheManager;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "cache:list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("cache:list")
	@Widget(name = ":list", description = "缓存管理", isShow = false)
	public String cacheManage(ModelMap modelMap) {
		String[] cacheNames = cacheManager.getAllCacheNames();
		cacheManager.getCache(cacheNames[0]);
		//
		// cacheManager.clearAll();
		// cacheNames = cacheManager.getAllCacheNames();
		return "manager.cache.list";
	}
}
