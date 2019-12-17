package com.t31.app.dev;

import com.t31.app.service.developer.DataDictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataDictonaryTest {
    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Test
    public void selectStatusListTest(){
        System.out.println(dataDictionaryService.selectStatusList());
    }
}