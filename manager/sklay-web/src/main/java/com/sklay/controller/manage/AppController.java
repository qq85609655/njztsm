package com.sklay.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.DelStatus;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.BeanUtils;
import com.sklay.core.util.Constants;
import com.sklay.model.Application;
import com.sklay.model.ApplicationView;
import com.sklay.model.Product;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
import com.sklay.service.ProductService;
import com.sklay.service.SMSService;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.util.MobileUtil;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ApplicationService appService;

	@Autowired
	private UserService userService;

	@Autowired
	private SMSService smsService;

	@Autowired
	private SklayApi sklayApi;

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("nav", "payApply");
	}

	@RequestMapping("/list")
	@RequiresUser
	public String list(ModelMap modelMap) {

		List<Product> list = productService.list(DelStatus.SAVE);

		modelMap.addAttribute("models", list);

		return "manager.app.apps";
	}

	@RequestMapping("/page")
	@RequiresUser
	public String appUserPage(AuditStatus status, AppType appType,
			String keyword, @PageableDefaults(value = 20) Pageable pageable,
			ModelMap modelMap) {

		modelMap.addAttribute("nav", "myApp");

		User creator = LoginUserHelper.getLoginUser();
		Page<Application> page = appService.getPage(keyword, appType, status,
				creator.getId(), pageable);

		List<Application> list = page != null ? page.getContent() : null;
		List<ApplicationView> result = Lists.newArrayList();
		Map<Long, User> mapUser = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(list))
			for (Application app : list) {
				Long creatorId = app.getOwner();
				User user;
				if (mapUser.containsKey(creatorId))
					user = mapUser.get(creatorId);
				else {
					user = userService.getUser(creatorId);
					mapUser.put(creatorId, user);
				}
				ApplicationView av = new ApplicationView();
				BeanUtils.copyProperties(app, av);

				av.setCreatorUser(user);

				result.add(av);
			}

		long total = null != page ? page.getTotalElements() : 0;
		modelMap.addAttribute("status", status);
		modelMap.addAttribute("app", appType);
		modelMap.addAttribute("pageModel", new PageImpl<ApplicationView>(
				result, pageable, total));
		modelMap.addAttribute("pageQuery", initQuery(keyword, status, appType));
		modelMap.addAttribute("keyword", keyword);

		return "manager.member.apps";
	}

	@RequestMapping("/{appId}/used")
	@RequiresUser
	public String appUsedPage(@PathVariable Long appId, SMSStatus status,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap modelMap) {

		modelMap.addAttribute("nav", "myApp");

		Application app = appService.get(appId);

		User creator = LoginUserHelper.getLoginUser();
		Long belong = null;
		if (LoginUserHelper.isSuperAdmin()) {
			belong = null;
			creator = null;
		} else if (LoginUserHelper.isAdmin())
			belong = creator.getId();
		else
			belong = null;
		Page<SMS> page = smsService.getSMSPage(app, status, creator, belong,
				pageable);

		modelMap.addAttribute("status", status);
		modelMap.addAttribute("appId", appId);
		modelMap.addAttribute("pageModel", page);
		modelMap.addAttribute("pageQuery", initQuery(status));

		return "manager.member.app_used";
	}

	@RequestMapping("/{appId}/reApply")
	@RequiresUser
	public String reApply(@PathVariable Long appId, ModelMap modelMap) {

		Application app = appService.get(appId);
		ApplicationView av = null;
		if (null != app) {
			Long creatorId = app.getOwner();
			Long updator = app.getUpdator();
			User user = userService.getUser(creatorId);

			av = new ApplicationView();
			BeanUtils.copyProperties(app, av);

			av.setCreatorUser(user);
			if (creatorId != updator)
				user = userService.getUser(updator);

			av.setUpdatorUser(user);
		}

		modelMap.addAttribute("model", av);

		return "modal:app.reApply";
	}

	@RequestMapping("/reApply")
	@RequiresUser
	@ResponseBody
	public DataView reApply(@Valid Application app, ModelMap modelMap) {

		// Application model = appService.get(appId);
		//
		// modelMap.addAttribute("model", model);

		return new DataView();
	}

	@RequestMapping("/initApply/{appId}")
	@RequiresUser
	public String initApply(@PathVariable Long appId, ModelMap modelMap) {

		User session = LoginUserHelper.getLoginUser();
		List<Application> list = appService.getByCreator(AppType.PUSH,
				AuditStatus.PASS, session.getId());
		if (CollectionUtils.isNotEmpty(list))
			throw new SklayException(ErrorCode.EXIST, null, new Object[] {
					AuditStatus.PASS.getLable() + "有效申请", "再次申请" });

		Product product = productService.get(appId);

		modelMap.addAttribute("model", product);

		return "modal:app.apply";
	}

	@RequestMapping("/apply")
	@RequiresUser
	@ResponseBody
	public DataView apply(@Valid Application app, ModelMap modelMap) {

		DataView dataView = new DataView();
		if (null == app) {
			dataView.setCode(-1);
			dataView.setMsg("应用无效！");
			return dataView;
		}
		if (null == app.getAppType()) {
			dataView.setCode(-1);
			dataView.setMsg("应用类型无效！");
			return dataView;
		}
		if (null == app.getCost() || app.getCost() <= 0) {
			dataView.setCode(-1);
			dataView.setMsg("支付费用无效！");
			return dataView;
		}
		if (null == app.getProduct()
				|| DelStatus.DELETE == app.getProduct().getDelete()) {
			dataView.setCode(-1);
			dataView.setMsg("产品无效或者产品已下架！");
			return dataView;
		}
		User session = LoginUserHelper.getLoginUser();
		Date date = new Date();

		Product product = app.getProduct();
		Integer count = (int) (app.getCost() / product.getPrice());
		app.setCount(count);
		app.setUsed(0);
		app.setStatus(AuditStatus.WAIT);
		app.setCreateTime(date);
		app.setOwner(session.getId());
		app.setUpdator(session.getId());
		app.setUpdatorTime(date);

		appService.cerate(app);

		return dataView;
	}

	@RequestMapping("/initsms")
	@RequiresUser
	public String initSMS(String phones, ModelMap modelMap) {
		modelMap.addAttribute("nav", "myApp");
		User session = LoginUserHelper.getLoginUser();
		List<Application> list = appService.getByCreator(AppType.PUSH,
				AuditStatus.PASS, session.getId());
		String success = "";
		if (CollectionUtils.isEmpty(list)) {
			success = "有效的业务审核不存在,无法使用该功能!";
		}
		modelMap.addAttribute("phones", phones);
		modelMap.addAttribute("success", success);

		return "manager.app.sms";
	}

	@RequestMapping("/sms")
	@RequiresUser
	@ResponseBody
	public DataView SMS(String phones, String content, ModelMap modelMap) {
		modelMap.addAttribute("nav", "myApp");
		User session = LoginUserHelper.getLoginUser();
		List<Application> list = appService.getByCreator(AppType.PUSH,
				AuditStatus.PASS, session.getId());
		if (CollectionUtils.isEmpty(list))
			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { AuditStatus.PASS.getLable() + "有效申请" });

		if (StringUtils.isBlank(phones) || StringUtils.isBlank(content))
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码或短信类容");

		Application app = list.get(Constants.ZERO);

		String[] phoneArray = phones.split(",");
		if (null == phoneArray || Constants.ZERO == phoneArray.length)
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码");

		Set<String> phoneSet = Sets.newHashSet();
		for (String phone : phoneArray) {
			if (StringUtils.isBlank(phone))
				continue;
			if (!MobileUtil.isMobile(phone)) {
				throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, "手机号码"
						+ phone);
			}
			phoneSet.add(phone);
		}

		if (CollectionUtils.isEmpty(phoneSet))
			throw new SklayException(ErrorCode.FINF_NULL, null, "收信号码");

		Set<SMS> smsList = Sets.newHashSet();

		int sendCount = phoneSet.size();
		Integer smsLength = MobileUtil.lengthOfQuanJiao(content) / 60;
		smsLength = Constants.ZERO == smsLength ? 1 : smsLength;
		int used = sendCount * smsLength;
		int c = app.getCount() - (used + app.getUsed());
		if (c < Constants.ZERO)
			throw new SklayException("短信數量不足,无法发送,短信剩余量 " + c);

		Date date = new Date();
		for (String reciver : phoneSet) {
			SMS sms = new SMS(session.getId(), content, date, session.getId(),
					reciver, SMSStatus.SUCCESS);
			sms.setApp(app);
			smsList.add(sms);
		}

		smsList = sklayApi.sms(smsList);

		/**
		 * TODO
		 */
		if (CollectionUtils.isNotEmpty(smsList)) {
			smsService.create(smsList);
			app.setUsed(used + app.getUsed());
			appService.update(app);
		}

		return new DataView();
	}

	private static String initQuery(String keyword, AuditStatus status,
			AppType appType) {

		String query = "";

		if (null != status)
			query += "status=" + status + "&";
		if (null != appType)
			query += "appType=" + appType + "&";
		if (StringUtils.isNotBlank(keyword))
			query += "keyword=" + keyword.trim().replaceAll("%", "") + "&";
		return query;
	}

	private static String initQuery(SMSStatus status) {

		String query = "";
		if (null != status)
			query += "status=" + status + "&";
		return query;
	}
}
