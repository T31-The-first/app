package com.t31.app.dev;

import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.devinfo.AppVersionInfo;
import com.t31.app.service.developer.DevAppVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppVersionTest {
    @Autowired
        private DevAppVersionService appVersionService;
    @Test
        public void selectVersionListTest(){
            List<AppVersionInfo> versionInfo = appVersionService.selectVersionListByAppId(58);
            for (AppVersionInfo versionInfo1:versionInfo){
                System.out.println(versionInfo1);
            }
        }
        @Test
        public void addVersionTest(){
            AppVersionDTO appVersionDTO = new AppVersionDTO();
            appVersionDTO.setAppId(48);
            appVersionDTO.setApkFileName("123");
            appVersionDTO.setApkLocPath("456");
            appVersionDTO.setCreatedBy(1);
            appVersionDTO.setDownloadLink("789");
            appVersionDTO.setVersionNo("1.1");
            appVersionDTO.setPublishStatus(3);
            appVersionDTO.setVersionSize(10);
            appVersionDTO.setVersionInfo("iiii");
            int result = appVersionService.addAppVersion(appVersionDTO);
            if(result>0){
                System.out.println("新增成功");
            }else{
                System.out.println("新增失败");
            }
        }
}
