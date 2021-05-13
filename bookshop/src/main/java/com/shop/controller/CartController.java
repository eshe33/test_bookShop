package com.shop.controller;

import com.shop.entity.Book;
import com.shop.entity.Result;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController    //相当于@Controller+@RequestBody
@RequestMapping("/cart")
@ResponseBody
public class CartController {

    @Autowired
    CartService cartService;

    //localhost:8080/cart/add?userId=13&bookId=1&num=10
    @GetMapping("add")
    public Result addToCart(Integer userId, Integer bookId, Integer num) {
        return cartService.addToCart(userId, bookId, num);
    }

    @GetMapping("show")
    public Result showCart(Integer userId) {
        return cartService.showCart(userId);
    }

    @GetMapping("update")
    public Result updateCountOfBook(Integer cartId, Integer num) {
        return cartService.updateCountByCartId(cartId, num);
    }

    // http://localhost:8080/cart/get_by_cartIds?cartIds=10&cartIds=12&userId=13
    @GetMapping("get_by_cartIds")
    public Result getByCids(Integer[] cartIds, Integer userId) {
        return cartService.getByCartIds(cartIds, userId);
    }

}
