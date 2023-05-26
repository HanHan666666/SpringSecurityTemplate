package com.system.controller;

import com.system.common.Result;

import com.system.service.ArrangementService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;


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

    @GetMapping("/typelist/{id}")
    public Result typeList(@PathVariable Long id) {
        return Result.success(arrangementService.getTypeList(id));
    }

    @GetMapping("/list/{fid}")
    public Result list(@PathVariable Long fid) {
        return Result.success(arrangementService.getByFid(fid));
    }
}
