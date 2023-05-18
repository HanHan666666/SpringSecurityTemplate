package com.system.common;

import com.system.service.UserService;
import com.system.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
//        request.setContentType("application/json;charset=utf-8");
        this.request = request;
        System.out.println("request = " + request);
    }

    protected HttpServletResponse response;

    @Autowired
    public void setResponse(HttpServletResponse response) {
//        response.setContentType("application/json;charset=utf-8");
        this.response = response;
    }


    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtil redisUtil;
}