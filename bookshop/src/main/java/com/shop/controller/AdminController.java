package com.shop.controller;

import com.shop.entity.Result;
import com.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping(value = "/login")
    public Result login(Integer adminId, String adminPassword) {
        return adminService.login(adminId,adminPassword);
    }

}
