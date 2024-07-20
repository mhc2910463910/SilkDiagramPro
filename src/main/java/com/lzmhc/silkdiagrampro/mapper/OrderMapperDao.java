package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface OrderMapperDao {
    @Select("select * from Order where orderNum=#{orderNum}")
    Order findOrderByorderNum(String orderNum);//订单号查询

    @Select("select * from Order")
    List<Order> findAllOrder();//查找所有订单

    @Select("select * from Order where userId=#{userId}")
    Order findOrderByuserId(String userId);//通过用户电话查询订单

    @Select("select * from Order where courierNum=#{courierNum}")
    Order findOrderBycourierNum(String courierNum);//查询快递单号

    @Select("select * from Order where orderStatus='ready'")
    List<Order> findReadyOrder();//查询新订单

    @Select("select * from Order where orderStatus='finish'")
    List<Order> findFinishOrder();//查询已发货订单

    @Select("select * from Order where orderStatus='over'")
    List<Order> findOverOrder();//查询已完成的订单

    @Insert("insert into Order(orderNum,userId,orderType,orderId,orderStatus,number,price,dateTimes) values(#{orderNum},#{userId},#{orderType},#{orderId},#{orderStatus},#{number},#{price},#{datetime})")
    int insertOrder(Order order);//创建订单

    @Update("update Order set orderStatus=#{orderStatus},courierNum=#{courierNum} where orderNum=#{orderNum}")
    int updateFinish(@Param("orderStatus") String orderStatus, @Param("courierNum") String courierNum, @Param("orderNum") String orderNum);//订单发货时状态

    @Update("update Order set orderStatus=#{orderStatus} where orderNum=#{orderNum}")
    int updateOver(@Param("orderStatus") String orderStatus, @Param("orderNum") String orderNum);//订单签收时状态

}
