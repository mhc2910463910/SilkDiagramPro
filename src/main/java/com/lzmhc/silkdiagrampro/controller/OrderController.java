package com.lzmhc.silkdiagrampro.controller;


import com.lzmhc.silkdiagrampro.Utils.TokenUtil;
import com.lzmhc.silkdiagrampro.domain.Commodity;
import com.lzmhc.silkdiagrampro.domain.Order;
import com.lzmhc.silkdiagrampro.domain.Route;
import com.lzmhc.silkdiagrampro.domain.WeiXinUser;
import com.lzmhc.silkdiagrampro.mapper.CommodityMapperDao;
import com.lzmhc.silkdiagrampro.mapper.OrderMapperDao;
import com.lzmhc.silkdiagrampro.mapper.RouteMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private TokenUtil token;
    @Autowired
    private OrderMapperDao orderService;
    @Autowired
    private CommodityMapperDao commodityService;
    @Autowired
    private RouteMapperDao routeService;
    @ResponseBody
    @RequestMapping("/createOrder")
    public Order createOrder(String tk, String orderType, String orderId, String number){
        System.out.println("生成订单:"+tk+" "+orderType+" "+orderId+" "+number);
        double price=0;
        if(orderType.equals("commodity")){
            Commodity commodity = commodityService.findCommodityById(orderId);
            price=commodity.getPrice();
        }else if(orderType.equals("route")){
            Route route = routeService.findUserById(orderId);
            price=route.getRoutePrice();
        }
        Order order=new Order();
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String str = df.format(date);//获取String类型的时间
        Timestamp timestamp = new Timestamp(date.getTime());
        WeiXinUser user=token.getWeiXinUser(tk);
        order.setOrderNum("SD"+str);
        order.setUserId(user.getUser_id());
        order.setOrderType(orderType);
        order.setOrderId(orderId);
        order.setOrderStatus("ready");
        order.setNumber(Integer.parseInt(number));
        order.setPrice(price);
        order.setDatetime(timestamp);
        int i = orderService.insertOrder(order);
        return order;
    }
    @ResponseBody
    @RequestMapping("/getOrderList")
    public List<Order> getOrderList(){
        List<Order> allOrder = orderService.findAllOrder();
        return allOrder;
    }
    @ResponseBody
    @RequestMapping("/getReadyOrderList")
    public List<Order> getReadyOrderList(){
        List<Order> allOrder = orderService.findReadyOrder();
        return allOrder;
    }
    @ResponseBody
    @RequestMapping("/getFinishOrderList")
    public List<Order> getFinishOrderList(){
        List<Order> allOrder = orderService.findFinishOrder();
        return allOrder;
    }
    @ResponseBody
    @RequestMapping("/getOverOrderList")
    public List<Order> getOverOrderList(){
        List<Order> allOrder = orderService.findOverOrder();
        return allOrder;
    }
    @ResponseBody
    @RequestMapping("/updateFinish")
    public Order updateFinish(String orderNum,String courierNum){
        orderService.updateFinish("finish",courierNum, orderNum);
        Order order=orderService.findOrderByorderNum(orderNum);
        return order;
    }
    @ResponseBody
    @RequestMapping("/updateOver")
    public Order updateOver(String orderNum){
        orderService.updateOver("over",orderNum);
        Order order=orderService.findOrderByorderNum(orderNum);
        return order;
    }
}
