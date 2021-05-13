package com.shop.mapper;


import com.shop.entity.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Struct;
import java.util.List;

@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface BookMapper {
    @Select(value = "select * from book")
    @ResultMap(value = "BookEntityMap")
    List<Book> getBookList();

    @Select(value = "select DISTINCT book_type from book ")
    List<String> getBookTypeList();

    @Select(value = "select * from book where book_id=#{bookId}")
    @ResultMap(value = "BookEntityMap")
    Book findBookById(Integer bookId);

    @Select(value = "select * from book where book_name like concat('%',#{bookName},'%')")
    @ResultMap(value = "BookEntityMap")
    List<Book> findBookByName(String bookName);

    @Select(value = "select * from book where book_author like concat('%',#{bookAuthor},'%')")
    @ResultMap(value = "BookEntityMap")
    List<Book> findBookByAuthor(String bookAuthor);

    @Select(value = "select * from book where book_type = #{bookType}")
    @ResultMap(value = "BookEntityMap")
    List<Book> findBookByType(String bookType);

    @Insert(value = "insert into book values(#{bookId}, #{bookName}, #{bookAuthor}, #{bookAmount}, #{bookPrice}, #{bookType}, 0)")
    Integer insertBook(Book book);

    @Update(value = "update book set book_amount=#{newAmount} where book_id=#{bookId}")
    Integer updateBookAmount(Integer bookId, Integer newAmount);

    @Update(value = "update book set book_name = #{bookName},  " +
                    "book_author = #{bookAuthor}, book_amount = #{bookAmount}, " +
                    "book_price = #{bookPrice}, book_type = #{bookType} " +
                    "where book_id=#{bookId}")
    Integer updateBook(Book book);

    @Update(value = "update book set book_state = 1 where book_id=#{bookId}")
    Integer deleteBookById(Integer bookId);
}
