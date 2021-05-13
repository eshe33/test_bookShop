package com.shop.service.impl;

import com.shop.entity.*;
import com.shop.mapper.OrderMapper;
import com.shop.service.AddressService;
import com.shop.service.BookService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AddressService addressService;
    @Autowired
    CartService cartService;
    @Autowired
    BookService bookService;

    /**
     * 插入订单数据
     * @param order 订单数据
     */
    private void insertOrder(Order order) {
        Integer rows = orderMapper.insertOrder(order);
    }

    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     */
    private void insertOrderItem(OrderItem orderItem) {
        Integer rows = orderMapper.insertOrderItem(orderItem);
    }


    @Override
    public Result createOrder(Integer addressId, Integer[] cartIds, Integer userId) {
        // 创建当前时间对象now
        Date now = new Date();

        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);


        // 根据参数aid调用addressService.getByAddressId()查询收货地址详情
        Address address = (Address)addressService.getByAddressId(addressId).getDetail();
        System.out.println(address);
        // 根据参数carts调用cartService.getByCartIds()，得到List<Cart>
        List<Cart> carts = (List<Cart>) cartService.getByCartIds(cartIds, userId).getDetail();
        // 定义totalPrice变量
        Integer totalPrice = 0;
        // 遍历以上查询到的List<Cart>，计算出totalPrice
        for (Cart cart : carts) {
            totalPrice += cart.getBookPrice() * cart.getBookCount();
        }

        // 创建Order对象
        Order order = new Order();
        // 补全Order对象中的属性：uid > 参数uid
        order.setUserId(userId);
        // 补全Order对象中的属性：recv* > 收货地址详情
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getTelephone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddressDetail());
        // 补全Order对象中的属性：orderTime > now
        order.setOrderTime(now);
        // 补全Order对象中的属性：payTime > null
        // 补全Order对象中的属性：totalPrice > totalPrice
        order.setTotalPrice(totalPrice);
        // 补全Order对象中的属性：status > 0
        order.setOrderState(0);

        // 调用insertOrder(Order order)插入订单数据
        insertOrder(order);

        // 遍历查询到的List<Cart>
        for (Cart cart : carts) {
            // -- 创建OrderItem对象
            OrderItem orderItem = new OrderItem();
            // -- 补全OrderItem对象中的属性：oid > order.getOid();
            orderItem.setOrderId(order.getOrderId());
            // -- 补全OrderItem对象中的属性
            orderItem.setBookId(cart.getBookId());
            orderItem.setBookPrice(cart.getBookPrice());
            orderItem.setBookCount(cart.getBookCount());
            // -- 多次调用insertOrderItem(OrderItem orderItem)插入订单商品数据
            insertOrderItem(orderItem);
            if(bookService.reduceBookAmount(cart.getBookId(), cart.getBookCount()).getDetail()==null){
                result.setMsg("库存不足");
                return result;
            }
        }

        for (Integer cartId : cartIds) {
            cartService.deleteByCartId(cartId);
        }





        // 返回订单数据
        result.setMsg("订单创建成功");
        result.setDetail(order);
        result.setSuccess(true);
        return result;
    }
}
