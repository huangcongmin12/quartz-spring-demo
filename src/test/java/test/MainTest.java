package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcm.base.task.domain.ScheduleJob;
import com.hcm.base.task.service.JobTaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class MainTest {

	@Autowired
	private JobTaskService jobTaskService;

	@Test
	public void testAdd() {
		List<ScheduleJob> jobList = jobTaskService.getAllTask();
		System.out.println("###########" + jobList.size());
		try {
			if (jobList != null && jobList.size() > 0) {
				for (ScheduleJob job : jobList) {
					jobTaskService.addJob(job);
				}
			}
		} catch (Exception e) {
		}
		
		try {
			// sleep for 30 seconds
			Thread.sleep(30L * 1000L);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void test() {
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(2);
		
		List<Integer> list2 = new ArrayList<Integer>();
		
		list2.addAll(list1);
		list2.addAll(list1);
		
		for (Integer i : list2) {
			System.out.println("#########" + i);
		}
	}
}
