package com.lzmhc.silkdiagrampro.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Order {
    private String orderNum;//订单编号
    private String userId;//用户电话
    private String orderType;//商品类型
    private String orderId;//商品id
    private String orderStatus;//订单状态
    //ready 已付款
    //finish 已发货
    //over 已签收
    private String courierNum;//快递单号
    private int number;//商品数量
    private double price;//订单金额
    private Timestamp datetime;//订单创建时间

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNum='" + orderNum + '\'' +
                ", userId='" + userId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", courierNum='" + courierNum + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", datetime=" + datetime +
                '}';
    }
}
