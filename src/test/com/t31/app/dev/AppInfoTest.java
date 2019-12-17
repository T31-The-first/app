package com.t31.app.dev;

import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppInfoTest {
    @Autowired
        private DevAppInfoService appInfoService;
        @Test
        public void selectAppInfoTest(){
//            Page page = new Page();
//            for(AppInfoList app:(List<AppInfoList>)appInfoService.selectAppInfo(new AppInfoDTO(),page).getDataList()) {
//                System.out.println(app.getCategoryLevel1Name());
//            }
        }
}
