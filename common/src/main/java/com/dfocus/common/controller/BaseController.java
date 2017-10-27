package com.dfocus.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author: qfwang
 * Date: 2017-10-12 下午4:46
 */
public class BaseController {
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected ModelAndView mv;
    /**
     * spring 中request、response是线程安全的，可以直接注入
     * @ModelAttribute注解只有在被@Controller和@ControllerAdvice两个注解的类下使用
     * ModelAttribute的作用
     * 1)放置在方法的形参上：表示引用Model中的数据
     * 2)放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面。
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.response= response;
        this.request = request;
        mv = new ModelAndView();
    }

    /**
     * @return
     */
    protected HashMap<String, String> getRequestHeaders()  {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestHeaders.put(headerName, headerValue);
        }
        return requestHeaders;
    }

    /**
     * @return
     */
    protected HashMap<String, String> getRequestParams() {
        HashMap<String, String> requestParams = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            requestParams.put(paramName, paramValue);
        }
        return requestParams;
    }

    protected void printMsg(String message){
        response.setHeader("Content-Type" , "text/html");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(message);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Object getParam(String key, String def) {
        String value = request.getParameter(key);
        return value==null?def:value;
    }
}
