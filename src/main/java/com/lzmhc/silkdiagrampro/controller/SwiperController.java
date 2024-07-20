package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.domain.Route;
import com.lzmhc.silkdiagrampro.mapper.RouteMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SwiperController {
    @Autowired
    private RouteMapperDao routeService;
    @ResponseBody
    @RequestMapping("/getSwiper")
    public List<String> getSwiper(){
        List<Route> allRoute = routeService.findAllRoute();
        List<String> swiperList=new ArrayList<>();
        for(Route route:allRoute){
            swiperList.add(route.getRouteImg().split(";")[1]);
        }
        return swiperList;
    }

}
