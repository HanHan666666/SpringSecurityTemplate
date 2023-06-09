package com.system.service;

import com.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     *  保存用户角色
     * */
    void saveUserRole(Long userId, Long[] roleIds);
}
