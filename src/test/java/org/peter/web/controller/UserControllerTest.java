package org.peter.web.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.peter.web.domain.User;
import org.peter.web.service.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@WebAppConfiguration
public class UserControllerTest extends TestSpringControllerBase {
	private MockMvc mockMvc;

	@InjectMocks
	UserController userController;

	@Mock
	private UserService userServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setViewResolvers(viewResolver()).build();
	}

	@Test
	public void testShowUser() throws Exception {
		Mockito.when(userServiceMock.getUserById(1)).thenReturn(new User());
		mockMvc.perform(get("/user/showUser?userId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("showUser"))
				.andExpect(model().attribute("user", is(instanceOf(User.class))))
				.andExpect(forwardedUrl("/WEB-INF/jsp/showUser.jsp"));

		mockMvc.perform(post("/user/showUser").param("userId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testShowUserNotFound() throws Exception {
		mockMvc.perform(get("/showUser")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
