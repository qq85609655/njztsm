package com.sklay.controller.manage;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

import com.google.common.collect.Sets;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.Sex;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.model.MataData;
import com.sklay.service.MatadataService;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/matadata")
@Widgets(value = "matadata", description = "元数据管理")
public class MatadateController {

	@Autowired
	private MatadataService matadataService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "matadata:model");
		model.addAttribute("subnav", "matadata:list");
		model.addAttribute("subHref", "/admin/matadata/list");
	}

	@RequestMapping("/list")
	@RequiresPermissions("matadata:list")
	@Widget(name = ":list", description = "元数据列表")
	public String list(Integer minAge, Integer maxAge, Integer sex,
			Integer lowPressure, Integer highPressure, String keyword,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		Sex usersex = null == sex ? null : Sex.findByValue(sex);
		Page<MataData> page = matadataService.getMataDataPage(minAge, maxAge,
				usersex, lowPressure, highPressure, keyword, pageable);
		String pageQuery = initQuery(minAge, maxAge, sex, lowPressure,
				highPressure, keyword);

		modelMap.addAttribute("minAge", minAge);
		modelMap.addAttribute("maxAge", maxAge);
		modelMap.addAttribute("checkedSex", sex);
		modelMap.addAttribute("highPressure", highPressure);
		modelMap.addAttribute("lowPressure", lowPressure);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", pageQuery);
		modelMap.addAttribute("keyword", keyword);

		return "manager.matadata.list";
	}

	@RequestMapping("/initCreate")
	@RequiresPermissions("matadata:create")
	@Widget(name = ":create", description = "创建元数据")
	public String initCreate(ModelMap modelMap) {
		modelMap.addAttribute("subnav", "matadata:create");
		modelMap.addAttribute("thirdbreadcrumb", "创建元数据");

		return "manager.matadata.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("matadata:create")
	@ResponseBody
	public DataView create(MataData mataData, ModelMap modelMap) {
		boolean checkId = false;
		checkMatadata(mataData, checkId);

		if (matadataService.exist(mataData))
			throw new SklayException(ErrorCode.EXIST, null, new Object[] {
					mataData, "再次添加" });

		matadataService.create(mataData);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/initUpdate/{id}")
	@RequiresPermissions("matadata:update")
	@Widget(name = ":update", description = "修改元数据", level = WidgetLevel.THIRD)
	public String initUpdate(@PathVariable Long id, ModelMap modelMap) {
		modelMap.addAttribute("thirdbreadcrumb", "修改元数据");
		MataData mataData = null;
		if (null != id)
			mataData = matadataService.getMataData(id);

		modelMap.addAttribute("mataData", mataData);
		return "modal:matadata.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("matadata:update")
	@ResponseBody
	public DataView update(MataData mataData, ModelMap modelMap) {

		boolean checkId = true;
		checkMatadata(mataData, checkId);

		MataData orginal = matadataService.getMataData(mataData.getId());

		if (null == orginal)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "元数据对象");

		if (orginal.equals(mataData))
			return new DataView(0, "操作成功.");

		BeanUtils.copyProperties(mataData, orginal, new String[] { "id" });

		matadataService.update(orginal);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/detail")
	@RequiresPermissions("matadata:detail")
	@Widget(name = ":matadata", description = "元素据详情", level = WidgetLevel.THIRD)
	public String detail(Long userId, ModelMap modelMap) {
		modelMap.addAttribute("nav", "matadata");
		modelMap.addAttribute("subnav", "matadata:detail");

		return "modal:matadata.detail";
	}

	@RequestMapping("/initDelete")
	@RequiresPermissions("matadata:delete")
	@Widget(name = ":delete", description = "删除元素据", level = WidgetLevel.THIRD)
	public String initDelete(Long[] ids, ModelMap modelMap) {

		List<MataData> list = null;
		if (null != ids && ids.length > 0) {
			Set<Long> idSet = Sets.newHashSet();
			for (Long id : ids)
				idSet.add(id);
			list = matadataService.getMataData(idSet);
		}
		modelMap.addAttribute("list", list);
		return "modal:matadata.delete";
	}

	@RequestMapping("/delete/{ids}")
	@RequiresPermissions("matadata:delete")
	@Widget(name = ":delete", description = "删除元素据", level = WidgetLevel.THIRD)
	@ResponseBody
	public DataView delete(@PathVariable Long[] ids) {
		List<MataData> list = null;
		if (null != ids && ids.length > 0) {
			Set<Long> idSet = Sets.newHashSet();
			for (Long id : ids)
				idSet.add(id);
			list = matadataService.getMataData(idSet);
			if (CollectionUtils.isNotEmpty(list))
				matadataService.delete(Sets.newHashSet(list));
		}
		return new DataView(0, "操作成功.");
	}

	private static String initQuery(Integer minAge, Integer maxAge,
			Integer sex, Integer lowPressure, Integer highPressure,
			String keyword) {

		String query = "";

		if (null != minAge)
			query += "minAge=" + minAge + "&";
		if (null != minAge)
			query += "maxAge=" + maxAge + "&";
		if (null != sex)
			query += "sex=" + sex + "&";
		if (null != lowPressure && null != highPressure)
			query += "lowPressure=" + "&highPressure=" + highPressure + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

	private static void checkMatadata(MataData mataData, boolean checkId) {

		if (null == mataData)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "元数据对象");

		if (checkId && null == mataData.getId())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "元数据对象");

		if (null == mataData.getAgeMin() || null == mataData.getAgeMax()
				|| mataData.getAgeMax() < mataData.getAgeMin()
				|| mataData.getAgeMin() < 1)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "年龄设置范围");

		if (null == mataData.getLowPressureMin()
				|| null == mataData.getLowPressureMax()
				|| mataData.getLowPressureMax() < mataData.getLowPressureMin()
				|| mataData.getLowPressureMin() < 1)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "血压低值设置范围");

		if (null == mataData.getHighPressureMin()
				|| null == mataData.getHighPressureMax()
				|| mataData.getHighPressureMax() < mataData
						.getHighPressureMin()
				|| mataData.getHighPressureMin() < 1)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "血压高值设置范围");

		if (StringUtils.isBlank(mataData.getResult()))
			throw new SklayException(ErrorCode.MISS_PARAM, null, "体检结果");

		if (null == mataData.getSex() || Sex.UNKNOWN == mataData.getSex())
			throw new SklayException(ErrorCode.MISS_PARAM, null, "性别");

		if (!(mataData.getAgeMax() > mataData.getAgeMin() && mataData
				.getAgeMin() > 1))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "年龄设置范围");

		if (!(mataData.getLowPressureMax() > mataData.getLowPressureMin() && mataData
				.getLowPressureMin() > 1))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "血压低值设置范围");

		if (!(mataData.getHighPressureMax() > mataData.getHighPressureMin() && mataData
				.getHighPressureMin() > 1))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "血压高值设置范围");
	}
}
