package com.system.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.service.RoleService;
import com.system.service.UserService;
import com.system.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;


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

    @Autowired
    protected RoleService roleService;

    public Page getPage() {
        long current = ServletRequestUtils.getIntParameter(request, "current", 1);
        long size = ServletRequestUtils.getIntParameter(request, "size", 10);
        return new Page<>(current, size);
    }
}