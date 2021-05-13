package com.shop.service.impl;

import com.shop.entity.Address;
import com.shop.entity.Result;
import com.shop.mapper.AddressMapper;
import com.shop.service.AddressService;
import com.shop.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    private DistrictService districtService;


    @Override
    public Result addAddress(Integer userId, Address address) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try{
            // 基于参数uid调用addressMapper.countByUid()统计当前用户的收货地址数据的数量
            Integer count = addressMapper.countByUserId(userId);
            // 基于统计结果判断得到是否默认的值
            Integer isDefault = (count == 0) ? 1 : 0;
            // 补全参数address中的属性：uid > 参数uid
            address.setUserId(userId);
            // 补全参数address中的属性：is_default > 以上判断结果
            address.setIsDefault(isDefault);
            // 补全参数address中的属性：根据省市区的代号获取名称
            String provinceName = districtService.getNameByCode(address.getProvinceCode()).getDetail().toString();
            String cityName = districtService.getNameByCode(address.getCityCode()).getDetail().toString();
            String areaName = districtService.getNameByCode(address.getAreaCode()).getDetail().toString();
            address.setProvinceName(provinceName);
            address.setCityName(cityName);
            address.setAreaName(areaName);
            // 基于参数address调用addressMapper.insertAddress()执行插入收货地址数据，并获取返回值
            Integer rows = addressMapper.insertAddress(address);
            if (rows == 1){
                result.setDetail(address);
                result.setMsg("地址添加成功");
                result.setSuccess(true);
            } else {
                result.setMsg("地址添加失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getByUserId(Integer userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Address> addresses = addressMapper.findByUserId(userId);
            if(addresses.isEmpty()) {
                result.setMsg("查找失败");
            }
            else {
                // 遍历查询到的收货地址列表
                for (Address address : addresses) {
                    // -- 将不需要响应到客户端的属性设置为null：uid, province_code, city_code, area_code, is_default, 4个日志属性
                    address.setUserId(null);
                    address.setProvinceCode(null);
                    address.setCityCode(null);
                    address.setAreaCode(null);
                    address.setIsDefault(null);
                }
                result.setDetail(addresses);
                result.setSuccess(true);
                result.setMsg("查找成功");
            }
        } catch (Exception e) {
            result.setDetail(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Result setDefault(Integer addressId, Integer userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        // 基于参数aid调用addressMapper.findByAid()执行查询
        Address address = addressMapper.findByAddressId(addressId);
        // 判断查询结果是否为null
        if (address == null) {
            // 是：抛出AddressNotFoundException
            result.setMsg("设置默认收货地址失败！尝试访问的数据不存在！");
            return result;
        }

        // 判断数据归属是否正确，即查询结果中的uid与参数uid(控制器层从Session中取出的uid)是否不一致，对比时，需要使用equals()方法，不要使用== 或 != 运算符
        if (!address.getUserId().equals(userId)) {
            // 是：抛出AccessDeniedException
            result.setMsg("设置默认收货地址失败！非法访问已经被拒绝！");
            return result;
        }

        // 基于参数uid调用addressMapper.updateNonDefaultByUserId()将该用户的所有收货地址设为“非默认”，并获取返回值
        Integer rows = addressMapper.updateNonDefaultByUserId(userId);
        // 判断返回结果是否小于1
        if (rows < 1) {
            // 是：抛出UpdateException
            result.setMsg("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
            return result;
        }

        // 调用addressMapper.updateDefaultByAid()将指定的收货地址设置为“默认”，并获取返回值
        rows = addressMapper.updateDefaultByAddressId(addressId);
        // 判断返回结果是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            result.setMsg("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
            return result;
        }
        result.setMsg("默认地址修改成功");
        result.setSuccess(true);
        result.setDetail(address);
        return result;

    }

    @Override
    public Result deleteAddress(Integer addressId, Integer userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        // 基于参数addressId调用addressMapper.findByAddressId()执行查询
        Address address = addressMapper.findByAddressId(addressId);
        // 判断查询结果是否为null
        if (address == null) {
            // 是：抛出AddressNotFoundException
            result.setMsg("删除收货地址失败！尝试访问的数据不存在！");
            return result;
        }

        // 判断数据归属是否正确，即查询结果中的userId与参数userId(控制器层从Session中取出的userId)是否不一致，
        // 对比时，需要使用equals()方法，不要使用 == 或 != 运算符
        if (!address.getUserId().equals(userId)) {
            // 是：抛出AccessDeniedException
            result.setMsg("删除收货地址失败！非法访问已经被拒绝！");
            return result;
        }

        // deleteByAddressId()执行删除，并获取返回值
        Integer rows = addressMapper.deleteByAddressId(addressId);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出DeleteException
            result.setMsg("删除收货地址失败！删除收货地址数据时出现未知错误，请联系系统管理员！");
            return result;
        }

        // 判断查询结果中的isDefault是否为0（刚才删除的不是默认收货地址）
        if (address.getIsDefault() == 0) {
            result.setDetail(address);
            result.setMsg("删除成功");
            result.setSuccess(true);
            return result;
        }

        // 调用countByUid()统计目前该用户的收货地址数量
        Integer count = addressMapper.countByUserId(userId);
        // 判断统计结果是否为0（该用户刚才确实删除了默认收货地址，但是，是最后一条收货地址）
        if (count == 0) {
            result.setMsg("删除成功");
            result.setDetail(address);
            result.setSuccess(true);
            return result;
        }

        // 调用findLastModified()查询该用户最近修改的收货地址
        Address lastModified = addressMapper.findLastModified(userId);
        // 取出此次查询结果中的addressId
        Integer lastModifiedAid = lastModified.getAddressId();
        // 基于以上aid调用updateDefaultByAid()将这条收货地址设置为默认，获取返回值
        rows = addressMapper.updateDefaultByAddressId(lastModifiedAid);
        // 判断返回结果是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            result.setMsg("删除后设置默认收货地址失败！");
            return result;
        }
        result.setDetail(address);
        result.setSuccess(true);
        result.setMsg("删除成功");
        return result;
    }

    @Override
    public Result updateAddress(Integer addressId, Integer userId, Address address) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        Integer preDefault = addressMapper.findByAddressId(addressId).getIsDefault();
        Integer nowDefault = address.getIsDefault();

        addressMapper.deleteByAddressId(addressId);

        if(preDefault == 0 && nowDefault == 1) {
            addressMapper.updateNonDefaultByUserId(userId);
        } else  if(preDefault == 1 && nowDefault == 0){
            Integer rows = addressMapper.countByUserId(userId);
            if (rows == 0) {
                address.setIsDefault(1);
            } else {
                Address address_last = addressMapper.findLastModified(userId);
                addressMapper.updateDefaultByAddressId(address_last.getAddressId());
            }
        }
        addressMapper.insertAddress(address);

        result.setDetail(address);
        result.setSuccess(true);
        result.setMsg("修改成功");
        return result;
    }

    @Override
    public Result getByAddressId(Integer addressId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        // 调用私有的findByAid()找出数据
        Address address = addressMapper.findByAddressId(addressId);
        // 判断查询结果是否为null
        if (address == null) {
            result.setMsg("获取收货地址数据失败！");
            return result;
        }

        result.setDetail(address);
        result.setSuccess(true);
        result.setMsg("获取收货地址数据成功");

        // 返回数据
        return result;
    }


}
