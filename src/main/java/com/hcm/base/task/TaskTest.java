package com.hcm.base.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TaskTest {
	public final Logger log = Logger.getLogger(this.getClass());

	public void run() {
		for (int i = 0; i < 1; i++) {
			log.info(i + " run......................................" + (new Date()));
		}

	}

	public void run1() {
		for (int i = 0; i < 1; i++) {
			log.info(i + " run1......................................" + (new Date()));
		}
	}

	public void run3() {
		for (int i = 0; i < 1; i++) {
			log.info(i + " run3......................................" + (new Date()));
		}
	}
}
