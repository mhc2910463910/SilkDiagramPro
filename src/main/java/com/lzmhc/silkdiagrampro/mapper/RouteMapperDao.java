package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.Route;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface RouteMapperDao {
    @Select("select * from Route where RouteId=#{id}")
    Route findUserById(String id);
    @Select("select * from Route")
    List<Route> findAllRoute();
    @Insert("insert into Route(RouteName,RouteText,RouteImg,RoutePrice,RoutePosition) values(#{RouteName},#{RouteText},#{RouteImg},#{RoutePrice},#{RoutePosition})")
    int insertRoute(Route route);

    @Delete("delete from Route where RouteId=#{id}")
    int deleteRoute(String id);
    @Update("update Route set RouteName=#{name},RouteText=#{content},RoutePrice=#{price},RoutePosition=#{position},RouteImg=#{imgList} where RouteId=#{id}")
    int updateRoute(@Param("id") String id, @Param("name") String name, @Param("content") String content, @Param("price")String price,@Param("position")String position,@Param("imgList") String imgList);
    @Select("select * from Route where RouteName=#{routeName}")
    List<Route> findRouteByName(String routeName);
}
