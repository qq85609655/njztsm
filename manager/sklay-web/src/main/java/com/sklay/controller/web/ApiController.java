package com.sklay.controller.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.Sex;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.message.NLS;
import com.sklay.core.util.Constants;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.ChartData;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GatherData;
import com.sklay.model.GlobalSetting;
import com.sklay.model.MataData;
import com.sklay.model.MedicalReport;
import com.sklay.model.Mold;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.model.UserAttr;
import com.sklay.service.ApplicationService;
import com.sklay.service.BindingService;
import com.sklay.service.GlobalService;
import com.sklay.service.MatadataService;
import com.sklay.service.MedicalReportService;
import com.sklay.service.OperationService;
import com.sklay.service.SMSService;
import com.sklay.service.TaskManager;
import com.sklay.service.UserAttrService;
import com.sklay.util.DateUtils;

@Controller
@RequestMapping("/api")
public class ApiController {

	Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private MedicalReportService medicalReportService;

	@Autowired
	private OperationService operationService;

	@Autowired
	private MatadataService matadataService;

	@Autowired
	private BindingService bindingService;

	@Autowired
	private SMSService smsService;

	@Autowired
	private UserAttrService attrService;

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private GlobalService globalService;

	@Autowired
	private SklayApi sklayApi;

	@Autowired
	private ApplicationService appService;

