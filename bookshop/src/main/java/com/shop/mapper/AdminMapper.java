package com.shop.mapper;

import com.shop.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;

public interface AdminMapper {
    @Select("select * from admin where admin_id = #{adminId}")
    @Results({@Result(property = "adminId", column = "admin_id"),
              @Result(property = "adminName", column = "admin_name"),
              @Result(property = "adminPassword", column = "admin_password")})
    Admin getAdminById(Integer adminId);

}
