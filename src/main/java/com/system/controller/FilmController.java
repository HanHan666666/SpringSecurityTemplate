package com.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.Result;
import com.system.entity.Film;
import com.system.service.CategoryService;
import com.system.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/system/film")
public class FilmController extends BaseController {
    final
    FilmService filmService;

    final
    CategoryService categoryService;

    public FilmController(FilmService filmService, CategoryService categoryService) {
        this.filmService = filmService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list") // param: current, size
    public Result list(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(filmService.getFilmList(current, size));
    }

    // getfilm by id
    @GetMapping("/getFilmById/{id}")
    public Result getFilmById(@PathVariable Long id) {
        return Result.success(filmService.getById(id));
    }

    @GetMapping("/getFilmCategoryById/{id}")
    public Result getFilmCategoryById(@PathVariable Long id) {
        // 传入电影id，根据电影id查询电影分类id，再根据电影分类id查询电影分类名称
        Long categoryId = filmService.getById(id).getCategoryId();
        return Result.success(categoryService.getById(categoryId));
    }
}
