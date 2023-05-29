package com.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Category;
import com.system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/system/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

    // all CURD
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size) {
        // 分页返回
        return Result.success(categoryService.page(new Page<>(current, size), null));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        category.setCreated(LocalDateTime.now());
        return Result.success(categoryService.save(category));
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        return Result.success(categoryService.removeByIds(Arrays.asList(ids)));
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        category.setUpdated(LocalDateTime.now());
        return Result.success(categoryService.updateById(category));
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }



}



