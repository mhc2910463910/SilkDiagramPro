package com.lzmhc.silkdiagrampro.service.impl;

import com.lzmhc.silkdiagrampro.domain.User;
import com.lzmhc.silkdiagrampro.domain.UserVo;
import com.lzmhc.silkdiagrampro.domain.UserVoToUser;
import com.lzmhc.silkdiagrampro.mapper.UserMapperDao;
import com.lzmhc.silkdiagrampro.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private UserMapperDao userMapper;

    @Autowired
    private JavaMailSenderImpl mailSender;

    // application.properties配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param session
     * @return
     */
    @Override
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            // 生成随机数
            String code = randomCode();

            // 将随机数放置到session中
            session.setAttribute("email", email);
            session.setAttribute("code", code);

            simpleMailMessage.setSubject("验证码邮件"); // 主题
            simpleMailMessage.setText("您的验证码是："+code); // 内容
            simpleMailMessage.setFrom("2910463910@qq.com"); // 发件人
            simpleMailMessage.setTo(email); // 收件人
            mailSender.send(simpleMailMessage); // 发送

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    @Override
    public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for(int i = 0;i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 检验验证码
     * @param userVo
     * @param session
     * @return
     */
    @Override
    public boolean registered(UserVo userVo, HttpSession session) {
        // 获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        // 获取表单中的提交的验证信息
        String voEmail=userVo.getEmail();
        String voCode =  userVo.getCode();
//        System.out.println("email="+email);
//        System.out.println("code="+code);
//        System.out.println("voEmail="+voEmail);
//        System.out.println("voCode="+voCode);
        //如果emial数据为空或者不一致都失败
        if(email == null || email.isEmpty()) {
            //return "error,请重新注册";
//            System.out.println("email"+email);
            return false;
        }else if(!email.equals(voEmail)){
//            System.out.println("email错误");
            return false;
        }else if (!code.equals(voCode)){
            //return "error,请重新注册";
//            System.out.println("验证码错误");
            return false;
        }

        // 保存数据
        User user = UserVoToUser.toUser(userVo);
        user.setPassword("12345678");

        // 将数据写进数据库
        userMapper.insertUser(user);

        // 跳转成功页面
        return true;
    }

    /**
     * 通过输入email查询password，然后比较两个password，如果一样，登录成功
     * @param email
     * @param password
     * @return
     */
    @Override
    public boolean loginIn(String email, String password) {
//        System.out.println(email);
//        System.out.println(password);
        User user = userMapper.findUserByEmail(email);
        if(user==null){
            return false;
        }
        if(!user.getPassword().equals(password)) {
            return false;
        }
        System.out.println(email+"登录成功");
        return true;
    }
}