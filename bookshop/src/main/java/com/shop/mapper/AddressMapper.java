package com.shop.mapper;

import com.shop.entity.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface AddressMapper {

    /**
     * 插入收货地址数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    @Insert(value = "INSERT INTO address" +
            "(user_id, name, province_name, province_code, city_name, city_code," +
            " area_name, area_code, zip, address_detail, telephone, is_default) " +
            "VALUES" +
            "(#{userId}, #{name}, #{provinceName}, #{provinceCode}, " +
            "#{cityName}, #{cityCode}, #{areaName}, #{areaCode}, #{zip}, " +
            "#{addressDetail}, #{telephone}, #{isDefault})")
    Integer insertAddress(Address address);

    /**
     * 统计某个用户的收货地址的数量
     * @param userId 用户的id
     * @return 该用户的收货地址的数量
     */
    @Select(value = "SELECT COUNT(*) FROM address WHERE user_id=#{userId}")
    Integer countByUserId(Integer userId);


    /**
     * 查询某用户的收货地址列表
     * @param userId 用户的id
     * @return 该用户的收货地址列表
     */
    @Select(value = "SELECT * FROM address WHERE user_id=#{userId} ORDER BY is_default DESC")
    @Results({@Result(property = "provinceName", column = "province_name"),
            @Result(property = "provinceCode", column = "province_code"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "cityCode", column = "city_code"),
            @Result(property = "areaName", column = "area_name"),
            @Result(property = "areaCode", column = "area_code"),
            @Result(property = "isDefault", column = "is_default")})
    List<Address> findByUserId(Integer userId);

    /**
     * 将某收货地址设置为默认
     * @param addressId 收货地址的id
     * @return 受影响的行数
     */
    @Update(value = "UPDATE address SET is_default=1 WHERE address_Id=#{addressId}")
    Integer updateDefaultByAddressId(Integer addressId);

    /**
     * 将某用户的所有收货地址设置为非默认
     * @param userId 用户id
     * @return 受影响的行数
     */
    @Update(value = "UPDATE address SET is_default=0 WHERE user_id=#{userId}")
    Integer updateNonDefaultByUserId(Integer userId);


    /**
     * 根据收货地址id查询收货地址详情
     * @param addressId 收货地址id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    @Select(value = "SELECT * FROM address WHERE address_id=#{addressId}")
    @ResultMap(value = "AddressEntityMap")
    Address findByAddressId(@Param(value = "addressId")Integer addressId);


    /**
     * 删除收货地址
     * @param addressId 收货地址的id
     * @return 受影响的行数
     */
    @Delete(value = "DELETE FROM address WHERE address_Id=#{addressId}")
    Integer deleteByAddressId(Integer addressId);

    /**
     * 查询某用户最近修改的收货地址
     * @param userId 用户的id
     * @return 该用户最近修改的收货地址
     */
    @Select(value = "SELECT * FROM address WHERE user_id=#{userId} " +
            "ORDER BY address_id DESC LIMIT 0, 1")
    @Results({@Result(property = "addressId", column = "address_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "isDefault", column = "is_default")})
    Address findLastModified(Integer userId);
}
