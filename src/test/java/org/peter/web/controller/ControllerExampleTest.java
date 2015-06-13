package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
// detects "WacTests-context.xml" in same package
// or static nested @Configuration class
@ContextConfiguration(locations = { "classpath:spring*.xml" })
// defaults to "file:src/main/webapp"
@WebAppConfiguration
public class ControllerExampleTest extends TestSpringControllerBase {
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		// Mockito.reset(todoServiceMock);

		this.mockMvc = MockMvcBuilders.standaloneSetup(new ControllerExample())
                .setViewResolvers(viewResolver()).build();
	}

	// result =
	// {"order":[{"orderNums":"1","status":"status1"},{"orderNums":"2","status":"status2"}],"response":"statusSucess"}
	// http://goessner.net/articles/JsonPath/
	@Test
	public void testGetJson() throws Exception {
		mockMvc.perform(get("/getJson?orderNums=['1','2']"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		mockMvc.perform(post("/getJson").param("orderNums", "['1','2']"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.order", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.order[0].orderNums", Matchers.is("1")))
				.andExpect(jsonPath("$.order[0].status", Matchers.is("status1")))
				.andExpect(jsonPath("$.response", Matchers.is("statusSucess")))
				.andDo(MockMvcResultHandlers.print());
	}

	public void testRedirect() throws Exception {
		mockMvc.perform(get("/redirect"))
		.andExpect(MockMvcResultMatchers.status().isFound())
		.andExpect(view().name("redirect:redirectPath"))
		.andExpect(redirectedUrl("redirectPath"));
	}

	public void testForwardUnderWebInf() throws Exception {
		mockMvc.perform(get("/forwardUnderWebInf"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("indexUnderWebInf"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/indexUnderWebInf.jsp"));
	}

	public void testForward() throws Exception {
		mockMvc.perform(get("/forward"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("index.jsp"))
				.andExpect(model().attribute("value", Matchers.is("value")));
		
		mockMvc.perform(get("/forward/second"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("/index.jsp"));
	}

	public void testHttpStatusCode404() throws Exception {
		mockMvc.perform(get("/api")).andExpect(status().isNotFound());
	}

}
