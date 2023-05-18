package com.system.service;

import com.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
public interface UserService extends IService<User> {
    /*
     * 根据用户名查询用户信息
     * @param username
     * */
    User getUserByUsername(String username);

    /**
     * 根据用户id获取权限列表
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 删除某个用户的缓存中的权限信息
     */
    void clearUserAuthorityInfo(String username);

    /**
     * 删除所有与该角色 关联的用户的权限信息:
     */

    void clearUserAuthorityInfoByRoleId(Long roleId);

    /**
     * 删除所有与菜单关联的所有用户的权限信息
     */

    void clearUserAuthorityInfoByMenuId(Long menuId);
}
