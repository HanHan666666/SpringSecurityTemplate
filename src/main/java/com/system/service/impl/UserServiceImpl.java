package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.mapper.UserMapper;
import com.system.service.MenuService;
import com.system.service.RoleService;
import com.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.utils.RedisUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    @Autowired
    RedisUtil redisUtil;

    // 根据用户名查询 该用户的详细信息: 用户名不能重复的
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        return userMapper.selectOne(qw);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        String authorityString = "";

        QueryWrapper<Role> role_wrapper = new QueryWrapper<>();
        role_wrapper.inSql("id", "select role_id from sys_user_role where user_id = " + userId);
        User user = this.getById(userId);

        // 如果redis中有权限字符串，直接返回
        if (redisUtil.hasKey("granted_" + user.getUsername())) {
            return redisUtil.get("granted_" + user.getUsername()).toString();
        }


        List<Role> list = roleService.list(role_wrapper);
        List<Role> roles = roleService.list(role_wrapper);
        // 通过用户编号查询所能操作的菜单id
        List<Long> menuIds = userMapper.getNavMenuIds(userId);
        // 使用menusids查询所有的菜单数据
        List<Menu> menus = menuService.listByIds(menuIds);
        // 要列表roles长度大于0才能进行循环，否则报错
        if (roles.size() > 0) {
            String roleString = roles.stream().map(m -> "ROLE_" + m.getCode()).collect(Collectors.joining(","));
            authorityString = roleString.concat(",");
        }
        if (menus.size() > 0) {
            // 权限字符串
            String permString = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            authorityString = authorityString.concat(permString);
        }
        // 用的时候再次查询太耗时，将权限字符串存到redis中
        redisUtil.set("granted_" + user.getUsername(), authorityString);
        // System.out.println("authorityString = " + authorityString);
        log.info("权限字符串--roleString = " + authorityString);
        return authorityString;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("granted_" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.inSql("id", "SELECT user_id FROM sys_user_role WHERE role_id =" + roleId);
        List<User> users = this.list(qw);
        users.forEach(u -> this.clearUserAuthorityInfo(u.getUsername()));
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        // 还没明白这个方法的sql原理👆

        List<User> users = userMapper.listByMenuId(menuId);
        users.forEach(u -> this.clearUserAuthorityInfo(u.getUsername()));
    }
}
