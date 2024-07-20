package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.domain.CustomRoute;
import com.lzmhc.silkdiagrampro.domain.Route;
import com.lzmhc.silkdiagrampro.mapper.CustomRouteDao;
import com.lzmhc.silkdiagrampro.mapper.RouteMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
public class RouteController {
    @Autowired
    private RouteMapperDao routeService;
    @Autowired
    private CustomRouteDao customRouteService;
    @RequestMapping("/routeList")
    public String getrouteList(Model model){
        List<Route> routeList=routeService.findAllRoute();
        model.addAttribute("routeList",routeList);
        return "routeList";
    }
    @RequestMapping("/updateRoute")
    public String updateRoute(String id,String name, String content, String price, String position,@RequestParam MultipartFile[] fileinput) throws IOException {
        System.out.println(id+ " " +name+" "+content+" "+price+" "+position+" "+fileinput);
        String path = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        String uuid = UUID.randomUUID().toString().replace("-","");
        String imgList=new String();
        for(MultipartFile multipartFile:fileinput){
            String url=uuid+"_"+multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(path,url));
            imgList+=url+';';
        }
        imgList+="null";
        routeService.updateRoute(id,name,content,price,position,imgList);
        return "routeList";
    }
    @RequestMapping("/addRoute")
    public String getaddRoute(){
        return "addRoute";
    }
    @RequestMapping(value = "/insertRoute",method = {RequestMethod.POST})
    public String addRoute(String name, String content, String price, String position,@RequestParam MultipartFile[] fileinput) throws IOException {

//        MultipartFile file=fileinput;
        System.out.println(name+" "+content+" "+price+" "+position+" "+fileinput);
        String path = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        String uuid = UUID.randomUUID().toString().replace("-","");
        String imgList=new String();
        for(MultipartFile multipartFile:fileinput){
            String url=uuid+"_"+multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(path,url));
            imgList+=url+';';
//            System.out.println(imgList);
        }
        imgList+="null";
        Route route=new Route();
        route.setRouteName(name);
        route.setRouteText(content);
        route.setRoutePrice(Double.parseDouble(price));
        route.setRoutePosition(position);
        route.setRouteImg(imgList);
        routeService.insertRoute(route);
        return "routeList";
    }
    @ResponseBody
    @RequestMapping("/getRouteList")
    public List<Route> getRouteList(Model model){
        List<Route> routeList=routeService.findAllRoute();
//        System.out.println(routeList);
        return routeList;
    }
    @ResponseBody
    @RequestMapping("/findRoute")
    public Route findRoute(String id){
        Route route=routeService.findUserById(id);
        return route;
    }
    @ResponseBody
    @RequestMapping("/getRouteInfo")
    public CustomRoute getRouteInfo(String routeType, String position, String typ){
        System.out.println(routeType+" "+position+" "+typ);
        List<CustomRoute> routeByName = customRouteService.findRouteByType(routeType);
//        System.out.println(routeByName);
        Random df = new Random();
//引用nextInt()方法
        int number = df.nextInt(routeByName.size());
        System.out.println(number);
        return routeByName.get(number);
    }
    @RequestMapping("/deleteRouteByid")
    public String deleteRouteByid(String id){
        int i = routeService.deleteRoute(id);
        return "routeList";
    }
    @RequestMapping("/getRouteByid")
    public String getRouteByid(Model model,String id){
        Route route = routeService.findUserById(id);
        model.addAttribute("route",route);
        return "updateRoute";
    }
}
