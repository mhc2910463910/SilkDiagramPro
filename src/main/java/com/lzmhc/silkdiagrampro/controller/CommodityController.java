package com.lzmhc.silkdiagrampro.controller;

import com.lzmhc.silkdiagrampro.domain.Commodity;
import com.lzmhc.silkdiagrampro.domain.Tab;
import com.lzmhc.silkdiagrampro.mapper.CommodityMapperDao;
import com.lzmhc.silkdiagrampro.mapper.TabMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CommodityController {
    @Autowired
    private CommodityMapperDao commodityService;
    @Autowired
    private TabMapperDao tabMapperDao;

//    商品查找
    @RequestMapping("/getCommodityByid")
    public String getcommodity(Model model,@RequestParam(name="id") String id){
        System.out.println("获取商品id为"+id);
        Commodity commodity=commodityService.findCommodityById(id);
        model.addAttribute("commodity",commodity);
        return "updateCommodity";
    }
//    获取商品列表
    @RequestMapping("/commodityList")
    public String getcommodityList(Model model){
        List<Commodity> commodities = commodityService.findAllCommodity();
        model.addAttribute("commodityList",commodities);
        return "commodityList";
    }
//    商品增加
    @RequestMapping("/addCommodity")
    public String getaddCommodity(Model model){
        List<Tab> tablist=tabMapperDao.getTabList();
        model.addAttribute("tablist",tablist);
        return "addCommodity";
    }
//    获取商品列表
    @ResponseBody
    @RequestMapping("/getCommodityList")
    public List<Commodity> getCommodityList(Model model){
        List<Commodity> commodities = commodityService.findAllCommodity();
        System.out.println(commodities);
        return commodities;
    }
//    获取商品类型列表
    @ResponseBody
    @RequestMapping("/getCommodityTypeList")
    public List<Tab> getCommodityTypeList(){
        List<Tab> typeList=tabMapperDao.getTabList();
        return typeList;
    }
//  获取指定商品类型
    @ResponseBody
    @RequestMapping("/getCommodityByTypeId")
    public List<Commodity> getCommodityByTypeId(String id){
        System.out.println(id);
        Tab tabByid = tabMapperDao.getTabByid(id);
        List<Commodity> commodityList = commodityService.findCommodityByTypeName(tabByid.getName());
        return commodityList;
    }
//    删除商品
    @RequestMapping("/deleteEssayByid")
    public String delCommodity(@RequestParam("id") String id){
        int flag=commodityService.deleteCommodity(id);
        return "commodityList";
    }
    @ResponseBody
    @RequestMapping("/findCommodity")
    public Commodity findCommodity(String id){
        Commodity commodity=commodityService.findCommodityById(id);
        return commodity;
    }
//    修改商品
    @RequestMapping("/updateCommodity")
    public String updateCommodity(String id,String name,String type,String price, MultipartFile img1, MultipartFile img2, MultipartFile img3 ){
        System.out.println(id+" "+name+" "+type+" "+price+" "+img1+" "+img2+" "+img3);
        String path="";
        try {
            path = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        }catch(IOException e){
            System.out.println("资源文件路径未找到");
        }
        String image1 = img1.getOriginalFilename();
        String image2 = img2.getOriginalFilename();
        String image3=img3.getOriginalFilename();
        /**
         *文件上传
         */
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        image1 = uuid + "_" + image1;
        image2 = uuid + "_" + image2;
        image3 = uuid + "_" + image3;
        try{
            if (img1 != null && !img1.isEmpty()) {
                img1.transferTo(new File(path, image1));
            } else {
                image1 = null;
            }
            if (img2 != null && !img2.isEmpty()) {
                img2.transferTo(new File(path, image2));
            } else {
                image2 = null;
            }
            if (img3 != null && !img3.isEmpty()) {
                img3.transferTo(new File(path, image3));
            } else {
                image3 = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int flag=commodityService.updateCommodity(id,name,type,price,image1,image2,image3);
        return "commodityList";
    }
//增加商品
    @RequestMapping("/insertCommodity")
    public String addCommodity(String name,String type,String price, MultipartFile img1, MultipartFile img2, MultipartFile img3, Model model) throws IOException {
        System.out.println(name+" "+type+" "+price+" "+img1+" "+img2+" "+img3);
        String path = ResourceUtils.getURL("classpath:").getPath()+"/static/upload";
        System.out.println(img1+" "+img2+" "+img3);
        String image1 = img1.getOriginalFilename();
        String image2 = img2.getOriginalFilename();
        String image3=img3.getOriginalFilename();
        /**
         *文件上传
         */
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        image1 = uuid + "_" + image1;
        image2 = uuid + "_" + image2;
        image3 = uuid + "_" + image3;
        if (img1 != null && !img1.isEmpty()) {
            img1.transferTo(new File(path,image1));
        }else{
            image1=null;
        }
        if (img2 != null && !img2.isEmpty()) {
            img2.transferTo(new File(path,image2));
        }else{
            image2=null;
        }
        if (img3 != null && !img3.isEmpty()) {
            img3.transferTo(new File(path,image3));
        }else{
            image3=null;
        }
        System.out.println(name+" "+type+" "+price+" "+img1+" "+img2+" "+img3);
        Commodity commodity=new Commodity();
        commodity.setName(name);
        commodity.setType(type);
        commodity.setPrice(Double.parseDouble(price));
        commodity.setImg1(image1);
        commodity.setImg2(image2);
        commodity.setImg3(image3);
        commodityService.insertCommodity(commodity);
        return "addCommodity";
    }
}
