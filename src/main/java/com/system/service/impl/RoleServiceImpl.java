package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.Role;
import com.system.entity.UserRole;
import com.system.mapper.RoleMapper;
import com.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    final
    UserRoleService userRoleService;

    public RoleServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public List<Role> listRolesByUserId(Long userId) {
        List<UserRole> user_role_list = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (user_role_list.size() > 0) {
            List<Long> roleIds = user_role_list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            return this.listByIds(roleIds);
        } else {
            return null;
        }
    }
}
