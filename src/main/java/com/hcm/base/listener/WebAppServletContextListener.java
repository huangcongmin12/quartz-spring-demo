package com.hcm.base.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hcm.base.task.domain.ScheduleJob;
import com.hcm.base.task.service.JobTaskService;

public class WebAppServletContextListener implements ServletContextListener {

	private static Log logger = LogFactory.getLog(WebAppServletContextListener.class);

	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext servletContext = sce.getServletContext();
		
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		JobTaskService jobTaskService = (JobTaskService) ac.getBean("jobTaskService");
		
		// 应用启动时，若Job状态为运行状态 ：1，则将Job启动。
		// JobTaskService jobTaskService = (JobTaskService) SpringUtils.getBean("jobTaskService");
		
		List<ScheduleJob> jobList = jobTaskService.getAllTask();
		try {
			if (jobList != null && jobList.size() > 0) {
				for (ScheduleJob job : jobList) {
					jobTaskService.addJob(job);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);	
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing...
	}

}
