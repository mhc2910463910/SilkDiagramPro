package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.Utils.JsonSerilizable;
import com.lzmhc.silkdiagrampro.Utils.TokenUtil;
import com.lzmhc.silkdiagrampro.domain.User;
import com.lzmhc.silkdiagrampro.domain.UserVo;
import com.lzmhc.silkdiagrampro.mapper.UserMapperDao;
import com.lzmhc.silkdiagrampro.service.MailService;
import org.apache.http.HttpResponse;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private MailService mailService;
    @Autowired
    private UserMapperDao userMapperDao;
    @Autowired
    private TokenUtil token;
//    注册发送验证码
    @PostMapping(value = "/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession) {
        if(email==""||email.equals(null)){
            return "邮箱不能为空";
        }
        System.out.println(email+"申请登陆");
        User userByEmail = userMapperDao.findUserByEmail(email);
        if(userByEmail!=null){
            System.out.println("该邮箱已注册");
            return "该邮箱已注册";
        }
        boolean b = mailService.sendMimeMail(email, httpSession);
//        System.out.println(httpSession.getAttribute("email"));
//        System.out.println(httpSession.getAttribute("code"));

        if(b){
            return "验证码已发送";
        }else{
            return "出现未知错误,请稍后重试";
        }
    }
//  校验验证码
    @PostMapping(value = "/regist")
    @ResponseBody
    public String regist(String email,String code, HttpSession httpSession) {
        if(email==""||email.equals(null)||code==""||code.equals("null")){
            System.out.println("邮箱或验证码不能为空");
            return "邮箱或验证码不能为空";
        }
        User userByEmail = userMapperDao.findUserByEmail(email);
        if(userByEmail!=null){
            System.out.println("该邮箱已注册");
            return "该邮箱已注册";
        }
        UserVo userVo=new UserVo();
        userVo.setEmail(email);
        userVo.setCode(code);
        boolean flag=mailService.registered(userVo, httpSession);
//        System.out.println(userVo);
        if(flag){
            System.out.println("用户注册成功,默认密码是12345678,请及时修改");
            return "用户注册成功,默认密码是12345678,请及时修改";
        }else {
            System.out.println("用户信息验证错误");
            return "用户信息验证错误";
        }
    }
//  登陆
    @PostMapping(value = "/login")
    @ResponseBody
    public boolean login(String email, String password, HttpSession session, HttpServletResponse response){
        if(email==""||email.equals(null)||password==""||password.equals("null")){
            System.out.println("邮箱或密码不能为空");
            return false;
        }
        boolean b = mailService.loginIn(email, password);
//        System.out.println(session.toString());
        if(b){
            User user=new User();
            user=userMapperDao.findUserByEmail(email);
            String tk = token.getToken(user, password);
            Cookie cookie=new Cookie("token",tk);
            response.addCookie(cookie);
            System.out.println("用户登陆成功");
            return true;
        }else{
            System.out.println("用户未注册或密码错误");
            return false;
        }
    }
//    解析token,获取用户信息
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String,Object> getUsernfo(String tk){
//        System.out.println(request);
//        Cookie[] cookies = request.getCookies();
//        System.out.println(cookies);
//        User user = token.getUser(String.valueOf(cookies[1]));
        Map<String , Object> map=new HashMap<>();
        User user=token.getUser(tk);
        map.put("username", user.getUsername());
        map.put("email",user.getEmail());
        map.put("phone",user.getPhone());
        map.put("avatar",user.getAvatar());
        return map;
    }
//    设置密码
    @RequestMapping("/setPassword")
    @ResponseBody
    public boolean setPassword(String tk,String password,String newPassword,HttpServletResponse response){
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie=cookies[1];
//        String name=cookie.getName();
//        String tk=cookie.getValue();
        User user=token.getUser(tk);
        User userByEmail = userMapperDao.findUserByEmail(user.getEmail());
        if(userByEmail.getPassword().equals(password)){
            userByEmail.setPassword(newPassword);
            int i = userMapperDao.setPassword(userByEmail);
            tk=token.getToken(userByEmail,newPassword);
            if(i==1){
                Cookie ck=new Cookie("token",tk);
                response.addCookie(ck);
                System.out.println("修改密码成功");
                return true;
            }else{
                System.out.println("密码更新错误");
                return false;
            }

        }
        System.out.println("密码错误");
        return false;
    }

//    设置用户名
    @RequestMapping("/setUserName")
    @ResponseBody
    public boolean setUserName(String tk,String username){
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie=cookies[1];
//        String name=cookie.getName();
//        String tk=cookie.getValue();
        System.out.println("修改用户名");
        User user=token.getUser(tk);
        user.setUsername(username);
        int i=userMapperDao.setUserName(user);
        if(i==1){
//            Cookie ck=new Cookie("token",tk);
//            response.addCookie(ck);
            System.out.println("修改用户名成功");
            return true;
        }else{
            System.out.println("出现错误");
            return false;
        }

    }

//    设置头像
    @RequestMapping("/setAvatar")
    @ResponseBody
    public boolean setAvatar(String tk,String avatar){
//        Cookie[] cookies = request.getCookies();
//        Cookie cookie=cookies[1];
//        String name=cookie.getName();
//        String tk=cookie.getValue();
        System.out.println("用户修改头像");
        User user=token.getUser(tk);
        user.setAvatar(avatar);
        userMapperDao.setAvatar(user);
        return true;
    }

////    设置手机号
//    @RequestMapping("/setPhone")
//    @ResponseBody
//    public boolean setPhone(String tk,String phone){
////        Cookie[] cookies = request.getCookies();
////        Cookie cookie=cookies[1];
////        String name=cookie.getName();
////        String tk=cookie.getValue();
//        User user=token.getUser(tk);
//        user.setPhone(phone);
//        int i=userMapperDao.setPhone(user);
//        if(i==1){
////            Cookie ck=new Cookie("token",tk);
////            response.addCookie(ck);
//            System.out.println("修改电话信息成功");
//            return true;
//        }else{
//            System.out.println("出现错误");
//            return false;
//        }
//    }

    @RequestMapping("/userList")
    public String getuserLIst(Model model){
        List<User> userList = userMapperDao.getUserList();
        model.addAttribute("userList",userList);
        return "userList";
    }
}
