package org.peter.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-service.xml", "classpath:spring-mvc.xml",
		"classpath:spring-xss.xml" })
@WebAppConfiguration
public class TestPractiseSecurityController extends TestSpringControllerBase {
	private static final Logger log = LoggerFactory.getLogger(TestPractiseSecurityController.class);

	@Autowired
	private PractiseSecurityController controller;

	public void testGetHtmlTag() throws Exception {
		String tag = "<script>alert()</script>";
		mvc.perform(get("/getHtmlTag").param("tag", tag)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetWithRestTemplate() {
		String URI = "http://localhost:8080/webapp/getJsonHtmlTag?tag=<script>alert()</script>";
		URI = "http://localhost:8080/webapp/getStringHtmlTag?tag=<script>alert()</script>";
		RestTemplate restTemplate = new RestTemplate();
		JsonResult result = restTemplate.getForObject(URI, JsonResult.class, "1");
		log.info("result = {}", result);
		Assert.assertEquals("0", result.getCode());
	}

	@Override
	protected BaseController getController() {
		return controller;
	}
}
