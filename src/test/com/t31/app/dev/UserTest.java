package com.t31.app.dev;

import com.t31.app.entity.DevUserDTO;
import com.t31.app.service.developer.DevUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserTest {
    @Autowired
    private DevUserService devUserService;
    @Test
        public void loginTest(){
            DevUserDTO user = devUserService.userLogin("test001","123456");
            if(user!=null){
                System.out.println("登录成功");
            }else{
                System.out.println("登录失败");
            }
        }
}
