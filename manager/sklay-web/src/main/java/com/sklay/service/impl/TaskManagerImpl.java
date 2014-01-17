package com.sklay.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.message.NLS;
import com.sklay.core.util.Constants;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.DeviceBinding;
import com.sklay.model.Festival;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.SMSTemplate;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
import com.sklay.service.BindingService;
import com.sklay.service.FestivalService;
import com.sklay.service.GlobalService;
import com.sklay.service.OperationService;
import com.sklay.service.SMSService;
import com.sklay.service.SMSTemplateService;
import com.sklay.service.TaskManager;
import com.sklay.service.UserAttrService;
import com.sklay.service.UserService;

@Service
public class TaskManagerImpl implements TaskManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskManagerImpl.class);

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private GlobalService globalService;

	@Autowired
	private UserAttrService userAttrService;

	@Autowired
	private BindingService bindingService;

	@Autowired
	private ApplicationService appService;

	@Autowired
	private OperationService operationService;

	@Autowired
	private SMSService smsService;

	@Resource
	private FestivalService festivalService;

	@Resource
	private SMSTemplateService smsTemplateService;

	@Resource
	private UserService userService;

	@Override
	@Async
	public void doDayJob() {

		GlobalSetting setting = globalService.getGlobalConfig();

		AppType appType = AppType.TIP;

		Date date = new Date();
		Operation operation = new Operation();
		operation.setName("【" + appType.getLable() + "】任务错误日志");
		operation.setCreateTime(date);
		operation.setType(LogLevelType.ADMIN);

		if (null == setting) {
			operation.setContent("全局站点配置文件不存在");
			operationService.create(operation);

			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "全局站点配置文件" });
		}
		if (SwitchStatus.OPEN != setting.getSendSMSJob()) {

			operation.setContent("全局站点" + appType.getLable() + "业务配置功能已关闭");
			operationService.create(operation);

			throw new SklayException(ErrorCode.CLOSED, null,
					new Object[] { appType.getLable() + "任务" });
		}

		List<User> list = userAttrService.queryBirthdayUser();

		Map<Long, Application> mapApps = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(list)) {
			Set<SMS> jobs = Sets.newHashSet();

			Calendar cal = DateTimeUtil.parseDateTime(setting.getSendSMSTime());

			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);

			Date sendTime = DateTimeUtil.getDate(date, hour, min, second);

			for (User targetUser : list) {
				Long belong = targetUser.getBelong();
				if (null == belong) {
					operation.setContent(JSONObject.toJSONString(targetUser));
					operation.setDesctiption("【" + targetUser.getId() + "】【"
							+ targetUser.getName() + "】的belong"
							+ appType.getLable() + "提醒不存在");
					operationService.create(operation);
					continue;
				}
				Application app = null;
				if (mapApps.containsKey(belong))
					app = mapApps.get(belong);
				else {
					app = appService.get(belong);
					mapApps.put(belong, app);
				}
				if (null == app) {
					operation.setContent("属于【" + belong + "】app"
							+ appType.getLable() + "任务不存在");
					operation.setDesctiption(operation.getContent());
					operationService.create(operation);
					continue;
				}

				if (AuditStatus.PASS != app.getStatus()) {
					operation.setContent(JSONObject.toJSONString(app));
					operation.setDesctiption("属于【" + belong + "】app"
							+ appType.getLable() + "业务审核未完成，无法发送短信");
					operationService.create(operation);
					continue;
				}
				List<DeviceBinding> bandList = bindingService
						.findTargetBinding(targetUser, Level.SECOND);

				if (CollectionUtils.isEmpty(bandList)) {
					operation.setContent(JSONObject.toJSONString(targetUser));
					operation.setDesctiption("【" + targetUser.getId() + "】【"
							+ targetUser.getName() + "】暂时没有附属帐号接收"
							+ appType.getLable() + "短信");
					operationService.create(operation);
					continue;
				}
				String birthdayUserName = targetUser.getName();
				String content = NLS.getMsg(sklayApi.getBirthPairs(),
						new Object[] { birthdayUserName });
				for (DeviceBinding bander : bandList) {
					if (AuditStatus.PASS != bander.getStatus())
						continue;
					if (BindingMold.PAID == bander.getMold()) {
						if (AuditStatus.PASS != bander.getMoldStatus())
							continue;
					}
					User reciver = bander.getTargetUser();
					SMS sms = new SMS(bander.getCreator().getId(), content,
							new Date(), reciver.getId(), reciver.getPhone(),
							SMSStatus.FAIL);

					sms.setApp(app);
					sms.setBelong(belong);
					sms.setSendTime(sendTime);
					jobs.add(sms);
				}

				jobs = sklayApi.birthday(jobs);
				smsService.create(jobs);

			}
		}

		LOGGER.info(" birthday list is {}", list);
	}

	@Override
	@Async
	public void doFestivalJob() {
		String jobTime = DateTimeUtil.getCurrentDate();
		SwitchStatus switchStatus = SwitchStatus.OPEN;

		List<Festival> festivals = festivalService.list(jobTime, switchStatus);

		if (CollectionUtils.isEmpty(festivals)) {
			return;
		}

		AppType appType = AppType.WISH;

		Operation operation = new Operation();
		operation.setName("【" + appType.getLable() + "】任务错误日志");
		operation.setCreateTime(new Date());
		operation.setType(LogLevelType.ADMIN);

		List<Application> apps = appService.getByAppType(appType);

		if (CollectionUtils.isEmpty(apps)) {
			operation.setContent("【" + appType.getLable() + "】app应用不存在");
			operation.setDesctiption(operation.getContent());
			operationService.create(operation);
			return;
		}

		Application app = apps.get(Constants.ZERO);

		if (AuditStatus.PASS != app.getStatus()) {
			operation.setContent(JSONObject.toJSONString(app));
			operation.setDesctiption("【" + appType.getLable() + "】app审核未完成");
			operationService.create(operation);
			return;
		}

		Festival festival = festivals.get(Constants.ZERO);

		AuditStatus status = AuditStatus.PASS;
		List<SMSTemplate> templates = smsTemplateService.list(status, festival);
		if (CollectionUtils.isEmpty(templates)) {
			operation.setContent("【" + appType.getLable() + "】"
					+ festival.getId() + "  " + festival.getName() + "  "
					+ festival.getJobTime() + "app短信模版不存在");
			operation.setDesctiption(operation.getContent());
			operationService.create(operation);
			return;
		}

		int szie = templates.size();

		List<User> users = userService.getUser(status);

		if (CollectionUtils.isEmpty(users)) {
			operation.setContent("【" + appType.getLable() + "】"
					+ festival.getId() + "  " + festival.getName() + "  "
					+ festival.getJobTime() + "app审核通过的会员不存在");

			operation.setDesctiption(operation.getContent());
			operationService.create(operation);
			return;
		}
		Long creator = app.getOwner();
		Date sendTime = festival.getSendTime();

		Set<SMS> jobs = Sets.newHashSet();
		for (User user : users) {

			int index = (int) (Math.random() * szie);
			String remark = templates.get(index).getTpl();
			String content = templates.get(index).getContent();
			Long receiver = user.getId();
			String mobile = user.getPhone();
			SMS sms = new SMS(creator, content, sendTime, receiver, mobile,
					SMSStatus.FAIL);
			sms.setRemark(remark);
			sms.setApp(app);
			jobs.add(sms);
		}

		jobs = sklayApi.festival(jobs);

		if (CollectionUtils.isNotEmpty(jobs))
			smsService.create(jobs);
	}
}
