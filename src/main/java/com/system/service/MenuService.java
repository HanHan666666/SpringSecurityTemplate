package com.system.service;

import com.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.dto.MenuDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
public interface MenuService extends IService<Menu> {
    List<MenuDto> getCurrentUserNav(String username);
    List<Menu> tree();
}
