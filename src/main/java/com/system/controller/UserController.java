package com.system.controller;


import com.system.service.UserService;
import com.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

}
