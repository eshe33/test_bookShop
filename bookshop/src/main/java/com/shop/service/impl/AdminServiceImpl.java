package com.shop.service.impl;

import com.shop.entity.Admin;
import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.mapper.AdminMapper;
import com.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Result login(Integer adminId, String password) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Admin admin = adminMapper.getAdminById(adminId);
            if(admin == null){
                result.setMsg("管理员账号不存在");
            }else{
                if (admin.getAdminPassword().equals(password)){
                    result.setMsg("登录成功");
                    result.setSuccess(true);
                    result.setDetail(admin);
                }
                else result.setMsg("密码错误");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
