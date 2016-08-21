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
import org.peter.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@WebAppConfiguration
public class UserControllerTest {
	private MockMvc mvc;

	// @InjectMocks
	@Autowired
	private UserController userController;

	// @Mock
	// private UserService userServiceMock;

	@Before
	public void setUp() {
		// MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver())
				.build();
	}

	@Test
	public void testShowUser() throws Exception {
		// Mockito.when(userServiceMock.getUserById(1)).thenReturn(new User());
		mvc.perform(get("/user/showUser?userId=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("showUser"))
				.andExpect(model().attribute("user", is(instanceOf(User.class))))
				.andExpect(forwardedUrl("/WEB-INF/jsp/showUser.jsp"));

		mvc.perform(post("/user/showUser").param("userId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testShowUserNotFound() throws Exception {
		mvc.perform(get("/showUser")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}


    protected static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    protected static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    protected ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

//        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }

}
