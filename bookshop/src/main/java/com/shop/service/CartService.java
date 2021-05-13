package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.Result;

public interface CartService {

    /**
     * 将商品添加到购物车
     * @param userId 用户的id
     * @param bookId 商品的id
     * @param num 添加的商品数量
     */
    Result addToCart(Integer userId,  Integer bookId, Integer num);

    /**
     * 得到用户的购物车信息
     * @param userId 用户的id
     */
    Result showCart(Integer userId);

    /**
     * 修改购物车内图书数量
     * @param cartId 购物车id
     */
    Result updateCountByCartId(Integer cartId, Integer num);

    /**
     * 查询若干个数据id匹配的购物车列表
     * @param cartIds 若干个数据id
     * @param userId 用户的id
     * @return 匹配的购物车列表
     */
    Result getByCartIds(Integer[] cartIds, Integer userId);

    /**
     * 删除某用户的若干个购物车数据
     * @param cartId 被删除的购物车数据的id
     */
    Integer deleteByCartId(Integer cartId);

}
