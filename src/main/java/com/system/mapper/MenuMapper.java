package com.system.mapper;

import com.system.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {
//    List<Long> getMenuIdsByUserId(Long userId);

    List<Long> getNavMenuIds(long id);
}