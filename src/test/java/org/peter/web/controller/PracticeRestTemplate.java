package org.peter.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.peter.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

/**
 * http://howtodoinjava.com/2015/02/20/spring-restful-client-resttemplate-example/
 * @author Shang Pu
 * @version Date：Nov 16, 2015 7:23:42 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@WebAppConfiguration
public class PracticeRestTemplate {
	private static final Logger log = LoggerFactory.getLogger(PracticeRestTemplate.class);

	@Test
	public void testGetBean() throws Exception {
		String uri = "http://localhost:8080/webapp/getBean";

		DateTime date = new DateTime();
		Bean bean = new Bean(1l, "name1", "value1", date.toDate());
		String json = JSON.toJSONString(bean);
		// {"creationDate":1444640831362,"id":1,"name":"name1","value":"value1"}
		log.info("json = {}", json);

		uri += "?id={id}&name={name}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		params.put("name", "name1");

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class, params);
		//{"code":"0","data":{"creationDate":null,"id":0,"name":"","value":""},"msg":"Success"}
		log.info("result[{}]", result);
	}

}
