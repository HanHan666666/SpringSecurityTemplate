package com.system.mapper;

import com.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 工具userid查询用户所能操作的菜单数据id集合。
     */
    List<Long> getNavMenuIds(Long userId);

    /**
     * 根据菜单编号menuid查询与菜单关联的所有用户信息
     */
    List<User> listByMenuId(Long menuID);
}

