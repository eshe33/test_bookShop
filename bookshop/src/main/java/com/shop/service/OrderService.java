package com.shop.service;

import com.shop.entity.Order;
import com.shop.entity.Result;

public interface OrderService {

    Result createOrder(Integer addressId, Integer[] cartIds, Integer userId);


}
