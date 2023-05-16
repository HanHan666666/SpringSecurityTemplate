package com.system.service;

import com.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
public interface UserService extends IService<User> {
    User getUserByUsername(String username);
}
