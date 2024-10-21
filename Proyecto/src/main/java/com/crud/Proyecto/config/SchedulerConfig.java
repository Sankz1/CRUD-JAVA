package com.crud.Proyecto.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class SchedulerConfig implements SchedulingConfigurer{
	private final int POOL_SIZE =10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
	ThreadPoolTaskScheduler threadPoolTask = new ThreadPoolTaskScheduler();
	threadPoolTask.setPoolSize(POOL_SIZE);
	threadPoolTask.setThreadNamePrefix("My-Scheduled-Task-Pool-");
	threadPoolTask.initialize();
	
	taskRegistrar.setTaskScheduler(threadPoolTask);
		
	}
	
	

}
