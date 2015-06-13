package org.peter.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.web.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

// 表示继承了SpringJUnit4ClassRunner类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-service.xml" })
public class TestUserService {
	private static final Logger log = LoggerFactory.getLogger(TestUserService.class);
	@Resource
	private UserService userService = null;

	@Test
	public void test1() {
		User user = userService.getUserById(1);
		log.info(JSON.toJSONString(user));
	}

	// @Test
	public void testUpdateUser() {
		long timestamp = System.currentTimeMillis();
		User user = userService.getUserById(2);
		user.setFirstName("TestFirstName" + timestamp);
		user.setLastName("TestLastName" + timestamp);
		userService.updateUser(user);
		User updatedUser = userService.getUserById(2);
		Assert.assertEquals(user.getFirstName(), updatedUser.getFirstName());
		Assert.assertEquals(user.getLastName(), updatedUser.getLastName());
	}

	// @Test
	public void testInsertUser() {
		User user = new User();
		user.setEmail("test_email_" + System.currentTimeMillis() + "@gmail.com");
		user.setPassword("secret");
		user.setFirstName("TestFirstName");
		user.setLastName("TestLastName");
		user.setCreationDate(new Date());

		userService.insertUser(user);
		Assert.assertTrue(user.getUserId() != 0);
		User createdUser = userService.getUserById(user.getUserId());
		Assert.assertNotNull(createdUser);
		Assert.assertEquals(user.getEmail(), createdUser.getEmail());
		Assert.assertEquals(user.getPassword(), createdUser.getPassword());
		Assert.assertEquals(user.getFirstName(), createdUser.getFirstName());
		Assert.assertEquals(user.getLastName(), createdUser.getLastName());
	}

	/**
	 * 事务处理必须抛出异常，Spring才会帮助事务回滚
	 * 注意@Transactional只能被应用到public方法上，对于其它非public的方法，
	 * 如果标记了@Transactional也不会报错,但方法没有事务功能。
	 */
	@Test(expected = RuntimeException.class)
	public void testTransaction() {
		int countBefore = userService.getCount();
		log.info("countBefore = {}", countBefore);
		List<User> users = new ArrayList<User>();
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setPassword(i + "111111");
			user.setFirstName("测试" + i);
			user.setLastName("TestLastName");
			user.setCreationDate(new Date());
			users.add(user);
		}
		this.userService.insertUserTestTransaction(users);
		int countAfter = userService.getCount();
		Assert.assertEquals(countBefore, countAfter);
	}
}
