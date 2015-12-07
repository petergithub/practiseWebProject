package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
@WebAppConfiguration
public class ControllerExampleWacTest extends TestSpringControllerBase {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test() {
		
	}
	// result =
	// {"order":[{"orderNums":"1","status":"status1"},{"orderNums":"2","status":"status2"}],"response":"statusSucess"}
	//TODO fix the test
	public void testGetJson() throws Exception {
		mockMvc.perform(get("/getJson?orderNums=['1','2']&id=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.order", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.response", Matchers.is("statusSucess")));
	}

	@Override
	protected BaseController getController() {
		// TODO Auto-generated method stub
		return null;
	}
}
