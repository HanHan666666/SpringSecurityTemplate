package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;

import com.system.entity.Arrangement;
import com.system.service.ArrangementService;

import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/arrangement")
public class ArrangementController extends BaseController {
    final
    ArrangementService arrangementService;



    public ArrangementController(ArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }
    //返回类型列表
    @GetMapping("/typelist/{id}")
    public Result typeList(@PathVariable Long id) {
        return Result.success(arrangementService.getTypeList(id));
    }
    //根据电影id返回排片信息列表
    @GetMapping("/list/{fid}")
    public Result list(@PathVariable Long fid) {
        return Result.success(arrangementService.getByFid(fid));
    }

    //返回所有排片信息，分页
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(arrangementService.page(new Page<>(current,size), null));
    }

    @PostMapping("/add")
    public Result add(@RequestBody com.system.entity.Arrangement arrangement) {
        arrangement.setCreated(LocalDateTime.now());
        arrangement.setUpdated(LocalDateTime.now());
        arrangement.setStartTime(arrangement.getStartAndEnd().get(0));
        arrangement.setEndTime(arrangement.getStartAndEnd().get(1));
        arrangementService.save(arrangement)    ;
        return Result.success("添加成功");
    }

    //根据id获取排片详情
    @GetMapping("/{id}")
    public Result info(@PathVariable Long id) {
        Arrangement arrangement = arrangementService.getById(id);
        arrangement.setStartAndEnd(arrangement.getStartTime(),arrangement.getEndTime());
        System.out.println(arrangement);
        return Result.success(arrangement);
    }

    //update
    @PostMapping("/update")
    public Result update(@RequestBody Arrangement arrangement) {
        arrangement.setUpdated( LocalDateTime.now());
        arrangement.setStartTime(arrangement.getStartAndEnd().get(0));
        arrangement.setEndTime(arrangement.getStartAndEnd().get(1));
        return Result.success(arrangementService.updateById(arrangement));
    }
    //delete
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
        arrangementService.removeByIds(java.util.Arrays.asList(ids));
        return Result.success("删除成功");
    }
}
