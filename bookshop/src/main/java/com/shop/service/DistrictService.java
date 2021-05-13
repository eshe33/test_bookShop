package com.shop.service;

import com.shop.entity.District;
import com.shop.entity.Result;

import java.util.List;

public interface DistrictService {

    Result getByParent(String parent);

    Result getNameByCode(String code);
}
