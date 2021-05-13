package com.shop.service;

import com.shop.entity.Book;
import com.shop.entity.Result;

public interface BookService {

    /**
     * 得到全部图书信息
     */
    Result getBookList();

    Result getBookByName(String bookName);

    Result getBookByType(String bookType);

    Result getBookByAuthor(String bookAuthor);

    Result getBookTypeList();

    Result addBook(Book book);

    Result updateBook(Book book);

    Result deleteBook(Integer bookId);

    /**
     * 根据图书id得到图书信息
     * @param bookId 商品的id
     */
    Result getBookById(Integer bookId);

    /**
     * 改变商品的库存(管理员操作)
     * @param bookId 商品的id
     * @param newAmount 新的数量
     */
    void updateBookAmount(Integer bookId, Integer newAmount);


    /**
     * 减少商品的库存(下单时调用)
     * @param bookId 商品的id
     * @param num 减少的数量
     */
    Result reduceBookAmount(Integer bookId, Integer num);

}
