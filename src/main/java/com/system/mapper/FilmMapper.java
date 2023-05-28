package com.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.system.entity.Film;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
@Repository
public interface FilmMapper extends BaseMapper<Film> {
    IPage<Film> getFilmList(IPage<Film> page);
}
