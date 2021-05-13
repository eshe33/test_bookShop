package com.shop.controller;

import com.shop.entity.Result;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController{

    @Autowired
    private OrderService orderService;

    // http://localhost:8080/orders/create?aid=28&cids=12&cids=8&cids=10
    @GetMapping("create")
    public Result create(Integer addressId, Integer[] cartIds, Integer userId) {
        return orderService.createOrder(addressId, cartIds, userId);
    }

}

