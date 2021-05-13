package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 省市区数据的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District{

    private Integer id;
    private String parent;
    private String code;
    private String name;


}

