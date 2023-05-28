package com.system.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.Result;
import com.system.entity.RoleMenu;
import com.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/role-menu")
public class RoleMenuController extends BaseController {
    @Autowired
    RoleMenuService roleMenuService;
    @PatchMapping("/update/{RoleId}")
    public Result update(@PathVariable Long RoleId, @RequestBody List<Long> menuIds) {
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", RoleId));
        List<RoleMenu> roleMenus = new ArrayList<>();
        menuIds.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(RoleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });
        roleMenuService.saveBatch(roleMenus);
        return Result.success("保存成功");
    }
}
