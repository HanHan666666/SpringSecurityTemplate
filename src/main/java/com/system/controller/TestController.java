package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.BaseController;
import com.system.common.Result;
import com.system.entity.User;
import com.system.mapper.RoleMapper;
import com.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/user/list")
    public Object getUserList() {
//        return userMapper.selectList(null);
        return Result.success(userMapper.selectList(null));
    }

    @PutMapping("/user/update")
    public boolean updateUser() {

        return true;
    }

    @PostMapping("/user/add")
    public int addUser() {
        User user = new User("hhhhh", "kkkkkk", "ssss","email","k", LocalDateTime.now());
        user.setStatu(1);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return userMapper.insert(user);
    }
    @DeleteMapping("/user/delete/{id}")
    public int deleteUser(@PathVariable("id") int id) {
        System.out.println("id = " + id);
        return userMapper.deleteById(id);
    }

    @PutMapping("/user/update/{id}")
    public int updateUser(@PathVariable("id") int id){
        User user = new User();
        user.setEmail("example@example.com");
//        user.setId(id);
         QueryWrapper qw = new QueryWrapper();
         qw.eq("id",id);
        return userMapper.update(user,qw);
    }



    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @GetMapping("/role/list")
    public Object getRoleList() {
        return roleMapper.selectList(null);
    }


}
