package com.sklay.task;

import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.sklay.service.TaskManager;

public class ScheduleInfoManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduleInfoManager.class);

	private Scheduler scheduler;

	private ScheduleInfoManager scheduleInfoManager;

	@Autowired
	private TaskManager taskManager;

	// 设值注入，通过setter方法传入被调用者的实例scheduleInfoManager
	public void setScheduleInfoManager(ScheduleInfoManager scheduleInfoManager) {
		this.scheduleInfoManager = scheduleInfoManager;
	}

	public void simpleJobTest() {
		System.out.println("uh oh, Job is scheduled !'" + "' Success...");
	}

	// 设值注入，通过setter方法传入被调用者的实例scheduler
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	private void reScheduleJob() throws SchedulerException, ParseException {
		// 运行时可通过动态注入的scheduler得到trigger，注意采用这种注入方式在有的项目中会有问题，如果遇到注入问题，可以采取在运行方法时候，获得bean来避免错误发生。
		CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(
				"cronTrigger", Scheduler.DEFAULT_GROUP);
		String originConExpression = trigger.getCronExpression();
		// String dbCronExpression = "0 30 0 * * ?";
		// String defaultCronExpression = "0 10 0 * * ?";
		//
		// if (originConExpression.equalsIgnoreCase("0/10 * * * * ?")) {
		// trigger.setCronExpression(defaultCronExpression);
		// scheduler.rescheduleJob("cronTrigger", Scheduler.DEFAULT_GROUP,
		// trigger);
		// } else if
		// (originConExpression.equalsIgnoreCase(defaultCronExpression)) {
		// trigger.setCronExpression(dbCronExpression);
		// scheduler.rescheduleJob("cronTrigger", Scheduler.DEFAULT_GROUP,
		// trigger);
		// } else if (originConExpression.equalsIgnoreCase(dbCronExpression)) {
		// trigger.setCronExpression(defaultCronExpression);
		// scheduler.rescheduleJob("cronTrigger", Scheduler.DEFAULT_GROUP,
		// trigger);
		// }
		taskManager.doDayJob();
		taskManager.doFestivalJob();
		LOGGER.debug("do job");
		// 下面是具体的job内容，可自行设置
		// executeJobDetail();
	}
}