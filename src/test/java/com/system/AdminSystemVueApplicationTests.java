package com.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.User;
import com.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdminSystemVueApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    public void testSelect(){
        userMapper.selectList(null).forEach(System.out::println);

    }
    @Test
    public void testSelectById(){
       User user= userMapper.selectById(9);
        System.out.println("user = " + user);
        user.setUsername("shit");
        user.setPassword("kkkkkk");
        int res =  userMapper.insert(user);
        System.out.println("res = " + res);

    }

    @Test
    public void testUpdate(){
        User user = userMapper.selectById(9);
                user.setUsername("shit");
        user.setPassword("kkkkkk");
//        user.setId(null);
        int res =  userMapper.update(user,null);
        System.out.println("res = " + res);
    }

    @Test
    public void testQueryWrapper(){
        QueryWrapper qw = new QueryWrapper();

        qw.eq("id",9);
        List list = userMapper.selectList(qw);
        System.out.println("list = " + list);
    }

    @Test
    public void  testDelete(){
        System.out.println("userMapper.deleteById(37) = " + userMapper.deleteById(37L));
    }

    @Test
    void contextLoads() {
    }

}
