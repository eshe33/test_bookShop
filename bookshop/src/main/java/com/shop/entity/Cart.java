package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Integer cartId;
    private Integer userId;
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private Integer bookCount;
    private Integer bookPrice;
    private String bookType;
}
