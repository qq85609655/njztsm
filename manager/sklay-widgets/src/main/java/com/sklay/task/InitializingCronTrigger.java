package com.sklay.task;

import java.io.Serializable;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.CronTriggerBean;

public class InitializingCronTrigger extends CronTriggerBean implements
		Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4949578146894204477L;

	@Value("${sms.cronExpression}")
	private String smsCronExpression;
	
	private ScheduleInfoManager scheduleInfoManager;

	// 设值注入，通过setter方法传入被调用者的实例scheduleInfoManager
	public void setScheduleInfoManager(ScheduleInfoManager scheduleInfoManager)
			throws ParseException {
		this.scheduleInfoManager = scheduleInfoManager;
		super.setCronExpression(smsCronExpression);
		super.getTriggerListenerNames() ;
	}
}
