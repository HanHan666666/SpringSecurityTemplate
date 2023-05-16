package com.system.controller;


import com.system.common.Result;
import com.system.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/user")

public class UserController extends BaseController {
    //参数传递使用RestFul。
    @GetMapping("/userinfo/{username}")
    public Result getUserByUsername(@PathVariable("username") String
                                            username) {
        User user = userService.getUserByUsername(username);
        //springboot自动将Java对象转换为JSON对象
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.fail("请求用户详细数据失败.");
        }
    }
}
//注意：因为定义结果封装类，之后项目中所有Controller中接口方法，返回都是统一
//类型 Result类型。

