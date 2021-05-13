package com.shop.service.impl;

import com.shop.entity.Book;
import com.shop.entity.Cart;
import com.shop.entity.Result;
import com.shop.mapper.BookMapper;
import com.shop.mapper.CartMapper;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public Result addToCart(Integer userId, Integer bookId, Integer num) {

        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            // -- bookMapper.findBookById()得到商品详情，该数据中包含商品信息
            Book book = bookMapper.findBookById(bookId);
            // -- getCartItem()查询购物车详情
            Cart cartItem = getCartItem(userId, bookId);

            // 判断查询结果是否为null
            if (cartItem == null) {
                // 是：表示该用户的购物车没有该商品，则需要执行insert操作
                // -- 创建新的Cart对象，补全Cart对象的属性
                cartItem = new Cart(null, userId, bookId, book.getBookName(),
                        book.getBookAuthor(), num, book.getBookPrice(), book.getBookType());
                result = insertBookIntoCart(cartItem);
            } else {
                // 否：表示该用户的购物车已有该商品，则需要执行update操作增加数量
                // -- 从查询结果中获取cartId
                Integer cartId = cartItem.getCartId();
                // -- 从查询结果中取出原有数量num，得到新的数量
                Integer total = cartItem.getBookCount() + num;
                // -- 调用updateNumByCid()执行修改数量
                result = updateCountByCartId(cartId, total);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    /**
     * 得到用户的购物车信息
     * @param userId 用户的id
     */
    public Result showCart(Integer userId) {
        Result result= new Result<>();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Cart> cartItems = cartMapper.getCartItemByUserId(userId);
            if(cartItems.isEmpty()){
                result.setMsg("购物车为空");
            } else {
                result.setDetail(cartItems);
                result.setMsg("购物车列表");
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     */
    public Result insertBookIntoCart(Cart cart) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book book = bookMapper.findBookById(cart.getBookId());
            if(book.getBookAmount() < cart.getBookCount()){
                result.setMsg("图书库存不足");
            } else {
                cartMapper.insertBookIntoCart(cart);
                result.setMsg("加入购物车成功");
                result.setSuccess(true);
                cart = cartMapper.getCartItem(cart.getUserId(), cart.getBookId());
                result.setDetail(cart);
            }
        }catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 修改购物车中商品的数量
     * @param cartId          购物车数据id
     * @param num             新的数量
     */
    public Result updateCountByCartId(Integer cartId, Integer num) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book book = bookMapper.findBookById(cartMapper.getCartItemByCartId(cartId).getBookId());
            if(book.getBookAmount() < num){
                result.setMsg("图书库存不足");
            } else {
                cartMapper.updateCountByCartId(cartId, num);
                result.setMsg("修改商品数量成功");
                result.setSuccess(true);
                result.setDetail(cartMapper.getCartItemByCartId(cartId));
            }
        }catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 查询若干个数据id匹配的购物车列表
     * @param cartIds 若干个数据id
     * @param userId 用户的id
     * @return 匹配的购物车列表
     */
    @Override
    public Result getByCartIds(Integer[] cartIds, Integer userId) {

        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        // findByCartIds(cartIds)执行查询，得到列表数据
        List<Cart> carts = cartMapper.findByCartIds(cartIds);

        // 从列表中移除非当前登录用户的数据：在遍历过程中移除集合中的元素，需要使用迭代器
        Iterator<Cart> it = carts.iterator();
        while (it.hasNext()) {
            Cart cart = it.next();
            if (!cart.getUserId().equals(userId)) {
                it.remove();
            }
        }

        result.setDetail(carts);
        result.setSuccess(true);
        // 返回列表
        return result;
    }

    @Override
    public Integer deleteByCartId(Integer cartId) {
        Integer row = cartMapper.deleteByCartId(cartId);
        return row;
    }

    /**
     * 查询某用户在购物车添加的某商品的详情
     * @param userId 用户的id
     * @param bookId 商品的id
     * @return 匹配的购物车详情，如果该用户没有将该商品添加到购物车，则返回null
     */
    public Cart getCartItem(Integer userId, Integer bookId) {
        return cartMapper.getCartItem(userId, bookId);
    }


}
