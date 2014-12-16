package com.hcm.base.task.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hcm.base.support.RetObj;
import com.hcm.base.support.spring.SpringUtils;
import com.hcm.base.task.domain.ScheduleJob;
import com.hcm.base.task.service.JobTaskService;

@Controller
@RequestMapping(value = "/task")
public class JobTaskController {
	
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private JobTaskService taskService;
	
	@RequestMapping(value = "/taskList", method = RequestMethod.GET)
	public String taskList(Model model) {
		List<ScheduleJob> taskList = taskService.getAllTask();
		model.addAttribute("taskList", taskList);
		return "base/task/taskList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RetObj taskList(Model model, ScheduleJob scheduleJob) {
		RetObj retObj = new RetObj();
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		try {
			Object obj = null;
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
			} else {
				Class<?> clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
			if (obj == null) {
				retObj.setMsg("未找到目标类！");
				return retObj;
			} 
			Class<? extends Object> clazz = obj.getClass();
			Method method = clazz.getMethod(scheduleJob.getMethodName());
			if (method == null) {
				retObj.setMsg("未找到目标方法！");
				return retObj;
			}
			taskService.addTask(scheduleJob);
		} catch (Exception e) {
			log.error("add error" + e);
			retObj.setMsg("" + e);
			return retObj;
		}

		retObj.setFlag(true);
		return retObj;
	}

	@RequestMapping(value = "/changeJobStatus", method = RequestMethod.POST)
	@ResponseBody
	public RetObj changeJobStatus(Long jobId, String cmd) {
		RetObj retObj = new RetObj();
		try {
			taskService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			retObj.setMsg("任务状态改变失败！");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}

	@RequestMapping(value = "/updateCron", method = RequestMethod.POST)
	@ResponseBody
	public RetObj updateCron(HttpServletRequest request, Long jobId, String cron) {
		RetObj retObj = new RetObj();
		try {
			CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		try {
			taskService.updateCron(jobId, cron);
		} catch (SchedulerException e) {
			retObj.setMsg("cron更新失败！");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}
}
