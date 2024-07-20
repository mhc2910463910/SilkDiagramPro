package com.lzmhc.silkdiagrampro.mapper;

import com.lzmhc.silkdiagrampro.domain.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface AddressMapperDao {
    @Select("select * from Address where email=#{email}")
    public List<Address> findAddressList(String email);
    @Insert("insert into Address(phone,address,name,email) values(#{phone},#{address},#{name},#{email})")
    public int insertAddress(Address address);
    @Delete("delete from Address where id=#{id}")
    public int deleteAddress(Integer id);
}