package com.lzmhc.silkdiagrampro.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzmhc.silkdiagrampro.domain.User;
import com.lzmhc.silkdiagrampro.domain.WeiXinUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service(value = "token")
public class TokenUtil {
    private String secret="iwqjhda8232bjgh432[cicada-smile]";
//    @Value("${jwt.secret}")
    public void setSecret(String secret_) {
        secret = secret_;
    }

    private long expire=3600;
//    @Value("${jwt.expire}")
    public void setExpire(Long expire_) {
        expire = expire_;
    }

    private String header="token";
//    @Value("${jwt.header}")
    public void setHeader(String header_) {
        header = header_;
    }
    /*
     * 根据身份ID标识，生成Token
     */
    public String getToken (User user, String key){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String,Object> maps = new HashMap<>();
        maps.put("user",user);
        maps.put("key",key);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(JSONObject.toJSONString(maps))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /*
     * 获取 Token 中注册信息
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public WeiXinUser getWeiXinUser(String tk){
        Claims tokenClaim = (Claims) this.getTokenClaim(tk);
        Map<String, WeiXinUser> map= JSON.parseObject((String) tokenClaim.get("sub"), Map.class);
        WeiXinUser user=  JSON.parseObject(String.valueOf(map.get("user")),WeiXinUser.class);
        return user;
    }
    public User getUser(String tk){
        Claims tokenClaim = (Claims) this.getTokenClaim(tk);
        System.out.println("tokenClaim : "+tokenClaim);
        Map<String, User> map= JSON.parseObject((String) tokenClaim.get("sub"), Map.class);
        User user=  JSON.parseObject(String.valueOf(map.get("user")),User.class);
        return user;
    }
}
