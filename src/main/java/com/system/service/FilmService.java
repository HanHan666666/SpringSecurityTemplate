package com.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.entity.Film;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
public interface FilmService extends IService<Film> {

   IPage<Film> getFilmList(int current, int size);
}
