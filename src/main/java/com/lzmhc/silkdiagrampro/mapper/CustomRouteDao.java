package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.CustomRoute;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CustomRouteDao {
    @Select("select * from CustomRoute where id=#{id}")
    CustomRoute findCustomById(String id);
    @Select("select * from CustomRoute where type=#{type}")
    List<CustomRoute> findRouteByType(String type);
}
