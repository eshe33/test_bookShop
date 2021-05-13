package com.shop.mapper;

import com.shop.entity.Book;
import com.shop.entity.Cart;
import com.shop.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface CartMapper {

    @Insert(value = "insert into cart values(#{cartId},#{userId},#{bookId}," +
            "#{bookName},#{bookAuthor},#{bookCount},#{bookPrice},#{bookType})")
    @ResultMap(value = "CartEntityMap")
    Integer insertBookIntoCart(Cart cart);

    @Update(value = "update cart set book_count=#{num} where cart_id=#{cartId}")
    Integer updateCountByCartId(Integer cartId, Integer num);

    @Select(value = "select * from cart where user_id=#{userId} and book_id=#{bookId}")
    @ResultMap(value = "CartEntityMap")
    Cart getCartItem(Integer userId,Integer bookId);

    @Select(value = "select * from cart where user_id=#{userId}")
    @ResultMap(value = "CartEntityMap")
    List<Cart> getCartItemByUserId(Integer userId);

    @Select(value = "select * from cart where cart_id=#{cartId}")
    @ResultMap(value = "CartEntityMap")
    Cart getCartItemByCartId(Integer cartId);


    /**
     * 查询若干个数据id匹配的购物车列表
     * @param cartIds 若干个数据id
     * @return 匹配的购物车列表
     */
    List<Cart> findByCartIds(Integer[] cartIds);


    /**
     * 删除购物车内书籍
     * @param cartId 若干个数据id
     * @return 匹配的购物车列表
     */
    @Delete(value = "delete from cart where cart_id=#{cartId}")
    Integer deleteByCartId(Integer cartId);
}
