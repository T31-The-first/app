package com.t31.app.controller.backend;

import com.t31.app.entity.AppCategoryDTO;
import com.t31.app.service.backend.AppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description: CatagoryController
 * date: 2019/12/16 22:10
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/catagory")
public class CatagoryController {
    @Autowired
    private AppCategoryService categoryService;


    @RequestMapping("/levellist")
    @ResponseBody
    public List<AppCategoryDTO> list2(Integer pid){
        List<AppCategoryDTO> list=categoryService.selAll(pid);
        return list;
    }


}
