package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.Menu;
import com.system.entity.User;
import com.system.entity.dto.MenuDto;
import com.system.mapper.MenuMapper;
import com.system.mapper.UserMapper;
import com.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    final
    MenuMapper menuMapper;
    final
    UserService userService;
    final
    UserMapper userMapper;

    public MenuServiceImpl(MenuMapper menuMapper, UserService userService, UserMapper userMapper) {
        this.menuMapper = menuMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public List<MenuDto> getCurrentUserNav(String username) {
        // 通过当前登录的用户名
        User user = userService.getUserByUsername(username);
        List<Long> menuIds = userMapper.getNavMenuIds(user.getId());
        List<Menu> menus = this.listByIds(menuIds);
        List<Menu> finalMenu = this.buildTreeMenu(menus);

        return this.convert(finalMenu);
    }

    @Override
    public List<Menu> tree() {
        List<Menu> menus = this.list(new QueryWrapper<Menu>().orderByAsc("orderNum"));
        return this.buildTreeMenu(menus);
    }

    private List<MenuDto> convert(List<Menu> menus) {
        List<MenuDto> menuDtoList = new ArrayList<>();
        menus.forEach(m -> {
            MenuDto dto = new MenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setIcon(m.getIcon());
            dto.setPath(m.getPath());
            if (m.getChildren().size() > 0) { //当前菜单是有子菜单
                //menu菜单取出Children有是一个 Menu集合（子菜单也需要Menu转为MenuDto）。
                //递归调用，convert(子菜单集合) ，将子菜单再转换一下。
                dto.setChildren(convert(m.getChildren()));
            }
            menuDtoList.add(dto);
        });
        return menuDtoList;
    }

    private List<Menu> buildTreeMenu(List<Menu> menus) {
        List<Menu> finalMenus = new ArrayList<>();
        for (Menu m : menus) {
            for (Menu e : menus) {
                if (e.getParentId() == m.getId()) {
                    m.getChildren().add(e);
                }
            }
            if (m.getParentId() == 0) {
                finalMenus.add(m);
            }

        }
        return finalMenus;

    }

}
