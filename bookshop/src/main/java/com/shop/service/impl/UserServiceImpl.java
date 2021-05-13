package com.shop.service.impl;

import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.mapper.CartMapper;
import com.shop.mapper.UserMapper;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByName(user.getUserName());
            if(existUser != null && existUser.getIsDeleted() == 0){
                //如果用户名已存在
                result.setMsg("用户名已存在");
            }else{
                Integer rows = userMapper.insertUser(user.getUserName(), user.getUserPassword());
                if (rows != 0){
                    result.setMsg("注册成功");
                    result.setSuccess(true);
                    result.setDetail(user);
                }
                else result.setMsg("注册失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result login(Integer userId, String userPassword) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            String userName= userMapper.getUserNameById(userId, userPassword);
            if(userName == null){
                result.setMsg("账号或密码错误");
            }else {
                User user = userMapper.findUserByName(userName);
                System.out.println(user);
                if(user.getIsDeleted() == 1){
                    result.setMsg("账号或密码错误");
                } else {
                    result.setMsg(userName+"您好！登录成功");
                    result.setSuccess(true);
                    result.setDetail(user);
                }
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
