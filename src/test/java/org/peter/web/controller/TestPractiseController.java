package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.Bean;
import org.peter.bean.BeanImplList;
import org.peter.bean.BeanList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
// detects "WacTests-context.xml" in same package
// or static nested @Configuration class
@ContextConfiguration(locations = { "classpath:spring*.xml" })
// defaults to "file:src/main/webapp"
@WebAppConfiguration
public class TestPractiseController extends TestSpringControllerBase {
	private static final Logger log = LoggerFactory.getLogger(TestPractiseController.class);

	private MockMvc mvc;

	// @Autowired
	private PractiseController controller = new PractiseController();

	@Before
	public void setUp() {
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		// Mockito.reset(todoServiceMock);

		this.mvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver())
				.build();
	}

	// NestedServletException
	public void testGetBeanArray() throws Exception {

		Bean bean = new Bean(1l, "name1", "value1");

		mvc.perform(get("/getBeanArray")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetBean() throws Exception {
		Date date = new DateTime().toDate();
		Bean bean = new Bean(1l, "name1", "value1", date);
		String json = JSON.toJSONString(bean); 
		log.info("json = {}", json);
		String creationDate = date.toString();
		mvc.perform(get("/getBean")
				.param("id", "1")
				.param("name", "name1")
				.param("value", "value1")
				.param("creationDate", creationDate))
				.andExpect(
				MockMvcResultMatchers.status().isOk());
		// .andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	public void testGetBeanStr() throws Exception {
		String json = "\"beans\":[{id:1,name:\"name1\",value:\"value1\"}]";
		log.info("json = {}", json);
		mvc.perform(post("/getBeanStr").param("beans", json).param("test", "ta")).andExpect(
				MockMvcResultMatchers.status().isOk());
		// .andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	public void testGetBeanImplList() throws Exception {
		BeanImplList beans = new BeanImplList();
		beans.add(new Bean(1l, "name1", "value1"));

		// json = [{"id":1,"name":"name1","value":"value1"}]
		String json = JSONArray.toJSONString(beans);

		log.info("json = {}", json);
		mvc.perform(
				post("/getBeanImplList").param("beans", json).contentType(APPLICATION_JSON_UTF8)
						.content(json)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	public void testGetBeanList() throws Exception {
		List<Bean> beanList = new ArrayList<>();
		beanList.add(new Bean(null, "name1", "value1"));
		BeanList beans = new BeanList(beanList);

		// json = {"beans":[{"id":1,"name":"name1","value":"value1"}]}
		String json = JSONArray.toJSONString(beans);
		log.info("json = {}", json);
		mvc.perform(post("/getBeanList").contentType(APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	public void testGetBeanCriteria() throws Exception {
		mvc.perform(
				post("/getBeanCriteria").param("id", "1").param("value", "value")
						.param("name", "name").param("test", "ta")).andExpect(
				MockMvcResultMatchers.status().isOk());
	}

	// result =
	// {"order":[{"orderNums":"1","status":"status1"},{"orderNums":"2","status":"status2"}],"response":"statusSucess"}
	// http://goessner.net/articles/JsonPath/
	public void testGetJson() throws Exception {
		mvc.perform(get("/getJson?orderNums=['1','2']")).andExpect(
				MockMvcResultMatchers.status().isOk());

		mvc.perform(post("/getJson").param("orderNums", "['1','2']"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.order", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.order[0].orderNums", Matchers.is("1")))
				.andExpect(jsonPath("$.order[0].status", Matchers.is("status1")))
				.andExpect(jsonPath("$.response", Matchers.is("statusSucess")))
				.andDo(MockMvcResultHandlers.print());
	}

	public void testRedirect() throws Exception {
		mvc.perform(get("/redirect")).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(view().name("redirect:redirectPath"))
				.andExpect(redirectedUrl("redirectPath"));
	}

	public void testForwardUnderWebInf() throws Exception {
		mvc.perform(get("/forwardUnderWebInf")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("indexUnderWebInf"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/indexUnderWebInf.jsp"));
	}

	public void testForward() throws Exception {
		mvc.perform(get("/forward")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("index.jsp"))
				.andExpect(model().attribute("value", Matchers.is("value")));

		mvc.perform(get("/forward/second")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(view().name("/index.jsp"));
	}

	public void testHttpStatusCode404() throws Exception {
		mvc.perform(get("/api")).andExpect(status().isNotFound());
	}

}
