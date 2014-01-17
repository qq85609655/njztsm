package com.sklay.controller.manage;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;
import com.sklay.model.SMSTemplate;
import com.sklay.service.SMSTemplateService;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/admin/smstpl")
public class SMSTemplateController {

	@Resource
	private SMSTemplateService smsTemplateService;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "festival:list");
		model.addAttribute("href", "/admin/festival/list");
	}

	@RequestMapping("/{fid}/list")
	@RequiresPermissions("festival:list")
	public String list(AuditStatus status, String keyword,
			@PathVariable Long fid,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		String jobTime = null;
		Festival festival = new Festival();
		festival.setId(Long.valueOf(fid));
		Page<SMSTemplate> page = smsTemplateService.getSMSTemplatePage(jobTime,
				status, festival, keyword, pageable);

		String pageQuery = initQuery(fid, status, keyword);

		modelMap.addAttribute("fid", fid);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("keyword", keyword);
		modelMap.addAttribute("checkedStatus", status);
		modelMap.addAttribute("pageQuery", pageQuery);

		return "manager.smstpl.list";
	}

	@RequestMapping("/initCreate/{fid}")
	@RequiresPermissions("festival:create")
	public String initCreate(@PathVariable Long fid, ModelMap modelMap) {
		modelMap.addAttribute("subnav", "festival:create");
		modelMap.addAttribute("thirdbreadcrumb", "创建节日");
		modelMap.addAttribute("fid", fid);

		return "modal:smstpl.initCreate";
	}

	@RequestMapping("/create")
	@RequiresPermissions("festival:create")
	@ResponseBody
	public DataView create(SMSTemplate smstpl, ModelMap modelMap) {

		if (null == smstpl)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "参数" });

		if (StringUtils.isBlank(smstpl.getTpl()))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "模版参数" });

		String tpl = smstpl.getTpl().trim();

		if (StringUtils.isBlank(smstpl.getContent()))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "短信内容" });

		String content = smstpl.getContent().trim();
		if (null == smstpl.getFestival()
				|| null == smstpl.getFestival().getId())
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "节日参数" });

		if (null == smstpl.getStatus())
			smstpl.setStatus(AuditStatus.WAIT);

		smstpl.setTpl(tpl);
		smstpl.setContent(content);

		Festival festival = new Festival();
		festival.setId(smstpl.getFestival().getId());

		List<SMSTemplate> list = smsTemplateService.list(tpl, festival);

		if (CollectionUtils.isNotEmpty(list)) {
			throw new SklayException(ErrorCode.EXIST, null, new Object[] {
					"同一节日同一模版", "添加" });
		}

		smsTemplateService.create(smstpl);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/initUpdate/{id}")
	@RequiresPermissions("festival:update")
	public String initUpdate(@PathVariable Long id, ModelMap modelMap) {
		SMSTemplate smstpl = null;
		if (null != id)
			smstpl = smsTemplateService.get(id);

		modelMap.addAttribute("smstpl", smstpl);
		return "modal:smstpl.initUpdate";
	}

	@RequestMapping("/update")
	@RequiresPermissions("festival:update")
	@ResponseBody
	public DataView update(SMSTemplate smstpl, ModelMap modelMap) {

		if (null == smstpl)
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "参数" });

		if (StringUtils.isBlank(smstpl.getTpl()))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "模版参数" });
		String tpl = smstpl.getTpl().trim();

		if (StringUtils.isBlank(smstpl.getContent()))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "短信内容" });
		String content = smstpl.getContent().trim();

		if (null == smstpl.getId())
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "节日Id参数" });
		Long id = smstpl.getId();

		if (null == smstpl.getFestival()
				|| null == smstpl.getFestival().getId())
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "节日参数" });

		if (null == smstpl.getStatus())
			smstpl.setStatus(AuditStatus.WAIT);

		smstpl.setTpl(tpl);
		smstpl.setContent(content);

		Festival festival = new Festival();
		festival.setId(smstpl.getFestival().getId());

		List<SMSTemplate> list = smsTemplateService.list(tpl, festival);

		if (CollectionUtils.isNotEmpty(list)) {
			for (SMSTemplate hasTpl : list) {
				if (id != hasTpl.getId())
					throw new SklayException(ErrorCode.EXIST, null,
							new Object[] { "同一节日同一模版", "添加" });
			}
		}

		SMSTemplate orginal = smsTemplateService.get(id);

		if (null == orginal)
			throw new SklayException(ErrorCode.MISS_PARAM, null, "节日对象");

		if (orginal.equals(smstpl))
			return new DataView(0, "操作成功.");

		BeanUtils.copyProperties(smstpl, orginal, new String[] { "id",
				"festival" });

		smsTemplateService.update(orginal);

		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/off/{id}")
	@RequiresPermissions("festival:off")
	@ResponseBody
	public DataView off(@PathVariable Long id, ModelMap modelMap) {

		if (null != id) {
			SMSTemplate festival = smsTemplateService.get(id);
			if (null != festival && AuditStatus.NOT != festival.getStatus()) {
				festival.setStatus(AuditStatus.NOT);
				smsTemplateService.update(festival);
			}
		}
		return new DataView(0, "操作成功.");
	}

	@RequestMapping("/on/{id}")
	@RequiresPermissions("festival:on")
	@ResponseBody
	public DataView on(@PathVariable Long id) {
		if (null != id) {
			SMSTemplate festival = smsTemplateService.get(id);
			if (null != festival && AuditStatus.PASS != festival.getStatus()) {
				festival.setStatus(AuditStatus.PASS);
				smsTemplateService.update(festival);
			}
		}
		return new DataView(0, "操作成功.");
	}

	private static String initQuery(Long fid, AuditStatus status, String keyword) {

		String query = "";

		if (null != fid)
			query += "fid=" + fid + "&";
		if (null != status)
			query += "status=" + status + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

}
