package com.sklay.controller.manage;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

import com.google.common.collect.Sets;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;
import com.sklay.service.FestivalService;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/festival")
@Widgets(value = "festival", description = "节日管理")
public class FestivalController {

	@Resource
	private FestivalService festivalService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "festival:model");
		model.addAttribute("subnav", "festival:list");
		model.addAttribute("subHref", "/admin/festival/list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("festival:list")
	@Widget(name = ":list", description = "节日列表")
	public String list(String jobTime, SwitchStatus switchStatus,
			String keyword, @PageableDefaults(value = 20) Pageable pageable,
			ModelMap modelMap) {

		Page<Festival> page = festivalService.getFestivalPage(jobTime,
				switchStatus, keyword, pageable);

		String pageQuery = initQuery(jobTime, switchStatus, keyword);

		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("keyword", keyword);

		return "manager.festival.list";
	}

	@RequestMapping("/initCreate")
	@RequiresPermissions("festival:create")
	@Widget(name = ":create", description = "创建节日")
	public String initCreate(ModelMap modelMap) {
		modelMap.addAttribute("subnav", "festival:create");
		modelMap.addAttribute("thirdbreadcrumb", "创建节日");

		return "manager.festival.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("festival:create")
	@ResponseBody
	public DataView create(Festival festival, ModelMap modelMap) {

		festivalService.create(festival);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/initUpdate/{id}")
	@RequiresPermissions("festival:update")
	@Widget(name = ":update", description = "修改元节日", level = WidgetLevel.THIRD)
	public String initUpdate(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("thirdbreadcrumb", "修改节日");
		Festival festival = null;
		if (null != id)
			festival = festivalService.get(id);

		modelMap.addAttribute("festival", festival);
		return "modal:festival.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("festival:update")
	@ResponseBody
	public DataView update(Festival festival, ModelMap modelMap) {

		Festival orginal = festivalService.get(festival.getId());

		if (null == orginal)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "节日对象");

		if (orginal.equals(festival))
			return new DataView(0, "操作成功.");

		BeanUtils.copyProperties(festival, orginal, new String[] { "id" });

		festivalService.update(orginal);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/off")
	@RequiresPermissions("festival:off")
	@ResponseBody
	@Widget(name = ":off", description = "停用", level = WidgetLevel.THIRD)
	public DataView off(Long[] ids, ModelMap modelMap) {

		if (null != ids && ids.length > 0) {
			Set<Long> idSet = Sets.newHashSet();
			for (Long id : ids)
				idSet.add(id);

			festivalService.offOn(idSet, SwitchStatus.CLOSE);
		}
		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/on")
	@RequiresPermissions("festival:on")
	@Widget(name = ":on", description = "启用", level = WidgetLevel.THIRD)
	@ResponseBody
	public DataView on(@PathVariable Long[] ids) {
		if (null != ids && ids.length > 0) {
			Set<Long> idSet = Sets.newHashSet();
			for (Long id : ids)
				idSet.add(id);
			festivalService.offOn(idSet, SwitchStatus.OPEN);
		}
		return new DataView(0, "操作成功.");
	}

	private static String initQuery(String jobTime, SwitchStatus switchStatus,
			String keyword) {

		String query = "";

		if (StringUtils.isNotBlank(jobTime))
			query += "jobTime=" + jobTime + "&";
		if (null != switchStatus)
			query += "switchStatus=" + switchStatus + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

}
