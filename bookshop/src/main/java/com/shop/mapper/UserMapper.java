package com.shop.mapper;

import com.shop.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * mapper的具体表达式
 */

@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface UserMapper {

    /**
     * 查询用户名是否存在，若存在，不允许注册
     */
    @Select(value = "select * from user where user_name=#{username}")
    @ResultMap(value = "UserEntityMap")
    User findUserByName(String userName);

    /**
     * 注册  插入一条user记录
     */
    @Insert("insert into user(user_name, user_password) values(#{userName},#{userPassword})")
    Integer insertUser(String userName, String userPassword);

    /**
     * 登录
     * @return
     */
    @Select("select user_name from user where user_id = #{userId} and user_password = #{userPassword}")
    String getUserNameById(Integer userId, String userPassword);
}