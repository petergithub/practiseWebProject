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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

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

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@WebAppConfiguration
public class PracticeGetPostControllerTest extends SpringControllerTestBase {
	private static final Logger log = LoggerFactory.getLogger(PracticeGetPostControllerTest.class);

	private MockMvc mvc;

	@Autowired
	private PracticeGetPostController controller;

	@Before
	public void setUp() {
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		// Mockito.reset(todoServiceMock);

		this.mvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver())
				.build();
	}
	
    @Test
    public void getPost() throws Exception {
        DateTime date = new DateTime();
        Bean bean = new Bean(1l, "name1", "value1", date.toDate());
        String json = JSON.toJSONString(bean);
        // {"creationDate":1444640831362,"id":1,"name":"name1","value":"value1"}
        log.info("json = {}", json);
        String creationDate = date.toString(Constants.dateFormat);
        mvc.perform(
                get("/getPost").param("id", "1").param("name", " ").param("value", "value1")
                        .param("creationDate", creationDate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getPostPost() throws Exception {
        DateTime date = new DateTime();
        Bean bean = new Bean(1l, "name1", "value1", date.toDate());
        String json = JSON.toJSONString(bean);
        // {"creationDate":1444640831362,"id":1,"name":"name1","value":"value1"}
        log.info("json = {}", json);
        String creationDate = date.toString(Constants.dateFormat);
        mvc.perform(
                post("/getPost").param("id", "1").param("name", " ").param("value", "value1")
                        .param("creationDate", creationDate))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }



	@Override
	protected BaseController getController() {
		return controller;
	}

}
