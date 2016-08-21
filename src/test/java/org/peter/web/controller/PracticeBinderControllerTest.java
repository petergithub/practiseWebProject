package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.Bean;
import org.peter.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@WebAppConfiguration
public class PracticeBinderControllerTest extends SpringControllerTestBase {
	private static final Logger log = LoggerFactory.getLogger(PracticeBinderControllerTest.class);

	@Autowired
	private PracticeBinderController controller;

	@Test
	public void testGetDate() throws Exception {
		DateTime date = new DateTime();
		Bean bean = new Bean(1l, "name1", "value1", date.toDate());
		String json = JSON.toJSONString(bean);
		log.info("json = {}", json);
		String creationDate = date.toString(Constants.dateFormat);
		mvc.perform(
				get("/getDate").param("id", "1").param("name", "name1").param("value", "value1")
						.param("creationDate", creationDate)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	public void testSameNameBinder() throws Exception {
		mvc.perform(
				post("/sameNameBinder").param("bean.id", "1").param("c.id", "2")
						.param("value", "value").param("name", "name").param("test", "ta"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	public void testSameName() throws Exception {
		mvc.perform(
				post("/sameName").param("id", "1").param("value", "value").param("name", "name")
						.param("test", "ta")).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Override
	protected BaseController getController() {
		return controller;
	}
}
