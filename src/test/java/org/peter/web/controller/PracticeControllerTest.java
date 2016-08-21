package org.peter.web.controller;

import static org.peter.util.Constants.ResponseMsg_Success;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.Bean;
import org.peter.bean.BeanImplList;
import org.peter.bean.BeanListWrapper;
import org.peter.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
// detects "WacTests-context.xml" in same package
// or static nested @Configuration class
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
// defaults to "file:src/main/webapp"
@WebAppConfiguration
public class PracticeControllerTest extends SpringControllerTestBase {
	private static final Logger log = LoggerFactory.getLogger(PracticeControllerTest.class);

	private MockMvc mvc;

	@Autowired
	private PracticeController controller;

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
		mvc.perform(get("/getBeanArray").param("id", bean.toString())).andExpect(
				MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetBean() throws Exception {
		DateTime date = new DateTime();
		Bean bean = new Bean(1l, "name1", "value1", date.toDate());
		String json = JSON.toJSONString(bean);
		// {"creationDate":1444640831362,"id":1,"name":"name1","value":"value1"}
		log.info("json = {}", json);
		String creationDate = date.toString(Constants.dateFormat);
		mvc.perform(
				get("/getBean").param("id", "1").param("name", " ").param("value", "value1")
						.param("creationDate", creationDate))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	public void testGetPathVariable() throws Exception {
		mvc.perform(get("/getPathVariable/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				// .andExpect(content().contentType(MediaType.APPLICATION_JSON));
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	public void testGetBeanCondition() throws Exception {
		String condition = "{id:1,name:\"name1\",value:\"value1\"}";
		String date = new DateTime().toString(Constants.dateFormat);
		// date = "{\"date\":" + date + "}";
		log.info("date={}, condition = {}", date, condition);
		mvc.perform(get("/getBeanCondition").param("condition", condition).param("date", date))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("data.id", Matchers.is(1)))
				.andExpect(jsonPath("data.name", Matchers.is("name1")));

		String encode = URLEncoder.encode("{id:1,name:\"name1\",value:\"value1\"}", "UTF-8");
		log.info("encode = {}", encode);
		// mvc.perform(
		// get("/getBeanCondition?condition=" + condition).param(
		// "date", date)).andExpect(MockMvcResultMatchers.status().isOk())
		// .andDo(MockMvcResultHandlers.print())
		// .andExpect(jsonPath("data.id", Matchers.is(1)))
		// .andExpect(jsonPath("data.name", Matchers.is("name1")));

		String conditionInCorrect = "{\"condition\":{id:1,name:\"name1\",value:\"value1\"}}";
		// {}
		net.minidev.json.JSONObject emptyJson = new net.minidev.json.JSONObject();
		mvc.perform(
				get("/getBeanCondition").param("condition", conditionInCorrect).param("date", date))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("data", Matchers.is(emptyJson)));
	}

	public void testGetBeanStr() throws Exception {
		String json = "\"beans\":[{id:1,name:\"name1\",value:\"value1\"}]";
		log.info("json = {}", json);
		mvc.perform(post("/getBeanStr").param("beans", json).param("test", "ta")).andExpect(
				MockMvcResultMatchers.status().isOk());
	}

	public void testGetBeanImplList() throws Exception {
		BeanImplList beans = new BeanImplList();
		beans.add(new Bean(1l, "name1", "value1"));

		// json = [{"id":1,"name":"name1","value":"value1"}]
		String json = JSONArray.toJSONString(beans);

		log.info("json = {}", json);
		mvc.perform(
				post("/getBeanImplList").param("beans", json)
						.contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(
				MockMvcResultMatchers.status().isOk());
	}

	public void testGetBeanListWrapper() throws Exception {
		List<Bean> beanList = new ArrayList<>();
		beanList.add(new Bean(null, "name1", "value1"));
		BeanListWrapper beans = new BeanListWrapper(beanList);

		// json = {"beans":[{"id":1,"name":"name1","value":"value1"}]}
		String json = JSONArray.toJSONString(beans);
		json = JSON.toJSONString(beans);
		log.info("json = {}", json);
		// mvc.perform(post("/getBeanListWrapper").contentType(MediaType.APPLICATION_JSON).content(json))
		// .andExpect(MockMvcResultMatchers.status().isOk());

		mvc.perform(post("/getBeanListWrapper").param("beans", json)).andExpect(
				MockMvcResultMatchers.status().isOk());
	}

	public void testGetBeanList() throws Exception {
		List<Bean> beanList = new ArrayList<>();
		beanList.add(new Bean(null, "name1", "value1"));

		// json = "[{\"name\":\"name1\",\"value\":\"value1\"}]";
		String json = JSON.toJSONString(beanList);
		log.info("json = {}", json);
		mvc.perform(post("/getBeanList").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	public void testGetCriteria() throws Exception {
		mvc.perform(
				get("/getCriteria").param("id", "1").param("value", "value").param("name", " ")
						.param("description", " ").param("test", "ta"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("msg", Matchers.is(ResponseMsg_Success)));
	}

	@Test
	public void testGetBeanCriteria() throws Exception {
		mvc.perform(
				post("/getBeanCriteria").param("id", "1").param("value", "value").param("name", "")
						.param("test", "ta")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	public void testImportFile() throws Exception {
		FileInputStream file = new FileInputStream(new File(
				"/home/pu/sp/doing/BatchImportCardsTemplate.xlsx"));
		MockMultipartFile multiFile = new MockMultipartFile("file",
				"BatchImportCardsTemplate.xlsx", "multipart/form-data", file);
		mvc.perform(MockMvcRequestBuilders.fileUpload("/importFile").file(multiFile))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	public void testExportFile() throws Exception {
		mvc.perform(post("/exportFile")).andExpect(status().is2xxSuccessful())
				.andDo(MockMvcResultHandlers.print());
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

	@Override
	protected BaseController getController() {
		return controller;
	}

}
