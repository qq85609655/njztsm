package com.sklay.task;

import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.sklay.core.util.DateTimeUtil;

public class ScheduleInfoAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ScheduleInfoAction.class);
	private Scheduler scheduler;
	
	
	private ScheduleInfoManager scheduleInfoManager;

	// 设值注入，通过setter方法传入被调用者的实例scheduleInfoManager
	public void setScheduleInfoManager(ScheduleInfoManager scheduleInfoManager)
			throws ParseException {
		this.scheduleInfoManager = scheduleInfoManager;
	}
	
	
	// 设值注入，通过setter方法传入被调用者的实例scheduler
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void simpleJobTest() {
		System.out.println("uh oh, Job is scheduled !'" + "' Success...");
	}

	private void reScheduleJob() throws SchedulerException {
		// 运行时可通过动态注入的scheduler得到trigger
		CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(
				"cronTrigger", Scheduler.DEFAULT_GROUP);
//		 String dbCronExpression = getCronExpressionFromDB();
		 String originConExpression = trigger.getCronExpression();
		 scheduler.getJobListenerNames() ;
		 scheduler.getTriggerListenerNames() ;
		 trigger.addTriggerListener("test001") ;trigger.getTriggerListenerNames() ;
		 trigger.setEndTime(DateTimeUtil.getStringToDate("2013-14-11 00:00:00")) ;
		// 判断从DB中取得的任务时间(dbCronExpression)和现在的quartz线程中的任务时间(originConExpression)是否相等
		// 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
//		 if(!originConExpression.equalsIgnoreCase(dbCronExpression)){
//		 trigger.setCronExpression(dbCronExpression);
//		 scheduler.rescheduleJob("cronTrigger", Scheduler.DEFAULT_GROUP,
//		 trigger);
		// 　　　　　　　　}
		// 下面是具体的job内容，可自行设置
		// executeJobDetail();
		 simpleJobTest() ;
	}
}
