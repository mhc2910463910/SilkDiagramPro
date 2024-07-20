package com.lzmhc.silkdiagrampro.controller;


import com.lzmhc.silkdiagrampro.Utils.TokenUtil;
import com.lzmhc.silkdiagrampro.domain.WeiXinUser;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class StepController {
    @Autowired
    private TokenUtil token;
    @ResponseBody
    @RequestMapping("/decrypt")
    public String decrypt(String encryptedData,String iv,String tk){
        WeiXinUser user=token.getWeiXinUser(tk);
        System.out.println("解密:"+user.getUser_id()+" "+user.getSession_key());
        Map map=new HashMap();
        final Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] sessionKeyByte = decoder.decode(user.getSession_key());
        byte[] encryptedDataByte = decoder.decode(encryptedData);
        byte[] ivByte = decoder.decode(iv);

        byte[] bytes;
        try {
            Key key = new SecretKeySpec(sessionKeyByte, "AES");
            AlgorithmParameters algorithmParameters = null;
            Cipher cipher = null;
            algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(ivByte));
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            bytes = cipher.doFinal(encryptedDataByte);
        } catch (InvalidParameterSpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            bytes = new byte[0];
        }

        String decryptString = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("微信解密后数据为：{}"+decryptString);
        return decryptString;
    }
}
