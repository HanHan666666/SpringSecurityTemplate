package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.UserRole;
import com.system.mapper.UserRoleMapper;
import com.system.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    final
    UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public void saveUserRole(Long userId, Long[] roleIds) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        // 先删除原来的角色
        userRoleMapper.delete(queryWrapper.eq("user_id", userId));
        // 再保存新的角色
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            }
        }
    }
}
