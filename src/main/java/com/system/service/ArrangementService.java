package com.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.Arrangement;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-25
 */
public interface ArrangementService extends IService<Arrangement> {
    /**
     * 获得排片类型的列表：2D, 3D, IMAX
     */
    List<String> getTypeList(Long id);

    List<Arrangement> getByFid(Long fid);
}
