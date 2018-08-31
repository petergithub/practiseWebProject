package org.peter.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.peter.bean.Bean;
import org.peter.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping
public class PracticeGetPostController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PracticeGetPostController.class);

    @RequestMapping(value = "/test0", method = { RequestMethod.POST, RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String test0(Bean bean, final HttpServletRequest request) {
        log.debug("Enter getPost bean: {} bean: {}", bean, bean);

        return LogUtil.buildResult(JSON.toJSONString(bean), request, log);
    }

    /**
     * * 1.直接把表单的参数写在Controller相应的方法的形参中
     * 适用于get方式提交，不适用于post方式提交。若"Content-Type"="application/x-www-form-urlencoded",可用post提交
     * 
     * curl -X GET "http://localhost:8080/practiceWebProject/test1?name=name&password=get"
     * 
     * curl -X POST "http://localhost:8080/practiceWebProject/test1?name=name&password=post"
     */
    @RequestMapping("/test1")
    @ResponseBody
    public String test1(String name, String password) {
        System.out.println("name is:" + name);
        System.out.println("password is:" + password);
        return "demo/index";
    }

    /**
     * * 2、通过HttpServletRequest接收 post方式和get方式都可以。
     * 
     * @param request
     * @return
     */
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        System.out.println("name is:" + name);
        System.out.println("password is:" + password);
        return "demo/index";
    }

    /** 
     * 3、通过一个bean来接收 ,post方式和get方式都可以
     **/
    @RequestMapping("/test3")
    @ResponseBody
    public String test3(Bean user) {
        System.out.println("name is:" + user.getId());
        return "demo/index";
    }

    /** 
     * 使用@ModelAttribute注解获取POST请求的FORM表单数据
     * 
     * curl -v -d "name=n1&id=1" "http://localhost:8080/practiceWebProject/test4?name=name&password=get"
     */
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ResponseBody
    public String test4(@ModelAttribute("user") Bean user) {
        System.out.println("name is:" + user.getName());
        return "demo/index";
    }

    /**
     * 5、用注解@RequestParam绑定请求参数
     * 当请求参数name不存在时会有异常发生,可以通过设置属性required=false解决,
     * @RequestParam(value="name", required=false) 
     * **** 若"Content-Type=application/x-www-form-urlencoded"，post get都可以
     * **** 若"Content-Type=application/application/json"，只适用get
     * 
     * curl -vX POST -H "Content-Type=application/x-www-form-urlencoded" "http://localhost:8080/practiceWebProject/test5?name=name&password=post"
     * curl -vX GET "http://localhost:8080/practiceWebProject/test5?name=name&password=get"
     */
    @RequestMapping(value = "/test5")
    @ResponseBody
    public String test5(@RequestParam(value="name", required=false) String name, @RequestParam("password") String password) {
        System.out.println("name is:" + name);
        System.out.println("password is:" + password);
        return "demo/index";
    }

}