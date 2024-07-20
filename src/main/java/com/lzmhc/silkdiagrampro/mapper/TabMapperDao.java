package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.Tab;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TabMapperDao {
    @Select("select * from TypeList")
    public List<Tab> getTabList();
    @Select("select id from TypeList where name=#{name}")
    public Integer findByname(String name);
    @Select("select * from TypeList where id=#{id}")
    public Tab getTabByid(String id);
    @Insert("insert into TypeList(name) value(#{name})")
    public int insertTab(Tab tab);
    @Delete("delete from TypeList where id=#{id}")
    public int deleteTab(Integer id);

    @Update("update TypeList set icon=#{icon} where id=#{id}")
    public int updateIcon(String id,String icon);
}
