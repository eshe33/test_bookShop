package com.shop;

import com.shop.entity.*;
import com.shop.mapper.*;
import com.shop.service.AddressService;
import com.shop.service.CartService;
import com.shop.service.DistrictService;
import com.shop.service.OrderService;
import javafx.scene.control.ChoiceBoxBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class BookshopApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    AddressService addressService;

    @Autowired
    CartService cartService;

    @Autowired
    DistrictMapper districtMapper;

    @Autowired
    DistrictService districtService;
    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        Book book = new Book(3,"呐喊","鲁迅",120,35,"文学",0);
        System.out.println(bookMapper.updateBook(book));
//2	狂人日记	鲁迅	119	30	文学
    }

    @Test
    void test2(){
        System.out.println(bookMapper.getBookTypeList());
    }

}
