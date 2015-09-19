package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
@WebAppConfiguration
public class TestPractiseDateBinderController extends TestSpringControllerBase {
	private static final Logger log = LoggerFactory
			.getLogger(TestPractiseDateBinderController.class);

	private MockMvc mvc;

	// @Autowired
	private PractiseDateBinderController controller = new PractiseDateBinderController();

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetBean() throws Exception {
		Date date = new DateTime().toDate();
		Bean bean = new Bean(1l, "name1", "value1", date);
		String json = JSON.toJSONString(bean);
		log.info("json = {}", json);
		String creationDate = date.toString();
		log.info("creationDate = {}", creationDate);
		creationDate = "20150919 12:13:14";
		mvc.perform(
				get("/getBean").param("id", "1").param("name", "name1").param("value", "value1")
						.param("creationDate", creationDate)).andExpect(
				MockMvcResultMatchers.status().isOk());
		// .andExpect(content().contentType("application/json;charset=UTF-8"));
	}
}
