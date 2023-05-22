package com.system.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.Result;
import com.system.entity.Menu;
import com.system.entity.RoleMenu;
import com.system.entity.dto.MenuDto;
import com.system.mapper.UserMapper;
import com.system.service.MenuService;
import com.system.entity.User;

import com.system.service.RoleMenuService;
import org.springframework.util.StringUtils;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.security.Principal;
import java.time.LocalDateTime;
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
@RequestMapping("/menu")
public class MenuController extends BaseController {
    final
    MenuService menuService;


    public MenuController(MenuService menuService, RoleMenuService roleMenuService, UserMapper userMapper) {
        this.menuService = menuService;
        this.roleMenuService = roleMenuService;
        this.userMapper = userMapper;
    }

    final
    RoleMenuService roleMenuService;
    final
    UserMapper userMapper;

    //    public
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @RequestMapping("/nav")
    public Result nav(Principal principal) {
        String username = principal.getName();
        List<MenuDto> menuDtoList = menuService.getCurrentUserNav(username);

        User user = userService.getUserByUsername(username);

        String[] authoritys = StringUtils.tokenizeToStringArray(userService.getUserAuthorityInfo(user.getId()), ",");
        return Result.success(MapUtil.builder().put("nav", menuDtoList).put("authoritys", authoritys).map());
    }
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @RequestMapping("/list/{id}")
    public Result list(@PathVariable("id") Long id) {
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().eq("parent_id", id));
        return Result.success(menus);
    }
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @RequestMapping("/list")
    public Result list() {
        List<Menu> tree = menuService.tree();
        return Result.success(tree);
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        menu.setUpdated(LocalDateTime.now());
        return Result.success("update");
    }

    @PreAuthorize("hasAuthority('sys:menu:list')")
    @PostMapping("/save")
    public Result save() {
        Menu menu = new Menu();
        menu.setCreated(LocalDateTime.now());
        menu.setUpdated(LocalDateTime.now());
        menuService.save(menu);
        return Result.success("save");
    }

    // delete
    //sys:menu:delete 自定义 menu:delete
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping("/delete/{id}")
    public Result del(@PathVariable("id") Long id) {
        //查询要删除这个id菜单下所有的子菜单数量
        int count = menuService.count(new QueryWrapper<Menu>
                ().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }
        //清空Redis中 用户权限和当前删除菜单id关联的数据
        userService.clearUserAuthorityInfoByMenuId(id);
        menuService.removeById(id);
        //删除菜单， sys_role_menu 中还有角色和菜单关联的数据了，需要同步删除。
        roleMenuService.remove(new QueryWrapper<RoleMenu>
                ().eq("menu_id", id));
        return Result.success("");
    }
}
