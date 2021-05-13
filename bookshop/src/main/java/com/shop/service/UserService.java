package com.shop.service;

import com.shop.entity.Result;
import com.shop.entity.User;

public interface UserService {
    public Result regist(User user);
    public Result login(Integer userId, String userPassword);
}
