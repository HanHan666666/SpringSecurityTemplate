package com.system.mapper;

import com.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
