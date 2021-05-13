package com.shop.service;

import com.shop.entity.Result;

public interface AdminService {
    Result login(Integer adminId, String password);
}
