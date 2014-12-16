package test;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.druid.util.StringUtils;

public class JobFactory implements Job {

	public final static Logger logger = Logger.getLogger(JobFactory.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String className = (String) dataMap.get("className");
		String methodName = (String) dataMap.get("methodName");
		invokMethod(className, methodName);
	}

	/**
	 * 通过反射执行目标类的相应方法
	 * 
	 * @param className
	 * @param methodName
	 */
	private void invokMethod(String className, String methodName) {
		
		if (StringUtils.isEmpty(className)) {
			throw new IllegalArgumentException("className cannot be set to null!");
		}
		
		Class<?> clazz = null;
		Object object = null;
		Method method = null;
		
		try {
			clazz = Class.forName(className);
			if (clazz != null) {
				object = clazz.newInstance();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		try {
			if (methodName == null) {
				method = clazz.getDeclaredMethod("execute");
			} else {
				method = clazz.getDeclaredMethod(methodName);
			}
			if (object != null && method != null) {
					method.invoke(object);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
