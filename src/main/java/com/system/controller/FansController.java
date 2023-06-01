package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Fans;
import com.system.service.FansService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wcy
 * @since 2023-05-24
 */
@Slf4j
@RestController
@RequestMapping("/fans")
public class FansController extends BaseController {
    final
    FansService fansService;

    public FansController(FansService fansService) {
        this.fansService = fansService;
    }

    // list
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String keyword) {
        QueryWrapper<Fans> wrapper = new QueryWrapper<>();
        log.info("keyword:{}", keyword);
        if (!StrUtil.isBlankOrUndefined(keyword)) {
            log.info("keyword:{}", keyword);
            wrapper.like("username", keyword);
            wrapper.or();
            wrapper.like("email", keyword);
        }
        Page<Fans> page = fansService.page(new Page<>(current, size), wrapper);
        return Result.success(page);
    }

    // delete
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        fansService.removeByIds(Arrays.asList(ids));
        return Result.success("删除成功");
    }

    // edit
    @PatchMapping("/update")
    public Result update(@RequestBody Fans fans) {
        fansService.updateById(fans);
        return Result.success("修改成功");
    }

    // info
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(fansService.getById(id));
    }
}
