package com.t31.app.dev.version;

import com.t31.app.service.backend.AppVersionService;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.service.developer.DevAppVersionService;
import com.t31.app.service.developer.impl.DevAppVersionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description: VersionTest
 * date: 2019/12/18 8:35
 * author: 周吉涛
 * version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VersionTest {
    @Autowired
    private DevAppVersionService versionService;

    @Test
    public void testSel(){
        int num=versionService.selByAppId(58);
        if(num>0){
            System.out.println("找到相关信息"+num);
        }else{
            System.out.println("没有找到相关信息"+num);
        }
    }
}
