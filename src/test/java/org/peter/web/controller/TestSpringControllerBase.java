package org.peter.web.controller;

import java.nio.charset.Charset;

import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public abstract class TestSpringControllerBase {
	protected MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    protected static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    protected static final String VIEW_RESOLVER_SUFFIX = ".jsp";
	protected MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.standaloneSetup(getController()).build();
	}

    protected ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

//        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
    
    protected abstract BaseController getController();
}
