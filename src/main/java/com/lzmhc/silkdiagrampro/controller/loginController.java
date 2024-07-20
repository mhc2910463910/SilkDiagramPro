//package com.lzmhc.silkdiagrampro.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//
//import com.lzmhc.silkdiagrampro.Utils.JsonSerilizable;
//import com.lzmhc.silkdiagrampro.Utils.TokenUtil;
//import com.lzmhc.silkdiagrampro.Utils.WechatUtil;
//import com.lzmhc.silkdiagrampro.domain.User;
//import com.lzmhc.silkdiagrampro.domain.WeiXinUser;
//import com.lzmhc.silkdiagrampro.mapper.UserMapperDao;
//import com.lzmhc.silkdiagrampro.mapper.WeiXinUserMapperDao;
//import io.jsonwebtoken.Claims;
////import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.crypto.NoSuchPaddingException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64; // 这个包不限，LZ 正好项目有个支持解密顺手用了
//import java.security.spec.AlgorithmParameterSpec;
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//@Controller
//public class loginController {
//    @Autowired
//    private TokenUtil token;
//    @Autowired
//    private UserMapperDao userService;
//    @Autowired
//    private WeiXinUserMapperDao weiXinUserService;
//    @RequestMapping("/Login")
//    public String login(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//        //登陆
//        String name=httpServletRequest.getParameter("name");
//        String password=httpServletRequest.getParameter("password");
//        System.out.println(name+" "+password);
//        User user=userService.findUserByName(name);
//        if(user!=null && user.getPassword().equals(password)){
//            HttpSession httpSession=httpServletRequest.getSession();
//            httpSession.setAttribute("user",user);
//            Cookie cookie=new Cookie("JSESSIONID",httpSession.getId());
//            cookie.setMaxAge(60*60*24);
//            httpServletResponse.addCookie(cookie);
//            return "home";
//        }else{
//            model.addAttribute("info","用户不存在或密码错误");
//            return "index";
//        }
//    }
////    退出
//    @RequestMapping("/ExitUserServlet")
//    public String Exit(HttpServletRequest httpServletRequest){
//        //退出登陆
//        HttpSession httpSession=httpServletRequest.getSession();
//        User user= (User) httpSession.getAttribute("user");
//        if(user!=null){
//            httpSession.removeAttribute("user");
//        }
//        return "index";
//    }
//    @ResponseBody
//    @RequestMapping(value = "/wx/updateInfo",method = RequestMethod.GET)
//    public Map<String, Object> user_update(String tk, String user_name, String avatar){
//        System.out.println("token="+tk);
//        System.out.println("user_name="+user_name);
//        System.out.println("avatar="+avatar);
//        Claims tokenClaim = (Claims) token.getTokenClaim(tk);
////        System.out.println(tokenClaim);
////        System.out.println(tokenClaim.get("sub"));
//        Map<String, WeiXinUser> map= JSON.parseObject((String) tokenClaim.get("sub"), Map.class);
////        System.out.println(tokenClaim.get("sessionKey",String.class));
////        System.out.println(tokenClaim.get("user",WeiXinUser.class));
////        System.out.println(map);
//        WeiXinUser user=  JSON.parseObject(String.valueOf(map.get("user")),WeiXinUser.class);
//        user.setAvatar(avatar);
//        user.setUser_name(user_name);
//        System.out.println(user);
//        weiXinUserService.updateWeiXinUser(avatar,user_name,user.getOpenid());
//        Map<String, Object> mp = new HashMap<>();
//        mp.put("token",token.getToken(user,user.getSession_key()));
//        return mp;
//    }
//    @ResponseBody
//    @RequestMapping("/wx/getUserInfo")
//    public Map<String,Object> getUserInfo(String tk){
//        Claims tokenClaim = (Claims) token.getTokenClaim(tk);
////        System.out.println(tokenClaim);
////        System.out.println(tokenClaim.get("sub"));
//        Map<String,WeiXinUser> map= JSON.parseObject((String) tokenClaim.get("sub"), Map.class);
////        System.out.println(tokenClaim.get("sessionKey",String.class));
////        System.out.println(tokenClaim.get("user",WeiXinUser.class));
////        System.out.println(map);
//        WeiXinUser user=  JSON.parseObject(String.valueOf(map.get("user")),WeiXinUser.class);
//        System.out.println(user);
////        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
////        // 3.接收微信接口服务 获取返回的参数
////        String newsessionKey = SessionKeyOpenId.getString("session_key");
////        weiXinUserService.updateSession_key(user.getUser_id(),newsessionKey);
//        user=weiXinUserService.findWeiXinUser(user.getOpenid());
//        Map<String, Object> mp = new HashMap<>();
//        mp.put("token",token.getToken(user,user.getSession_key()));
//        mp.put("avatar",user.getAvatar());
//        mp.put("user_name",user.getUser_name());
//        mp.put("phone",user.getUser_id());
//        return mp;
//    }
//    @ResponseBody
//    @RequestMapping(value = "/wx/login",method = RequestMethod.GET)
//    public Map<String,Object> user_login(@RequestParam(value = "encryptedData", required = false) String encryptedData,
//                                         @RequestParam(value = "iv", required = false) String iv,
//                                         @RequestParam(value = "code", required = false) String code) {
//        System.out.println("encryptedData="+encryptedData);
//        System.out.println("iv="+iv);
//        System.out.println("code="+code);
//        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
////        // 3.接收微信接口服务 获取返回的参数
//        String openid = SessionKeyOpenId.getString("openid");
//        String sessionKey = SessionKeyOpenId.getString("session_key");
//        System.out.println("openid="+openid);
//        System.out.println("sessionKey="+sessionKey);
//        weiXinUserService.updateSession_key(sessionKey,openid);
//            byte[] encData = Base64.decode(encryptedData);
//            byte[] ivStr = Base64.decode(iv);
//            byte[] key = Base64.decode(sessionKey);
//            Map<String,String> phoneNum=new HashMap<String,String>();
//            String phone="";
//        try {
//            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivStr);
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//            phoneNum= JsonSerilizable.deserilizableForMapFromFile(new String(cipher.doFinal(encData), "UTF-8"), String.class);
//            phone=phoneNum.get("phoneNumber");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        WeiXinUser user = null;
//        user=weiXinUserService.findWeiXinUser(openid);
//        System.out.println(user);
////        登陆或注册
//        if(user==null){
////            注册
//            user=new WeiXinUser();
//            user.setUser_id(phone);
//            user.setUser_name(null);
//            user.setAvatar(null);
//            user.setOpenid(openid);
//            user.setSession_key(sessionKey);
//            weiXinUserService.insertWeiXinUser(user);
//            System.out.println(user.getUser_id()+"注册成功");
//        }else{
//            System.out.println(user.getUser_id()+"登陆成功");
//        }
////        System.out.println("phoneNum="+phoneNum.get("phoneNumber"));
//        Map<String, Object> map = new HashMap<>();
//        map.put("token",token.getToken(user,sessionKey));
////        map.put("phone",phone);
//        return map;
//    }
//
//}
