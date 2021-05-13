package com.shop.mapper;


import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 处理订单数据和订单商品数据的持久层接口
 */
@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface OrderMapper {
    /**
    * 插入订单数据
    * @param order 订单数据
    * @return 受影响的行数
    */
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    @Insert(value = "INSERT INTO t_order (user_id, recv_name, recv_phone, recv_province," +
        " recv_city, recv_area, recv_address, order_time, pay_time, total_price, order_state) " +
        " VALUES (#{userId}, #{recvName}, #{recvPhone}, #{recvProvince},#{recvCity}, " +
        " #{recvArea}, #{recvAddress}, #{orderTime}, #{payTime}, #{totalPrice}, #{orderState})" )
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    @Insert(value = "INSERT INTO t_order_item (order_id, book_id, book_price, book_count) " +
            "VALUES ( #{orderId}, #{bookId}, #{bookPrice}, #{bookCount})" )
    Integer insertOrderItem(OrderItem orderItem);

}
