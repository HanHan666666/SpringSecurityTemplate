package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Role;
import com.system.entity.User;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

import java.util.List;

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
    @GetMapping("/info/{username}")
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

    @GetMapping("/userinfo/{id}")
    public Result getUserById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        //springboot自动将Java对象转换为JSON对象
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.fail("请求用户详细数据失败.");
        }
    }

    //list
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public Result list(String username){
        Page<User> users = userService.page(getPage(), new QueryWrapper<User>()
                .like(StrUtil.isNotBlank(username),
                        "username",username));
        users.getRecords().forEach(u->{
            List<Role> roles = roleService.listRolesByUserId(u.getId());
            u.setRoles(roles);
        });
        return Result.success(users);
    }
}
//注意：因为定义结果封装类，之后项目中所有Controller中接口方法，返回都是统一
//类型 Result类型。

