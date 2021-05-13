package com.shop.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 订单商品实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Integer itemId;
    private Integer orderId;
    private Integer bookId;
    private Integer bookPrice;
    private Integer bookCount;

}
