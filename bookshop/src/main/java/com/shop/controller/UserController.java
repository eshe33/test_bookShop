package com.shop.controller;

import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController    //相当于@Controller+@RequestBody
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @GetMapping(value = "/regist")
    public Result regist(User user) {
        return userService.regist(user);
    }

    //登录
    @GetMapping(value = "/login")
    public Result login(Integer userId, String userPassword) {
        return userService.login(userId, userPassword);
    }
}
