package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Role;
import com.system.entity.RoleMenu;
import com.system.entity.User;
import com.system.entity.UserRole;
import com.system.entity.dto.PassDTO;
import com.system.service.RoleMenuService;
import com.system.service.RoleService;
import com.system.service.UserRoleService;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.security.Principal;

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
    // roleMenuService
    final
    RoleMenuService roleMenuService;
    final
    UserRoleService userRoleService;
    final
    PasswordEncoder passwordEncoder;

    public UserController(RoleMenuService roleMenuService, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.roleMenuService = roleMenuService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    // updatepassword
    @PreAuthorize("hasAuthority('sys:user:repass')")
    @PostMapping("/updatePass")
    public Result updatePass(@RequestBody PassDTO passDTO, HttpTrace.Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        boolean matches = passwordEncoder.matches(passDTO.getPassword(), user.getPassword());
        if (!matches) {
            return Result.fail("旧密码错误");
        }
        if (!passDTO.getNewPass().equals(passDTO.getCheckPass())) {
            return Result.fail("两次输入密码不相同");
        }

        user.setPassword(passwordEncoder.encode(passDTO.getNewPass()));
        user.setUpdated(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("修改成功");


    }

    // 参数传递使用RestFul。
    @GetMapping("/info/{username}")
    public Result getUserByUsername(@PathVariable("username") String
                                            username) {
        User user = userService.getUserByUsername(username);
        // springboot自动将Java对象转换为JSON对象
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.fail("请求用户详细数据失败.");
        }
    }

    @GetMapping("/userinfo/{id}")
    public Result getUserById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        List<Role> roles = roleService.listRolesByUserId(user.getId());
        user.setRoles(roles);
        // springboot自动将Java对象转换为JSON对象
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.fail("请求用户详细数据失败.");
        }
    }

    // list
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public Result list(String username) {
        Page<User> users = userService.page(getPage(), new QueryWrapper<User>()
                .like(StrUtil.isNotBlank(username),
                        "username", username));
        users.getRecords().forEach(u -> {
            List<Role> roles = roleService.listRolesByUserId(u.getId());
            u.setRoles(roles);
        });
        return Result.success(users);
    }

    // 批量delete
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id", ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", ids));
        userService.removeByIds(Arrays.asList(ids));
        return Result.success("删除成功");
    }

    // update
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        user.setUpdated(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("更新成功");
    }

    // save
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        user.setCreated(LocalDateTime.now());
        // user.setUpdated(LocalDateTime.now());
        String encode_password = passwordEncoder.encode(Const.DEFAULT_PASSWORD);
        user.setPassword(encode_password);
        if (StrUtil.isBlankOrUndefined(user.getAvatar()))
            user.setAvatar(Const.DEFAULT_AVATAR);
        // 检查用户是否已经存在
        try {
            userService.save(user);
        } catch (DuplicateKeyException e) {
            return Result.fail("用户名已存在");
        }

        return Result.success("保存成功");
    }

    // resetPassword
    @PreAuthorize("hasAuthority('sys:user:repass')")
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody Long id) {
        User user = userService.getById(id);
        String encode_password = passwordEncoder.encode(Const.DEFAULT_PASSWORD);
        user.setPassword(encode_password);
        userService.updateById(user);
        return Result.success("重置密码成功");
    }

    @PostMapping("/setRole")
    public Result setRole(@RequestBody UserRole userRole) {
        userRoleService.saveOrUpdate(userRole);
        return Result.success("设置成功");
    }

    @PostMapping("/saveUserRole/{id}")
    public Result saveUserRole(@RequestBody Long[] roleIds, @PathVariable("id") Long userId) {
        userRoleService.saveUserRole(userId, roleIds);
        return Result.success("设置成功");
    }

    @GetMapping("/myinfo")
    public Result myinfo(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        user.setRoles(roleService.listRolesByUserId(user.getId()));
        return Result.success(user);
    }
}
// 注意：因为定义结果封装类，之后项目中所有Controller中接口方法，返回都是统一
// 类型 Result类型。

