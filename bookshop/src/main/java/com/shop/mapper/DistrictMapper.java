package com.shop.mapper;

import com.shop.entity.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface DistrictMapper {

    @Select(value = " SELECT * FROM t_dict_district WHERE parent=#{parent} ORDER BY code")
    List<District> findByParent(String parent);

    @Select(value = " SELECT name FROM t_dict_district WHERE code=#{code}")
    String findNameByCode(String code);

}
