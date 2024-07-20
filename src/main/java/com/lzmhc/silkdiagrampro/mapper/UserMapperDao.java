package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapperDao {
//    select * from UserInfo where email='2910463910@qq.com';
    @Select("select * from UserInfo where email=#{email}")
    User findUserByEmail(String email);
    @Insert("insert into UserInfo(email,password) values(#{email},#{password})")
    int insertUser(User user);
    @Update("update UserInfo set username=#{username} where email=#{email}")
    int setUserName(User user);
    @Update("update UserInfo set phone=#{phone} where email=#{email}")
    int setPhone(User user);
    @Update("update UserInfo set avatar=#{avatar} where email=#{email}")
    int setAvatar(User user);
    @Update("update UserInfo set password=#{password} where email=#{email}")
    int setPassword(User user);
    @Select("select * from UserInfo")
    List<User> getUserList();
    @Select("select * from UserInfo where phone=#{phone}")
    List<User> findUserByPhone(String phone);
}
