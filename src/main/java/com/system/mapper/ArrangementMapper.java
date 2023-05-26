package com.system.mapper;

import com.system.entity.Arrangement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-25
 */
@Repository
public interface ArrangementMapper extends BaseMapper<Arrangement> {
    /**
     * 获得排片类型的列表：2D, 3D, IMAX
     */
    List<String> TypeList(Long id);
}
