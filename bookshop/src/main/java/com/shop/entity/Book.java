package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private Integer bookAmount;
    private Integer bookPrice;
    private String bookType;
    private Integer bookState;

}
