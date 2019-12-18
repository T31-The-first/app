package com.t31.app.dev;

import com.t31.app.dao.developer.DevAppInfoDAO;
import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppInfoTest {
    @Autowired
        private DevAppInfoService appInfoService;
        @Autowired
        private DevAppInfoDAO appInfoDAO;
        @Test
        public void selectAppInfoTest(){
//            Page page = new Page();
//            for(AppInfoList2 app:(List<AppInfoList2>)appInfoService.selectAppInfo(new AppInfoDTO(),page).getDataList()) {
//                System.out.println(app.getCategoryLevel1Name());
//            }
        }
        @Test
        public void updateAppLogTest(){
            //保存id
            List ids = new ArrayList();
            //保存path
            List paths = new ArrayList();
            List<AppInfoList> appinfo = appInfoDAO.selectAppInfo(new AppInfoDTO(),0,appInfoDAO.selectAppCount(new AppInfoDTO()));
            for(int i=0;i<appinfo.size();i++){
//                ids.add(appinfo.get(i).getId());
//                paths.add(appinfo.get(i).getLogoPicPath());
                //System.out.println(appinfo.get(i).getLogoPicPath());
                System.out.println(appinfo.get(i).getLogoPicPath());
                if(appinfo.get(i).getLogoPicPath()!=null&&appinfo.get(i).getLogoPicPath().substring(0,14).equals("/AppInfoSystem")){
                    //System.out.println(appinfo.get(i).getLogoPicPath());
                    String newPath = appinfo.get(i).getLogoPicPath().substring(14,appinfo.get(i).getLogoPicPath().length());

                    //appInfoDAO.updatePath(newPath,appinfo.get(i).getId());
                }
            }


        }

        @Test
        public void TestAPKname(){
            if(appInfoService.selByAPKName("com.momocorp.o2jamu")>0){
                System.out.println("已存在APKName");

            }else{
                System.out.println("keyi");
            }
        }
        @Test
        public void AddTest(){
            int num=appInfoService.addApp(new AppInfoDTO());
            if(num>0){
                System.out.println("成功！");
            }else{
                System.out.println("失败！");
            }
        }

    @Test
    public void del() {
        int num = appInfoService.delApp(59);
        if(num>0){
            System.out.println("成功！");
        }else{
            System.out.println("失败！");
        }
    }

    @Test
    public void selById(){
            if(appInfoService.selAppById(58)>0){
                System.out.println("zhaodao");
            }else{
                System.out.println("meiyou");
            }
    }
}
