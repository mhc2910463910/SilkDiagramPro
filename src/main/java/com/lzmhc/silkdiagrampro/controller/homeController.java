package com.lzmhc.silkdiagrampro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {
    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }
}
