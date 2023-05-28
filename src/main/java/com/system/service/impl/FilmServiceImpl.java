package com.system.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.entity.Film;
import com.system.mapper.FilmMapper;
import com.system.service.FilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements FilmService {
    @Autowired
    FilmMapper filmMapper;
    @Override
    public IPage<Film> getFilmList(int current,int size) {
        return filmMapper.getFilmList(new Page<>(current, size));
    }
}
