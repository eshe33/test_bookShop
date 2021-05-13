package com.shop.service.impl;

import com.shop.entity.Book;
import com.shop.entity.Result;
import com.shop.entity.User;
import com.shop.mapper.BookMapper;
import com.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public Result getBookList() {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Book> books = bookMapper.getBookList();
            for (Book book : books) {
                if(book.getBookState() == 1){
                    books.remove(book);
                }
            }
            if(books.isEmpty()) {
                result.setMsg("不存在匹配图书");
            } else {
                result.setMsg("已寻找到匹配图书");
                result.setSuccess(true);
                result.setDetail(books);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getBookByName(String bookName) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Book> books = bookMapper.findBookByName(bookName);
            for (Book book : books) {
                if(book.getBookState() == 1){
                    books.remove(book);
                }
            }
            if(books.isEmpty()) {
                result.setMsg("不存在匹配图书");
            } else {
                result.setMsg("已寻找到匹配图书");
                result.setSuccess(true);
                result.setDetail(books);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getBookByType(String bookType) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Book> books = bookMapper.findBookByType(bookType);
            for (Book book : books) {
                if(book.getBookState() == 1){
                    books.remove(book);
                }
            }
            if(books.isEmpty()) {
                result.setMsg("不存在匹配图书");
            } else {
                result.setMsg("已寻找到匹配图书");
                result.setSuccess(true);
                result.setDetail(books);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getBookByAuthor(String bookAuthor) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<Book> books = bookMapper.findBookByAuthor(bookAuthor);
            for (Book book : books) {
                if(book.getBookState() == 1){
                    books.remove(book);
                }
            }
            if(books.isEmpty()) {
                result.setMsg("不存在匹配图书");
            } else {
                result.setMsg("已寻找到匹配图书");
                result.setSuccess(true);
                result.setDetail(books);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getBookTypeList() {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            List<String> list = bookMapper.getBookTypeList();
            if(list.isEmpty()) {
                result.setMsg("不存在匹配图书");
            } else {
                result.setMsg("已寻找到匹配图书");
                result.setSuccess(true);
                result.setDetail(list);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result addBook(Book book) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book existBook = bookMapper.findBookById(book.getBookId());
            if(existBook != null){
                //如果图书ID已存在
                result.setMsg("图书ID重复");
            }else{
                Integer rows = bookMapper.insertBook(book);
                if (rows != 0){
                    result.setMsg("图书添加成功");
                    result.setSuccess(true);
                    result.setDetail(book);
                }
                else result.setMsg("图书添加失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result updateBook(Book book) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book existBook = bookMapper.findBookById(book.getBookId());
            if(existBook == null){
                result.setMsg("该图书不存在");
            }else{
                Integer flag = bookMapper.updateBook(book);
                if (flag == 0){
                    result.setMsg("图书信息更新成功");
                    result.setSuccess(true);
                    result.setDetail(book);
                }
                else result.setMsg("图书信息更新失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result deleteBook(Integer bookId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book book = bookMapper.findBookById(bookId);
            if(book == null){
                result.setMsg("该图书不存在");
            }else{
                Integer flag = book.getBookState();
                if (flag == 0){
                    bookMapper.deleteBookById(bookId);
                    result.setMsg("图书下架成功");
                    result.setSuccess(true);
                    result.setDetail(book);
                }
                else result.setMsg("该图书已下架");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getBookById(Integer bookId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book book = bookMapper.findBookById(bookId);
            if(book == null){
                result.setMsg("该图书不存在");
            }else{
                Integer flag = book.getBookState();
                if (flag == 0){
                    result.setMsg("查找成功");
                    result.setSuccess(true);
                    result.setDetail(book);
                }
                else result.setMsg("该图书已下架");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateBookAmount(Integer bookId, Integer newAmount) {
        bookMapper.updateBookAmount(bookId, newAmount);
    }

    @Override
    public Result reduceBookAmount(Integer bookId, Integer num) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Book book = bookMapper.findBookById(bookId);
            if(book == null){
                result.setMsg("该图书不存在");
            }else if (book.getBookAmount() < num){
                result.setMsg("该图书库存不足");
            }
            else {
                bookMapper.updateBookAmount(bookId, book.getBookAmount() - num);
                result.setMsg("库存减少成功");
                result.setSuccess(true);
                result.setDetail(book.getBookAmount() - num);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
