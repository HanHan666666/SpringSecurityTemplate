package com.system.service.impl;

import com.system.entity.Menu;
import com.system.entity.User;
import com.system.entity.dto.MenuDto;
import com.system.mapper.MenuMapper;
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
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserService userService;

    @Override
    public List<MenuDto> getCurrentUserNav(String username) {
        // 通过当前登录的用户名
        User user = userService.getUserByUsername(username);
        List<Long> menuIds = menuMapper.getMenuIdsByUserId(user.getId());
        List<Menu> menus = this.listByIds(menuIds);
        this.buildTreeMenu(menus);
        return null;
    }

    private List<Menu> buildTreeMenu(List<Menu> menus) {
        List<Menu> finalMenus = new ArrayList<>();
        return null;
    }
}
