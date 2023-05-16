package com.system.common;

import com.system.service.UserService;
import com.system.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
        System.out.println("request = " + request);
    }

    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtil redisUtil;
}