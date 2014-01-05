package com.sklay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.message.NLS;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;
import com.sklay.service.BindingService;
import com.sklay.service.GlobalService;
import com.sklay.service.OperationService;
import com.sklay.service.SMSService;
import com.sklay.service.TaskManager;
import com.sklay.service.UserAttrService;

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

	@Override
	@Async
	public void doDayJob() {

		GlobalSetting setting = globalService.getGlobalConfig();

		Operation operation = new Operation();
		operation.setName("定时任务错误日志");
		operation.setCreateTime(new Date());
		operation.setType(LogLevelType.ADMIN);

		if (null == setting) {
			operation.setContent("全局站点配置文件不存在");
			operationService.create(operation);

			throw new SklayException(ErrorCode.FINF_NULL, null,
					new Object[] { "全局站点配置文件" });
		}
		if (SwitchStatus.OPEN != setting.getSendSMSJob()) {

			operation.setContent("全局站点生日提醒业务配置功能已关闭");
			operationService.create(operation);

			throw new SklayException(ErrorCode.CLOSED, null,
					new Object[] { "生日提醒任务" });
		}

		List<User> list = userAttrService.queryBirthdayUser();

		Map<Long, Application> mapApps = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(list)) {
			Set<SMS> jobs = Sets.newHashSet();
			for (User targetUser : list) {
				Long belong = targetUser.getBelong();
				if (null == belong) {
					operation.setContent(JSONObject.toJSONString(targetUser)
							+ "的belong生日短信提醒不存在");
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
					operation.setContent("属于【" + belong + "】app生日提醒任务不存在");
					operationService.create(operation);
					continue;
				}

				if (AuditStatus.PASS != app.getStatus()) {
					operation.setContent("属于【" + belong
							+ "】app生日提醒业务审核未完成，无法发送短信");
					operationService.create(operation);
					continue;
				}
				List<DeviceBinding> bandList = bindingService
						.findTargetBinding(targetUser, Level.SECOND);

				if (CollectionUtils.isEmpty(bandList)) {
					operation.setContent(JSONObject.toJSONString(targetUser)
							+ "暂时没有附属帐号接收生日提醒短信");
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
					jobs.add(sms);
				}

				// jobs = sklayApi.birthday(jobs);
				smsService.create(jobs);

			}
		}

		LOGGER.info(" birthday list is {}", list);
	}

}
