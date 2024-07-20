package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.Utils.*;
import com.lzmhc.silkdiagrampro.mapper.AddressMapperDao;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lzmhc.silkdiagrampro.domain.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AddressController {
    @Autowired
    private AddressMapperDao addressService;
    @Autowired
    private TokenUtil token;
    @ResponseBody
    @RequestMapping(value = "/getAddress")
    public List<Address> getAddress(String tk){
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie=cookies[1];
//        String name=cookie.getName();
//        String tk=cookie.getValue();
        User user=token.getUser(tk);
        List<Address> addressList=new ArrayList<>();
        addressList=addressService.findAddressList(user.getEmail());
        return addressList;
    }
    @ResponseBody
    @RequestMapping("/insertAddress")
    public String insertAddress(String tk,String address,String name,String phone){
//        System.out.println(request);
////        System.out.println(address+" "+name);
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie=cookies[1];
//        String tk=cookie.getValue();
        System.out.println(tk+" "+address+" "+name+" "+phone);
        User user=token.getUser(tk);
        Address ad=new Address();
        ad.setPhone(phone);
        ad.setName(name);
        ad.setAddress(address);
        ad.setEmail(user.getEmail());
        int i=addressService.insertAddress(ad);
        if(i==1){
            return "添加成功";
        }else{
            return "失败";
        }
    }
    @RequestMapping("delAddress")
    public boolean deleteAddress(String id){
        return addressService.deleteAddress(Integer.valueOf(id))==1;

    }
}
