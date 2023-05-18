package com.system.controller;


import com.system.common.Result;
import com.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    MenuService menuService;
//    public
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @RequestMapping("/nav")
    public Result nav(Principal principal){
        String username = principal.getName();
        menuService.getCurrentUserNav(username);
        return Result.success("nav");
    }
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @PostMapping("/save")
    public Result save(){
        return Result.success("save");
    }
}
