package com.lzmhc.silkdiagrampro.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WechatUtil {
    public static JSONObject getSessionKeyOrOpenId(String code){
        String requestUrl="https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam=new HashMap<>();
        requestUrlParam.put("appid","wxe69ea29d8c4050a9");
        requestUrlParam.put("secret","f0763996ff143dc50872213dd628a883");
        requestUrlParam.put("js_code",code);
        requestUrlParam.put("grant_type","authorization_code");
        JSONObject jsonObject= JSON.parseObject(HttpClientUtil.doPost(requestUrl,requestUrlParam));
        return jsonObject;
    }
}
