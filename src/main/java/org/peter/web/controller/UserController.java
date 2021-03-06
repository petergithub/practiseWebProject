package org.peter.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.peter.web.domain.User;
import org.peter.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
//	@Resource
	@Autowired
	private UserService userService;

	@RequestMapping("/showUser")
	public String showUser(Integer userId, HttpServletRequest request, Model model) {
//		int userId = Integer.parseInt(request.getParameter("userId"));
		log.info("Enter showUser(userId {})", userId);
		User user = userService.getUserById(userId);
		log.info("user {}", user);
		model.addAttribute("user", user);
		return "showUser";
	}

	@RequestMapping("/addUser")
	public String addUser(User user, HttpServletRequest request, Model model) {
		log.info("Enter addUser(user {})", user);
		user = new User("email@gmail.com", "pwd", "test😁", "last");
		try {
			userService.insertUser(user);
		} catch (Exception e) {
			log.error("insert user", e);
		}
		log.info("user {}", user);
		model.addAttribute("user", user);
		return "addUser";
	}
}
