package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.domain.Tab;
import com.lzmhc.silkdiagrampro.mapper.TabMapperDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class tabListController {
    @Autowired
    private TabMapperDao tabMapperDao;
    @RequestMapping("/tabList")
    public String getTabList(Model model){
        List<Tab> keywordList = tabMapperDao.getTabList();
        model.addAttribute("tablist",keywordList);
        return "tabList";
    }
    @RequestMapping("/submitTab")
    public String addTab(@RequestBody String text){
//        json字符串转对象
        JSONObject jsonObject= JSONObject.fromObject(text);
        String tabname=(String) jsonObject.get("tabName");
        Tab keyword=new Tab();
        keyword.setName(tabname);
        System.out.println(tabname);
        int ok=tabMapperDao.insertTab(keyword);
        if(ok==1){
            return "/tabList";
        }else{
            return "/tabList";
        }
    }
    @RequestMapping("/deleteTabByid")
    public String deleteTab(String id){
        int i = tabMapperDao.deleteTab(Integer.valueOf(id));
        return "/tabList";
    }
    @RequestMapping("/updateIcon")
    public String updateIcon(String keyword_id, MultipartFile icon){
        System.out.println(keyword_id+" "+icon);
        String path = "";
        try {
            path = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        }catch (IOException e){
            e.printStackTrace();
        }
        String image = icon.getOriginalFilename();
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        image = uuid + "_" + image;
        try {
            if (icon != null && !icon.isEmpty()) {
                icon.transferTo(new File(path, image));
            } else {
                image = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int i=tabMapperDao.updateIcon(keyword_id,image);
        return "/tabList";
    }
}
