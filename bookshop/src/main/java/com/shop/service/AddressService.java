package com.shop.service;

import com.shop.entity.Address;
import com.shop.entity.Result;

public interface AddressService {
    /**
     * 增加新的收货地址
     * @param userId 用户id
     * @param address 收货地址数据
     */
    Result addAddress(Integer userId, Address address);

    /**
     * 查询某用户的收货地址列表
     * @param userId 用户的id
     * @return 该用户的收货地址列表
     */
    Result getByUserId(Integer userId);

    /**
     * 设置默认收货地址
     * @param addressId 需要被设置为默认的收货地址id
     * @param userId 用户id
     */
    Result setDefault(Integer addressId, Integer userId);

    /**
     * 删除收货地址
     * @param addressId 需要被删除的收货地址id
     * @param userId 用户id
     */
    Result deleteAddress(Integer addressId, Integer userId);

    /**
     * 修改收货地址
     * @param addressId 需要被删除的收货地址id
     * @param userId 用户id
     */
    Result updateAddress(Integer addressId, Integer userId, Address address);

    /**
     * 根据收货地址id查询收货地址详情
     * @param addressId 收货地址id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    Result getByAddressId(Integer addressId);

}
