package com.lzmhc.silkdiagrampro.mapper;


import com.lzmhc.silkdiagrampro.domain.Commodity;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CommodityMapperDao {
    @Select("select * from Commodity where id=#{id}")
    Commodity findCommodityById(String id);
    @Select("select * from Commodity")
    List<Commodity> findAllCommodity();
    @Insert("insert into Commodity(name,type,price,img1,img2,img3) values(#{name},#{type},#{price},#{img1},#{img2},#{img3})")
    int insertCommodity(Commodity commodity);
    @Delete("delete from Commodity where id=#{id}")
    int deleteCommodity(String id);
    @Update("update Commodity set name=#{name},type=#{type},price=#{price},img1=#{img1},img2=#{img2},img3=#{img3} where id=#{id}")
    int updateCommodity(@Param("id") String id,@Param("name") String name,@Param("type") String type,@Param("price") String price,String img1,String img2,String img3);
    @Select("select * from Commodity where type=#{type}")
    List<Commodity> findCommodityByTypeName(String type);
}
