package org.peter.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
@RequestMapping
public class PracticeRedirectController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PracticeRedirectController.class);

    @RequestMapping(value = "forward")
    public String forward(final HttpServletRequest request, Model model) {
        log.info("Enter forward(forward)");
        model.addAttribute("value", "value");
        // start without /
        return "index.jsp";
    }

    @RequestMapping(value = "/forward/second")
    public String forwardSecond(final HttpServletRequest request) {
        log.info("Enter forward(/forward/second)");
        // start with /
        return "/index.jsp";
    }

    // http://localhost:8080/webapp/forwardUnderWebInf
    @RequestMapping(value = "/forwardUnderWebInf")
    public String forwardUnderWebInf(final HttpServletRequest request) {
        log.info("Enter forward(forwardUnderWebInf)");
        // spring-mvc.xml
        /*
         * <!-- 定义跳转的文件的前后缀 ，视图模式配置 --> <bean
         * class="org.springframework.web.servlet.view.InternalResourceViewResolver"
         * > <!-- 这里的配置我的理解是自动给后面action的方法return的字符串加上前缀和后缀，变成一个 可用的url地址 -->
         * <property name="prefix" value="/WEB-INF/jsp/" /> <property
         * name="suffix" value=".jsp" /> </bean>
         */
        return "indexUnderWebInf";
    }

    @RequestMapping(value = "/redirect")
    public void redirect(final HttpServletRequest request, HttpServletResponse response) {
        String redirectUrl = "http://www.baidu.com";
        log.info("Enter redirect() to {}", redirectUrl);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Exception in PracticeController.redirect()", e);
        }
    }

    @RequestMapping(value = "/redirectView")
    public String redirectView(final HttpServletRequest request) {
        String redirectUrl = "http://www.baidu.com";
        log.info("Enter redirectView() to {}", redirectUrl);
        return createRedirectViewPath(redirectUrl);
    }

    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        log.info("redirectViewPath = {}", redirectViewPath);
        return redirectViewPath.toString();
    }
}