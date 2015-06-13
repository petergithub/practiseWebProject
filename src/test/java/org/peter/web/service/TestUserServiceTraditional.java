package org.peter.web.service;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.peter.web.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

public class TestUserServiceTraditional {
	private static Logger log = Logger.getLogger(TestUserServiceTraditional.class);
	private ApplicationContext context = null;
	private UserService userService = null;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("spring-service.xml");
		userService = (UserService) context.getBean("userService");
	}

	@Test
	public void test1() {
		User user = userService.getUserById(1);
		log.info(JSON.toJSONString(user));
	}
}
