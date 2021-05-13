package com.shop.controller;

import com.shop.entity.Result;
import com.shop.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("districts")
public class DistrictController{

    @Autowired
    private DistrictService districtService;

    // http://localhost:8080/districts?parent=86
    // http://localhost:8080/districts/?parent=86
    @GetMapping({"", "/"})
    public Result getByParent(String parent) {
        return districtService.getByParent(parent);
    }

}

