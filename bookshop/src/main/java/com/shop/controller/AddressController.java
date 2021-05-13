package com.shop.controller;


import com.shop.entity.Address;
import com.shop.entity.Result;
import com.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController    //相当于@Controller+@RequestBody
@RequestMapping("/address")
@ResponseBody
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/add")
    Result addAddress(Integer userId, Address address){
        return addressService.addAddress(userId, address);
    }

    // http://localhost:8080/address
    @GetMapping({"", "/"})
    Result getByUid(Integer userId) {
        return addressService.getByUserId(userId);
    }

    // http://localhost:8080/address/setDefault
    @GetMapping("/setDefault")
    Result setDefault(Integer addressId, Integer userId) {
        return  addressService.setDefault(addressId, userId);
    }

    // http://localhost:8080/address/delete
    @GetMapping("/delete")
    public Result delete(Integer addressId, Integer userId) {
        return addressService.deleteAddress(addressId, userId);
    }

    @GetMapping("/update")
    public Result update(Integer addressId, Integer userId, Address address) {
        return addressService.updateAddress(addressId, userId, address);
    }



}
