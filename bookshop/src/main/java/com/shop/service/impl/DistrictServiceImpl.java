package com.shop.service.impl;

import com.shop.entity.District;
import com.shop.entity.Result;
import com.shop.mapper.DistrictMapper;
import com.shop.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    DistrictMapper districtMapper;


    @Override
    public Result getByParent(String parent) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<District> list = districtMapper.findByParent(parent);
            for (District district : list) {
                district.setId(null);
                district.setParent(null);
            }
            if(list.isEmpty()) {
                result.setMsg("查找失败");
            }
            else {
                result.setDetail(list);
                result.setSuccess(true);
                result.setMsg("查找成功");
            }
        } catch (Exception e) {
            result.setDetail(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Result getNameByCode(String code) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            String name = districtMapper.findNameByCode(code);
            if(name == null) {
                result.setMsg("查找失败");
            }
            else {
                result.setDetail(name);
                result.setSuccess(true);
                result.setMsg("查找成功");
            }
        } catch (Exception e) {
            result.setDetail(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
