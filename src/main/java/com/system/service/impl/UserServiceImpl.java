package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.User;
import com.system.mapper.UserMapper;
import com.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    //根据用户名查询 该用户的详细信息: 用户名不能重复的
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        return userMapper.selectOne(qw);
    }
}
