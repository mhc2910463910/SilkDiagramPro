package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.WeiXinUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WeiXinUserMapperDao {
    @Select("select * from LoginInfo where openid=#{openid}")
    public WeiXinUser findWeiXinUser(String openid);
    @Insert("insert into LoginInfo(user_id,avatar,session_key,openid,user_name) values(#{user_id},#{avatar},#{session_key},#{openid},#{user_name})")
    public int insertWeiXinUser(WeiXinUser weiXinUser);
    @Update("update LoginInfo set avatar=#{avatar},user_name=#{user_name} where openid=#{openid}")
    public int updateWeiXinUser(@Param("avatar") String avatar,@Param("user_name") String user_name,@Param("openid") String openid);
    @Update("update LoginInfo set session_key=#{session_key} where openid=#{openid}")
    public int updateSession_key(@Param("session_key")String session_key,@Param("openid") String openid);
}