	@RequestMapping("/physical")
	@ResponseBody
	public int physical(GatherData gatherData) throws SklayException {

		/** 检查系统配置 */
		GlobalSetting setting = globalService.getGlobalConfig();

		Operation operation = null;
		if (SwitchStatus.OPEN == filterPhysicalSetting(setting)) {

			operation = new Operation();
			operation.setContent(gatherData.toString());
			operation.setName("体检短信");
			operation.setType(LogLevelType.API);
			operation.setDesctiption(LogLevelType.API.getLable());
			operation.setCreateTime(new Date());

		}

		if (null == gatherData) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 体检采集的数据 不能为空.");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.FINF_NULL, null, "体检采集的数据");
		}
		if (StringUtils.isBlank(gatherData.getSimNo())) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 采集卡号不能为空!");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.SMS_GATHER_SIMNO_EMPTY);
		}
		/** 获取绑定主号 */
		DeviceBinding deviceBinding = bindingService
				.getDefaultBinding(gatherData.getSimNo().trim());

		if (null == deviceBinding) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption() + " 设备号【"
						+ gatherData.getSimNo() + "】还未绑定主帐号");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.SMS_GATHER_ERROR);
		}
		if (null == deviceBinding.getTargetUser()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption() + " 设备号【"
						+ gatherData.getSimNo() + "】还未绑定主帐号");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.SMS_GATHER_NOBANDING, null,
					gatherData.getSimNo());
		}
		if (AuditStatus.PASS != deviceBinding.getStatus()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 设备绑定信息审核未完成,无法发送短信");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"设备绑定信息", "发送短信" });
		}
		User targetUser = deviceBinding.getTargetUser();
		User creator = deviceBinding.getCreator();

		if (AuditStatus.PASS != targetUser.getStatus()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 用户信息审核未完成,无法发送短信");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"用户信息", "发送短信" });
		}
		long targetUserId = targetUser.getId();
		long creatorId = creator.getId();
		long timeStart = DateTimeUtil.getCurrentDay().getTime();
		long timeEnd = DateTimeUtil.getNextDay().getTime();
		/** 检查系统配置 */
		if (setting.getVisitCount() != Constants.ALL) {
			long allowCount = setting.getVisitCount();
			long visitCount = medicalReportService.countDayReport(targetUserId,
					timeStart, timeEnd, SMSType.PHYSICAL);
			if (allowCount <= visitCount) {
				if (null != operation) {
					operation.setDesctiption(operation.getDesctiption()
							+ " 每天的体检次数已达到上线");
					operationService.create(operation);
				}
				throw new SklayException(ErrorCode.MORE_COUNT);
			}
		}

		Date dataTime = new Date();

		/** 创建体检报告 */
		MedicalReport medicalReport = getMedicalReport(gatherData, targetUser,
				sklayApi.getPhysical_tpl(), SMSType.PHYSICAL, dataTime);

		/** 短信內容 */
		String content = medicalReport.getSmsContent();
		Long reportTime = medicalReport.getReportTime();
		Date reportDate = new Date(reportTime);
		SwitchStatus switchStatus = setting.getSmsFetch();
		Set<SMS> smsLogs = Sets.newHashSet();
		Long belong = targetUser.getBelong();

		Application app = appService.getByCreator(AppType.PHYSICAL, belong);

		if (null == app || AuditStatus.PASS != app.getStatus()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 体检短信应用【" + belong + "】 不存在或未完成审核");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"应用信息不存在或未完成审核", "发送短信" });
		}
		/** 全部发送 */
		if (SwitchStatus.OPEN == switchStatus) {
			/** 获取已绑定的审核通过的用户 */
			List<User> freeList = initUser(gatherData);

			if (CollectionUtils.isNotEmpty(freeList)) {
				for (User reciver : freeList) {
					SMS log = new SMS(creatorId, content, reportDate,
							reciver.getId(), reciver.getPhone(),
							SMSStatus.SUCCESS);
					log.setBelong(belong);
					log.setApp(app);
					smsLogs.add(log);
				}
			}
		}
		/** 只发送给主机号 */
		else {
			SMS log = new SMS(creator.getId(), content, reportDate,
					targetUser.getId(), targetUser.getPhone(), SMSStatus.FAIL);
			log.setBelong(belong);
			log.setApp(app);
			smsLogs.add(log);
		}

		/** 发送短信 */
		if (CollectionUtils.isNotEmpty(smsLogs)) {
			smsLogs = sklayApi.physical(smsLogs);
			smsService.create(smsLogs);
		}

		return 0;
	}

	@RequestMapping("/asyncphysical")
	@ResponseBody
	@Async
	public void asyncPhysicalc(GatherData gatherData) throws SklayException {
		taskManager.doDayJob();
	}

	@RequestMapping("/sos")
	@ResponseBody
	public int sos(GatherData gatherData) throws SklayException {

		/** 检查系统配置 */
		GlobalSetting setting = globalService.getGlobalConfig();
		Operation operation = null;
		if (SwitchStatus.OPEN == filterSOSSetting(setting)) {

			operation = new Operation();
			operation.setContent(gatherData.toString());
			operation.setName("定位短信");
			operation.setType(LogLevelType.API);
			operation.setDesctiption(LogLevelType.API.getLable());
			operation.setCreateTime(new Date());
		}

		if (null == gatherData) {
			if (null != operation) {
				operation.setDesctiption("定位数据采集为空");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.FINF_NULL, null, "定位的数据");
		}
		if (StringUtils.isBlank(gatherData.getSimNo())) {
			if (null != operation) {
				operation.setDesctiption("采集卡号不能为空!");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.SMS_GATHER_SIMNO_EMPTY);
		}

		DeviceBinding deviceBinding = bindingService
				.getDefaultBinding(gatherData.getSimNo().trim());

		if (null == deviceBinding
				|| AuditStatus.PASS != deviceBinding.getStatus()) {
			if (null != operation) {
				operation.setDesctiption("设备:" + gatherData.getSimNo()
						+ "还未绑定主用户");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.SMS_GATHER_NOBANDING, null,
					new Object[] { gatherData.getSimNo() });
		}

		if (null == deviceBinding.getTargetUser()
				|| AuditStatus.PASS != deviceBinding.getTargetUser()
						.getStatus()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 用户信息审核未完成,无法发送短信");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"用户信息", "发送短信" });
		}
		User creator = deviceBinding.getCreator();
		User bandingor = deviceBinding.getTargetUser();
		long belong = deviceBinding.getBelong();
		long creatorId = creator.getId();
		Date dataTime = new Date();

		Application app = appService.getByCreator(AppType.PHYSICAL, belong);

		if (null == app || AuditStatus.PASS != app.getStatus()) {
			if (null != operation) {
				operation.setDesctiption(operation.getDesctiption()
						+ " 体检短信应用【" + belong + "】 不存在或未完成审核");
				operationService.create(operation);
			}
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"应用信息不存在或", "发送短信" });
		}

		MedicalReport medicalReport = getMedicalReport(gatherData, bandingor,
				sklayApi.getSosPairs(), SMSType.LOCATION, dataTime);

		/** 短信內容 */
		String content = medicalReport.getSmsContent();

		Set<SMS> smsLogs = Sets.newHashSet();

		/** 获取已绑定的审核通过的用户 */
		List<User> freeList = initUser(gatherData);

		if (CollectionUtils.isNotEmpty(freeList)) {
			for (User targetUser : freeList) {
				SMS log = new SMS(creatorId, content, new Date(
						medicalReport.getReportTime()), targetUser.getId(),
						targetUser.getPhone(), SMSStatus.SUCCESS);

				log.setApp(app);
				log.setBelong(belong);
				smsLogs.add(log);
			}
		}

		/** 发送短信 */
		if (CollectionUtils.isNotEmpty(smsLogs)) {
			smsLogs = sklayApi.sos(smsLogs);
			smsService.create(smsLogs);
		}

		return 0;
	}

	private MedicalReport getMedicalReport(GatherData gatherData, User user,
			String smsTemplate, SMSType type, Date dataTime) {

		long reportTime = dataTime.getTime();
		String userName = user.getName();

		MedicalReport report = null;

		/** 体检 */
		if (SMSType.PHYSICAL == type) {

			int age = user.getAge();
			Sex sex = user.getSex();
			String pulse = gatherData.getPulse().trim();
			String lowPressure = gatherData.getLowPressure().trim();
			String highPressure = gatherData.getHighPressure().trim();
			int lowP = Integer.valueOf(lowPressure);
			int highP = Integer.valueOf(highPressure);
			/** 健康指数 */
			int indexHealth = (highP + 2 * lowP) / 3;
			gatherData.setIndexHealth(indexHealth);

			List<MataData> results = matadataService.queryUserMataData(age,
					sex, lowP, highP);

			String resultReport = "系统中暂无诊断结果";
			String resultRmark = "";
			/** 检查体检结果 */
			if (CollectionUtils.isNotEmpty(results)) {
				MataData result = results.get(0);
				resultReport = result.getResult();
				resultRmark = result.getRemark();
			}

			String SMSContent = NLS.getMsg(smsTemplate, new Object[] {
					userName, highP, lowP, pulse, resultReport });

			ChartData chartData = new ChartData(gatherData.getSimNo(),
					gatherData.getLowPressure(), gatherData.getHighPressure(),
					gatherData.getPulse(), gatherData.getLongitude(),
					gatherData.getLatitude(), indexHealth);

			/** 保存采集到的原始数据 */
			String originalData = JSONObject.toJSONString(chartData);

			Long userId = user.getId();
			TipType tipType = TipType.INFO;
			if ((highP - lowP) >= 50)
				tipType = TipType.WARNING;
			else {
				MedicalReport lastReport = medicalReportService
						.getMemberLastReports(userId);
				if (null != lastReport
						&& StringUtils.isNotEmpty(lastReport.getOriginalData())) {
					String lastData = lastReport.getOriginalData();
					ChartData data = JSONObject.parseObject(lastData,
							ChartData.class);
					if (null != data) {
						Integer lastHighP = Integer.parseInt(data
								.getHighPressure());
						Integer lastLowP = Integer.parseInt(data
								.getLowPressure());

						if ((highP - lastHighP) >= 15
								|| (lowP - lastLowP) >= 15)
							tipType = TipType.WARNING;
					}

				}
			}

			/** 创建体检报告 */
			report = new MedicalReport(user, reportTime, resultReport,
					resultRmark, type, SMSContent, originalData);
			report.setRead(ReadType.WAIT);
			report.setTipType(tipType);

			report = medicalReportService.createMedicalReport(report);
		}
		/** 定位 */
		else if (SMSType.LOCATION == type) {

			String latitude = gatherData.getLatitude().trim();
			String longitude = gatherData.getLongitude().trim();
			String loaction = "";
			loaction = sklayApi.SearchLocation(latitude, longitude);

			String SMSContent = NLS.getMsg(smsTemplate, new Object[] {
					userName, DateUtils.getCurrentTime(), loaction });
			/** 保存采集到的原始数据 */
			String originalData = JSONObject.toJSONString(gatherData);
			/** 创建体检报告 */
			report = new MedicalReport(user, reportTime, loaction, null, type,
					SMSContent, originalData);
			report.setRead(ReadType.READED);
			report.setTipType(TipType.INFO);
			report = medicalReportService.createMedicalReport(report);
		} else {
			throw new SklayException("TODO getMedicalReport");
		}

		return report;
	}

	private static SwitchStatus filterPhysicalSetting(GlobalSetting setting) {

		if (null == setting)
			throw new SklayException(ErrorCode.FINF_NULL, null, "系统配置");

		if (SwitchStatus.CLOSE == setting.getPhysicalSMS())
			throw new SklayException(ErrorCode.CLOSED, null, "短信");

		/** 时间段 */
		if (SwitchStatus.PERIOD == setting.getPhysicalSMS()) {
			if (!(DateTimeUtil.isBetween(setting.getSendStartTime(),
					setting.getSendEndTime())))
				throw new SklayException(ErrorCode.NO_PERIOD);
		}
		return setting.getApiLogSwitch();
	}

	private static SwitchStatus filterSOSSetting(GlobalSetting setting) {

		if (null == setting)
			throw new SklayException(ErrorCode.FINF_NULL, null, "系统配置");

		if (SwitchStatus.CLOSE == setting.getSosSMS())
			throw new SklayException(ErrorCode.CLOSED, null, "定位业务");

		return setting.getApiLogSwitch();

	}

	private List<User> initUser(GatherData gatherData) {
		Level level = null;
		Date current = new Date();

		String sn = gatherData.getSimNo();
		/** 获取已绑定的审核通过的用户 */
		List<User> freeList = bindingService.getBindingUser(sn, level,
				AuditStatus.PASS, BindingMold.FREE);

		List<User> paidList = bindingService.getBindingUser(sn, level,
				AuditStatus.PASS, BindingMold.PAID);
		Set<User> needAutid = Sets.newHashSet();
		Set<Long> paidUserIds = Sets.newHashSet();
		Map<Long, User> userMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(paidList)) {
			for (User paid : paidList) {
				Long id = paid.getId();
				paidUserIds.add(id);
				userMap.put(id, paid);
			}
			String key = Constants.DEVICE_MOLD_TIME;
			List<UserAttr> paidAttrs = attrService.findUserPaidAttrByKey(key,
					paidUserIds);
			if (CollectionUtils.isNotEmpty(paidAttrs)) {
				for (UserAttr paidAttr : paidAttrs) {
					String value = paidAttr.getValue();
					Long userId = paidAttr.getUserId();
					Mold mold = JSONObject.parseObject(value, Mold.class);
					if (null == mold) {
						needAutid.add(userMap.get(userId));
						continue;
					}

					/** TODO 开始时间 > */
					if (current.before(mold.getStartTime())) {
						continue;
					}
					if (mold.getEndTime().before(current)) {
						needAutid.add(userMap.get(userId));
						continue;
					}
					freeList.add(userMap.get(userId));
				}

				if (CollectionUtils.isNotEmpty(needAutid))
					bindingService.updateMoldStatus(AuditStatus.WAIT,
							needAutid, sn);
			}
		}

		return freeList;
	}
}
