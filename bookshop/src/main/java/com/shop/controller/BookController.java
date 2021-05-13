package com.shop.controller;

import com.shop.entity.Book;
import com.shop.entity.Result;
import com.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController    //相当于@Controller+@RequestBody
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/booksById/{bookId}")
    public Result getBookById(@PathVariable("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }


    @GetMapping("/books")
    public Result getBookList(){
        return bookService.getBookList();
    }

    @GetMapping("/booksByName/{bookName}")
    public Result getBookByName(@PathVariable("bookName") String bookName){
        return bookService.getBookByName(bookName);
    }

    @GetMapping("/booksByType/{bookType}")
    public Result getBookByType(@PathVariable("bookType") String bookType){
        return bookService.getBookByType(bookType);
    }

    @GetMapping("/booksByAuthor/{author}")
    public Result getBookByAuthor(@PathVariable("author") String bookAuthor){
        return bookService.getBookByAuthor(bookAuthor);
    }

    @GetMapping("/bookTypes")
    public Result getBookTypeList(){
        return bookService.getBookTypeList();
    }

    @PostMapping("/book")
    public Result addBook(Book book){
        return bookService.addBook(book);
    }

    @DeleteMapping("/book/{bookId}")
    public Result deleteBook(@PathVariable("bookId") Integer bookId){
        return bookService.deleteBook(bookId);
    }

    @PutMapping("/book")
    public Result updateBook(Book book){
        return bookService.updateBook(book);
    }

}
 