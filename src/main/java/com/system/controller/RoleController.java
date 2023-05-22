package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Role;
import com.system.entity.RoleMenu;
import com.system.entity.UserRole;
import com.system.service.RoleMenuService;


import com.system.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    UserRoleService userRoleService;
    final
    RoleMenuService roleMenuService;

    public RoleController(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    // @PreAuthorize("hasAuthority('sys:role:list')")
    // @GetMapping("/list")
    // public Result list(String name) {
    //     Page<Role> roles = roleService.page(getPage(), new QueryWrapper<Role>().like(StringUtils.isNotBlank(name), name, name));
    //     return Result.success(roles);
    // }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Result list(String name) { // 搜索栏输入 模糊查询关键词 name
        // 情况1：name==null没有值， 没有输入模糊查询，查询所有角色分页数据信息：
        // 情况2： name==XXXX， 输入搜索关键词， 按照 关键词模糊查询 出所有角色的分页数据：
        // MP 返回分页数据结果，就是封装到Page对象中，page对象中就包括 records属性（角色分页数据集合） 和相关分页属性 size、total、page...
        // select * from role where name like '%XXXXX%'
        Page<Role> roles = roleService.page(getPage(), new
                QueryWrapper<Role>().like(StrUtil.isNotBlank(name), "name", name));
        return Result.success(roles);
    }


    @PreAuthorize("hasAuthority('sys:role:save')")
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        role.setStatu(Const.STATUS_ON);
        role.setCreated(LocalDateTime.now());
        roleService.save(role);
        return Result.success("添加成功");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {

        Role role = roleService.getById(id);
        // 查询该角色 所关联的权限菜单： select menu_id from sys_role_menuwhere role_id = 角色id
        List<RoleMenu> rolemenu = roleMenuService.list(new
                QueryWrapper<RoleMenu>().eq("role_id", id));
        List<Long> menuIds = rolemenu.stream().map(rm ->
                rm.getMenuId()).collect(Collectors.toList());
        role.setMenuIds(menuIds);
        return Result.success(role);
    }

    // update
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        role.setUpdated(LocalDateTime.now());
        roleService.updateById(role);
        return Result.success("修改成功");
    }

    // 批量delete

    @Transactional
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id", (Object) ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id", (Object) ids));
        // roleService.removeByIds(ids);
        roleService.removeByIds(Arrays.asList(ids));
        return Result.success("删除成功");
    }
}
