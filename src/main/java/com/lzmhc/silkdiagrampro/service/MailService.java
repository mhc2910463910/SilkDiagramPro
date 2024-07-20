package com.lzmhc.silkdiagrampro.service;

import com.lzmhc.silkdiagrampro.domain.UserVo;

import javax.servlet.http.HttpSession;

public interface MailService {
    public boolean sendMimeMail( String email, HttpSession session);
    public String randomCode();
    public boolean registered(UserVo userVo, HttpSession session);
    public boolean loginIn(String email, String password);
}
