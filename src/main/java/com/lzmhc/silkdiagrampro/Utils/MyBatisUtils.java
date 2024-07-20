package com.lzmhc.silkdiagrampro.Utils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory=null;
    static{
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);

    }
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
    public static SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }
}