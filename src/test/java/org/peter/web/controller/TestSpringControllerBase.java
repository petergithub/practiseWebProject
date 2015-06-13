package org.peter.web.controller;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class TestSpringControllerBase {
	protected MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    protected static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    protected static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    protected ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

//        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
}
