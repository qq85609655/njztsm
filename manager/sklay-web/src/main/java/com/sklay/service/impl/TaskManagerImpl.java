package com.sklay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.OperatorType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.Sex;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.message.NLS;
import com.sklay.core.util.Constants;
import com.sklay.dao.MedicalReportDao;
import com.sklay.model.ChartData;
import com.sklay.model.DeviceBinding;
import com.sklay.model.MataData;
import com.sklay.model.MedicalReport;
import com.sklay.model.SMS;
import com.sklay.model.SMSLog;
import com.sklay.model.User;
import com.sklay.service.BindingService;
import com.sklay.service.MatadataService;
import com.sklay.service.SMSLogService;
import com.sklay.service.SMSService;
import com.sklay.service.TaskManager;
import com.sklay.util.Convert;

@Service
public class TaskManagerImpl implements TaskManager {

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private SMSService smsService;

	@Autowired
	private MedicalReportDao medicalReportDao;

	@Autowired
	private BindingService bindingService;

	@Autowired
	private MatadataService matadataService;

	@Override
	public void doDayJob() {

		String time = "2013-12-15";// DateTimeUtil.getCurrentDate();
		SMSType reportType = SMSType.PHYSICAL;

		List<MedicalReport> list = medicalReportDao.findTodayReport(time,
				reportType);
		Map<User, Set<MedicalReport>> map = Maps.newHashMap();

		if (CollectionUtils.isNotEmpty(list)) {
			for (MedicalReport mr : list) {
				User targetUser = mr.getTargetUser();
				Set<MedicalReport> set = Sets.newHashSet();

				if (map.containsKey(targetUser)) {
					set = map.get(targetUser);
					set.add(mr);
				} else {
					set.add(mr);
				}
				map.put(targetUser, set);
			}

		}
		Map<Long, Set<SMS>> logs = splitResult(map);

		sendSMS(logs);
	}

	private Map<Long, Set<SMS>> splitResult(Map<User, Set<MedicalReport>> map) {
		Map<Long, Set<SMS>> logs = Maps.newConcurrentMap();

		if (map == null || map.size() <= 0)
			return logs;

		Set<User> keysSet = map.keySet();
		for (User key : keysSet) {
			Set<MedicalReport> reports = map.get(key);
			Long lowPressure = 0L;
			Long highPressure = 0L;
			String simNo = "";
			ChartData data;
			User targetUser = null;
			for (MedicalReport rp : reports) {
				data = JSONObject.parseObject(rp.getOriginalData(),
						ChartData.class);
				lowPressure += Long.parseLong(data.getLowPressure());
				highPressure += Long.parseLong(data.getHighPressure());
				targetUser = rp.getTargetUser();
				simNo = targetUser.getPhone();
			}

			data = new ChartData(simNo, lowPressure.toString(),
					highPressure.toString());
			List<DeviceBinding> list = bindingService
					.getDefaultBindingUser(simNo);

			Set<SMS> listSMS = getMedicalReport(data, targetUser, list);

			logs.put(targetUser.getId(), listSMS);
		}

		return logs;
	}

	private Set<SMS> getMedicalReport(ChartData gatherData, User targetUser,
			List<DeviceBinding> list) {
		Date date = new Date();
		long reportTime = date.getTime();
		String userName = targetUser.getName();
		Set<SMS> logs = Sets.newHashSet();
		int age = targetUser.getAge();
		Sex sex = targetUser.getSex();
		String pulse = gatherData.getPulse().trim();
		String lowPressure = gatherData.getLowPressure().trim();
		String highPressure = gatherData.getHighPressure().trim();
		int lowP = Integer.valueOf(lowPressure);
		int highP = Integer.valueOf(highPressure);
		/** 健康指数 */
		int indexHealth = (highP + 2 * lowP) / 3;

		gatherData.setIndexHealth(indexHealth);

		List<MataData> results = matadataService.queryUserMataData(age, sex,
				lowP, highP);

		String resultReport = "系统中暂无诊断结果";
		/** 检查体检结果 */
		if (CollectionUtils.isNotEmpty(results)) {
			MataData result = results.get(0);
			resultReport = result.getResult();
		}

		String content = NLS.getMsg(sklayApi.getPhysical(), new Object[] {
				userName, highP, lowP, pulse, resultReport });
		for (DeviceBinding bd : list) {
			User reciver = bd.getTargetUser();
			SMS log = new SMS(reciver.getId(), content, date, reciver.getPhone(), SMSStatus.FAIL,date.getTime()) ;
			logs.add(log);
		}

		return logs;
	}

	/**
	 * TODO
	 * @param logs
	 */
	private void sendSMS(Map<Long, Set<SMS>> logs) {

		if (null == logs || logs.size() <= Constants.ZERO)
			return;
		Set<Long> keys = logs.keySet();
		for (Long key : keys) {
			Set<SMS> smsLogs = logs.get(key);
			String SMSContent = smsLogs.iterator().next().getContent();

			if (CollectionUtils.isEmpty(smsLogs))
				throw new SklayException(ErrorCode.SMS_NULL_SMS);

			Map<OperatorType, Set<String>> phoneMap = Maps.newHashMap();
			Set<String> str = Sets.newHashSet();
			str.add("15105151253");
			phoneMap.put(OperatorType.CHINAMOBILE, str);

			smsLogs = sklayApi.sendSMS(smsLogs);

			if (CollectionUtils.isNotEmpty(smsLogs))
				smsService.create(smsLogs);
		}
	}

}
